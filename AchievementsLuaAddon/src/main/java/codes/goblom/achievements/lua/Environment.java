/*
 * Copyright 2016 Goblom.
 */
package codes.goblom.achievements.lua;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 *
 * @author Goblom
 */
class Environment {
    
    public static void load(LuaPlugin plugin) {
        File[] files = plugin.getDataFolder().listFiles(new LuaFilter());
        String variables = new File(plugin.getDataFolder(), "variables.lua").getAbsolutePath();
        
        if (files != null && files.length != 0) {
            for (File file : files) {
                Globals globals = JsePlatform.standardGlobals();
                        globals.loadfile(variables);
                
                LuaTable val = (LuaTable) globals.load(new RegisteredAchievementTable(file));
                
                Arrays.asList("achievement", "achievements", "ach")
                        .forEach((name) -> {
                            globals.set(name, val);
                            globals.get("package").get("loaded").set(name, val);
                        });
                
                Arrays.asList(val.keys()).forEach((key) -> globals.set(key, val.get(key)));
                LuaValue chunk = null;
                
                try {
                    chunk = globals.loadfile(file.getAbsolutePath());
                } catch (Exception e) {
                    if (e instanceof LuaError) {
                        throw e;
                    } else {
                        e.printStackTrace();
                    }
                }
                
                if (chunk == null) {
                    plugin.getLogger().warning(String.format("Unable to load luaFile (%s)", file.getName()));
                    continue;
                }
                
                try {
                    chunk.call();
                } catch (Exception e) { e.printStackTrace(); }
            }
        }
    }
    
    static class LuaFilter implements FileFilter {

        @Override
        public boolean accept(File pathname) {
            return !pathname.getName().startsWith("example") && !pathname.getName().startsWith("variables") && pathname.getName().toLowerCase().endsWith(".lua");
        }
        
    }
}
