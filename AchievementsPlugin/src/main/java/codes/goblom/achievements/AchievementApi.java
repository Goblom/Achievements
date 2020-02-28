/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.reflections.Reflections;
import codes.goblom.achievements.api.Achievement;
import codes.goblom.achievements.api.AchievementData;
import codes.goblom.achievements.api.achievement.RegisteredAchievement;
import codes.goblom.achievements.api.achievement.UnlockedAchievement;
import codes.goblom.achievements.misc.ExpiringMap;
import codes.goblom.achievements.model.RegisteredModel;
import codes.goblom.achievements.model.UnlockedModel;
import com.avaje.ebean.ExpressionList;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import me.libraryaddict.inventory.InventoryApi;
import me.libraryaddict.inventory.ItemBuilder;
import me.libraryaddict.inventory.PageInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Goblom
 */
public class AchievementApi {

    private static String[] UNLOCKED_MESSAGE_FORMAT = {
        ChatColor.GRAY + "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■",
        "",
        ChatColor.AQUA.toString() + ChatColor.BOLD + "Name",
        ChatColor.GRAY + "{name}",
        "",
        ChatColor.AQUA.toString() + ChatColor.BOLD + "Description",
        ChatColor.GRAY + "{desc}",
        "",
        ChatColor.GRAY + "■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■",
    };
    
    protected static AchievementPlugin plugin = AchievementPlugin.getPlugin();
    
    private static final Map<String, Achievement> registeredAchievements = Maps.newHashMap();
    private static ImmutableList<RegisteredAchievement> allRegisteredAchievements = null;
    private static final Map<UUID, ImmutableList<UnlockedAchievement>> unlockedAchievements = ExpiringMap.builder().expiration(10, TimeUnit.MINUTES).build();
    
    public static Collection<Achievement> getLocallyRegisteredAchievements() {
        return Collections.unmodifiableCollection(registeredAchievements.values());
    }
    
    public static void refreshAllRegisteredAchievements() {
    	allRegisteredAchievements = null;
    	getAllRegisteredAchievements();
    }
    
    public static void refreshUnlockedAchievements(OfflinePlayer player) {
        unlockedAchievements.remove(player.getUniqueId());
        getAllUnlockedAchievements(player);
    }
    
    public static List<RegisteredAchievement> getAllRegisteredAchievements() {
    	if (allRegisteredAchievements == null) {
            List<RegisteredModel> models = plugin.getDatabase().find(RegisteredModel.class).findList();
            List<RegisteredAchievement> list = Lists.newArrayList();
            models.stream().forEach((model) -> {
                list.add(model.toStat());
            });

            allRegisteredAchievements = ImmutableList.<RegisteredAchievement>copyOf(list);
    	}
    	
        return allRegisteredAchievements;
    }
    
    public static ImmutableList<UnlockedAchievement> getAllUnlockedAchievements(OfflinePlayer player) {
    	if (unlockedAchievements.containsKey(player.getUniqueId())) {
            return unlockedAchievements.get(player.getUniqueId());
    	}
    	
    	List<UnlockedModel> models = plugin.getDatabase().find(UnlockedModel.class).where()
                .ieq("uuid", player.getUniqueId().toString()).findList();
    	List<UnlockedAchievement> list = Lists.newArrayList();
    	
        models.stream().forEach((model) -> {
            list.add(model.toStat());
        });
    	
    	ImmutableList<UnlockedAchievement> stats = ImmutableList.<UnlockedAchievement>copyOf(list);
    	unlockedAchievements.put(player.getUniqueId(), stats);
        return stats;
    }
    
    public static boolean isUnlocked(OfflinePlayer player, AchievementData ach) {
        return ach.hasUnlocked(player);
    }
    
