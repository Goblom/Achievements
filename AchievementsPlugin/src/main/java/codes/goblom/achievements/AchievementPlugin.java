/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements;

import codes.goblom.achievements.model.UnlockedModel;
import codes.goblom.achievements.misc.Database;
import codes.goblom.achievements.misc.UUIDFetcher;
import codes.goblom.achievements.model.RegisteredModel;
import com.avaje.ebean.EbeanServer;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;

/**
 *
 * @author Goblom
 */
public class AchievementPlugin extends JavaPlugin {

    @Getter
    private static AchievementPlugin plugin;
    private Database db;

    @Override
    public void onLoad() {
        plugin = this;
    }
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        this.db = new Database();
        this.db.init(this);
        this.db.start("achievements", Arrays.asList(UnlockedModel.class, RegisteredModel.class));

        PluginCommand cmd = getCommand("achievements");
                      cmd.setDescription("View achievements");
                      cmd.setUsage("/a (player)");
                      cmd.setExecutor(this);

        Reflections r = new Reflections("codes.goblom.achievements");
        Set<Class<? extends BaseListener>> listeners = r.getSubTypesOf(BaseListener.class);

        listeners.stream().forEach((bl) -> {
            try {
                Bukkit.getPluginManager().registerEvents(bl.newInstance(), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Testing Achievements
        if (getConfig().getBoolean("enable-testing-achievements")) { 
            AchievementApi.registerAchievementsFromPackage("testing");
        }
        
        List<String> unlockedMessageFormat = getConfig().getStringList("unlocked-message");
        if (unlockedMessageFormat != null && !unlockedMessageFormat.isEmpty()) {
            String[] lines = unlockedMessageFormat.toArray(new String[unlockedMessageFormat.size()]);
            AchievementApi.setUnlockedMessageFormat(lines);
        }
    }

    public String getEnglishServerName() {
        return getConfig().getString("server-name", "Global");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can do that");
            return true;
        }

        debug("Running achievement command sent by " + sender.getName());
        
        new BukkitRunnable() {
            @Override
            public void run() {
                if (args.length == 1) {
                    if (!(sender.isOp() || sender.hasPermission("achievements.viewothers"))) {
                        sender.sendMessage("You do not have permission to do that.");
                        return;
                    }
                    
                    String arg = args[0];
                    UUID uuid = null;

                    try {
                        uuid = UUID.fromString(arg);
                    } catch (Exception e) { }
                    
                    if (uuid == null) {
                        try {
                            uuid = Bukkit.getPlayer(arg).getUniqueId();
                        } catch (Exception e) { }
                    }
                    
                    if (uuid == null) {
                        try {
                            uuid = UUIDFetcher.getId(arg);
                        } catch (Exception e) { 
                            debug(e.getMessage());
                        }
                    }

                    if (uuid == null) {
                        sender.sendMessage(ChatColor.RED + "Unable to gather achievement data for " + arg);
                    } else {
                        AchievementApi.openAchievementsMenu((Player) sender, Bukkit.getOfflinePlayer(uuid));
                    }
                } else {
                    AchievementApi.openAchievementsMenu((Player) sender);
                }
            }
        }.runTaskAsynchronously(this);
        
        return true;
    }

    public EbeanServer getDatabase() {
        return this.db.getDatabase();
    }

    public void debug(String message) {
        if (getConfig().getBoolean("debug", false)) {
            getLogger().info("[Debug] " + message);
        }
    }
}
