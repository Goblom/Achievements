/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.api.achievement;

import codes.goblom.achievements.AchievementApi;
import codes.goblom.achievements.AchievementPlugin;
import codes.goblom.achievements.api.AchievementData;
import codes.goblom.achievements.api.events.AchievementAwardedEvent;
import codes.goblom.achievements.misc.ExpiringMap;
import codes.goblom.achievements.model.RegisteredModel;
import codes.goblom.achievements.model.UnlockedModel;
import com.avaje.ebean.ExpressionList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Goblom
 */
@Getter
public final class RegisteredAchievement extends AchievementData {
    
    private static Map<UUID, Map<Long, UnlockedAchievement>> MAP = Maps.newConcurrentMap();
    private static Map<String, String> ICONS = Maps.newConcurrentMap();
    
    static {
        ICONS.put("box", "■");
    }
    
    private final long id;
    private final String server;
    private final Timestamp registeredOn;
    
    protected RegisteredAchievement(String name, String description, Type type, long id, String server, Timestamp on) {
        super(name, description, type);
        
        this.id = id;
        this.server = server;
        this.registeredOn = on;
    }
    
    /**
     * This will return null if player has not yet unlocked (been awarded) the achievement
     * 
     * This is not stored anywhere. Everytime you call this method it runs a query on the database.
     */
    public UnlockedAchievement getUnlocked(OfflinePlayer player) {
        if (!MAP.containsKey(player.getUniqueId())) {
            MAP.put(player.getUniqueId(), ExpiringMap.builder().expiration(10, TimeUnit.MINUTES).build());
        } else {
            Map<Long, UnlockedAchievement> localMap = MAP.get(player.getUniqueId());
            
            if (localMap.containsKey(getId())) {
                return localMap.get(getId());
            }
        }
        
        ExpressionList<UnlockedModel> query = AchievementPlugin.getPlugin().getDatabase().find(UnlockedModel.class).where()
                .ieq("uuid", player.getUniqueId().toString())
                .ieq("achievement_id", String.valueOf(getRegisteredStat().getId()));
                if (getType() != AchievementData.Type.GLOBAL) {
                    query.ieq("unlocked_server", AchievementPlugin.getPlugin().getEnglishServerName());
                }
        
        UnlockedModel model = query.findUnique();
        UnlockedAchievement stat = null;
        
        if (model != null) {
            setLocalUnlocked(player, model.getAchievementId(), true);
            Map<Long, UnlockedAchievement> localMap = MAP.get(player.getUniqueId());
            localMap.put(getId(), stat = model.toStat());
        }
        
        return stat;
    }
    
    public void award(OfflinePlayer player) {
        if (hasUnlocked(player)) {
            return;
        }
        
        AchievementAwardedEvent aae = new AchievementAwardedEvent(this, player, AchievementApi.getLocallyRegisteredAchievement(getName()));
        Bukkit.getPluginManager().callEvent(aae);
            
        if (aae.isCancelled()) {
            return;
        }
        
        if (player instanceof Player) {
            List<String> lines = Lists.newArrayList();
            
            for (String line : AchievementApi.getUnlockedMessageFormat()) {
                String r = line.replace("{name}", getName()).replace("{desc}", getDescription());
                
                for (Entry<String, String> icon : ICONS.entrySet()) {
                    r = r.replace("%" + icon.getKey(), icon.getValue());
                }
                
                lines.add(r);
            }
            
            lines.stream().forEach((line) -> {
                ((Player) player).sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            });
        }
        
        new BukkitRunnable() {
            @Override
            public void run() {
                UnlockedModel model = new UnlockedModel();
                              model.setUuid(player.getUniqueId().toString());
                              model.setLastKnownName(player.getName());
                              model.setUnlockedServer(AchievementPlugin.getPlugin().getEnglishServerName());
                              model.setAchievementId(getId());
                              model.setType(getType().name());

                AchievementPlugin.getPlugin().getDatabase().save(model);
                setLocalUnlocked(player, Boolean.TRUE);
                AchievementApi.getAllUnlockedAchievements(player);
            }
        }.runTaskAsynchronously(AchievementPlugin.getPlugin());
    }
    
    public static RegisteredAchievement fromModel(RegisteredModel model) {
        long id = model.getId();
        String name = model.getName();
        String description = model.getDescription();
        Type type = Type.valueOf(model.getType());
        String server = model.getServer();
        Timestamp registeredOn = model.getRegisteredOn();
        
        return new RegisteredAchievement(name, description, type, id, server, registeredOn);
    }
}