    public static RegisteredAchievement registerAchievement(Achievement achievement) { 
        if (registeredAchievements.containsKey(achievement.getName())) {
            return registeredAchievements.get(achievement.getName()).getRegisteredStat();
        }
        
        registeredAchievements.put(achievement.getName(), achievement);
        
        ExpressionList<RegisteredModel> query = plugin.getDatabase().find(RegisteredModel.class).where()
                .ieq("name", achievement.getName())
                .ieq("description", achievement.getDescription())
                .ieq("type", achievement.getType().name());
                if (achievement.getType() != AchievementData.Type.GLOBAL) {
                    query.ieq("server", plugin.getEnglishServerName());
                }
                
        RegisteredModel model = query.findUnique();
        
        if (model == null) {
            model = new RegisteredModel();
            model.setName(achievement.getName());
            model.setDescription(achievement.getDescription());
            model.setType(achievement.getType().name());
            if (achievement.getType() != AchievementData.Type.GLOBAL) {
                model.setServer(plugin.getEnglishServerName());
            }
            
            plugin.getDatabase().save(model);
        }
        
        achievement.setStat(model.toStat());
        AchievementPlugin.getPlugin().debug("Registered Achievement[" + achievement.getRegisteredStat().getName() + "]");
        return achievement.getRegisteredStat();
    }
    
    public static Achievement getLocallyRegisteredAchievement(String name) {
        return registeredAchievements.get(name);
    }
    
    public static RegisteredAchievement getRegisteredAchievement(String name) {
        for (RegisteredAchievement stat : getAllRegisteredAchievements()) {
            if (stat.getName().equalsIgnoreCase(name)) {
                return stat;
            }
        }
        
        return null;
    }
    
    public static RegisteredAchievement getRegisteredAchievement(long id) {
        for (RegisteredAchievement stat : getAllRegisteredAchievements()) {
            if (stat.getId() == id) {
                return stat;
            }
        }
        
        return null;
    }
    
    public static void registerAchievementsFromPackage(String packageStr) {
        Reflections r = new Reflections(packageStr);
        Set<Class<? extends Achievement>> achievements = r.getSubTypesOf(Achievement.class);
        
        achievements.stream()/*.map((achievement) -> {
            System.out.println(String.format("Found Achievement [%s]", achievement.getSimpleName()));
            return achievement;
        })*/.forEach((achievement) -> {
            try {
                AchievementApi.registerAchievement(achievement.newInstance());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });
    }
    
    public static void addCustomListener(BaseListener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
    
    public static String[] getUnlockedMessageFormat() {
        return AchievementApi.UNLOCKED_MESSAGE_FORMAT;
    }
    
    public static void setUnlockedMessageFormat(String[] format) {
        AchievementApi.UNLOCKED_MESSAGE_FORMAT = format;
    }
    
    public static void openAchievementsMenu(Player player) {
        openAchievementsMenu(player, player);
    }
    
    public static void openAchievementsMenu(Player whoOpens, OfflinePlayer forWhom) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final PageInventory inv;
                
                if (whoOpens == forWhom) {
                    inv = new PageInventory("Unlocked Achievements", whoOpens, true);
                } else {
                    inv = new PageInventory(forWhom.getName() + "'s Achievements", whoOpens, true);
                }
                
                ArrayList<ItemStack> items = Lists.newArrayList();
                
                for (RegisteredAchievement stat : AchievementApi.getAllRegisteredAchievements()) {
                    String name = stat.getName();
                    String server = stat.getType() == AchievementData.Type.GLOBAL ? "Global" : stat.getServer();

                    UnlockedAchievement us = stat.getUnlocked(forWhom);
                    ItemBuilder builder = new ItemBuilder(us == null ? Material.GLASS_BOTTLE : Material.EXPERIENCE_BOTTLE);
                                builder.setTitle(String.format("%s (%s)", name, server));
                                builder.addLore((us == null ? ChatColor.RED : ChatColor.GREEN) + stat.getDescription());

                    if (us != null) {
                        builder.addLore("Unlocked On: " + us.getUnlocked().toString());
                    }

                    items.add(builder.build());
                }

                inv.setPages(items);
                
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        InventoryApi.registerInventory(inv);
                        inv.openInventory();
                    }
                }.runTask(plugin);
            }
        }.runTaskAsynchronously(plugin);
    }
}
