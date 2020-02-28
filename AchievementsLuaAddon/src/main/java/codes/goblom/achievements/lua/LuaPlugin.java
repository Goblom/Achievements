/*
 * Copyright 2015 Goblom.
 */
package codes.goblom.achievements.lua;

import java.io.File;
import java.io.FileFilter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Goblom
 */
public class LuaPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {        
        saveResource("variables.lua", false);
        Environment.load(this);
        
        PluginCommand cmd = getCommand("achievementslua");
                      cmd.setExecutor(this);
                      cmd.setDescription("Save the LuaAchievements example achievement");
                      cmd.setPermission("achievements.example");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            saveResource("blameGoblom.lua", true);
        }
        
        return true;
    }
    
    static class LuaFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            return !pathname.getName().startsWith("example") && !pathname.getName().startsWith("variables") && pathname.getName().toLowerCase().endsWith(".lua");
        }
        
    }
}
