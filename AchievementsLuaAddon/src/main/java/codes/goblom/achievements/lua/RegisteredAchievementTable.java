/*
 * Copyright 2016 Goblom.
 */
package codes.goblom.achievements.lua;

import codes.goblom.achievements.AchievementApi;
import codes.goblom.achievements.api.achievement.RegisteredAchievement;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import org.bukkit.OfflinePlayer;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * 
 * @author Goblom
 */
class RegisteredAchievementTable extends TwoArgFunction {

    private final LuaAchievementBuilder builder;
    
    private RegisteredAchievement registeredAchievement = null;
    private LuaValue registeredAchievementLua = null;
    
    public RegisteredAchievementTable(File file) {
        this.builder = new LuaAchievementBuilder(file.getName());
    }
    
    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaTable t = new LuaTable();
                 
                // Achievement Builder
                 t.set("setName", new setName());
                 t.set("setDescription", new setDescription());
                 t.set("setType", new setType());
                 Arrays.asList("isCompatible", "setIsCompatible").forEach((name) -> t.set(name, new setIsCompatible()));
                 Arrays.asList("setTesting", "setTest", "setTestFunction").forEach((name) -> t.set(name, new setTestFunction()));
                 Arrays.asList("finish", "register", "build").forEach((name) -> t.set(name, new register()));
                 
                 // Achievement Modifications
                 Arrays.asList("get", "getAchievement").forEach((name) -> t.set(name, new getAchievement()));
                 t.set("getAchievementFromName", new getAchievementByNameOrId());
                 t.set("getAchievementFromId", new getAchievementByNameOrId());
                 
                 // Data Handling
                 t.set("getData", new getData());
                 t.set("getDataKeyOrDefault", new getDataKeyOrDefault());
                 t.set("getDataKey", new getDataKey());
                 t.set("setDataKey", new setDataKey());
                 t.set("removeDataKey", new removeDataKey());
                 
        return t;
    }
    
    class removeDataKey extends TwoArgFunction {
        
        @Override
        public LuaValue call(LuaValue lplayer, LuaValue lkey) {
            OfflinePlayer player = (OfflinePlayer) check(lplayer);
            String key = lkey.checkjstring();
            
            Object val = builder.removeDataKey(player, key);
            
            return CoerceJavaToLua.coerce(val);
        }
    }
    
    class getDataKeyOrDefault extends ThreeArgFunction {
        
        @Override
        public LuaValue call(LuaValue lplayer, LuaValue lkey, LuaValue ldef) {
            OfflinePlayer player = (OfflinePlayer) check(lplayer);
            String key = lkey.checkjstring();
            Object def = check(ldef);
            
            Object val = builder.getDataKeyOrDefault(player, key, def);
            
            return CoerceJavaToLua.coerce(val);
        }
    }
    
    class getDataKey extends TwoArgFunction {
        
        @Override
        public LuaValue call(LuaValue lplayer, LuaValue lkey) {
            OfflinePlayer player = (OfflinePlayer) check(lplayer);
            String key = lkey.checkjstring();
            
            Object val = builder.getDataKey(player, key);
            
            return CoerceJavaToLua.coerce(val);
        }
    }
    
    class getData extends OneArgFunction {
        
        @Override
        public LuaValue call(LuaValue lplayer) {
            Map<String, Object> data = builder.getData((OfflinePlayer) check(lplayer));
            
            return CoerceJavaToLua.coerce(data);
        }
    }
    
    class setDataKey extends ThreeArgFunction {
        
        @Override
        public LuaValue call(LuaValue lplayer, LuaValue lkey, LuaValue lvalue) {
            OfflinePlayer player = (OfflinePlayer) check(lplayer);
            String key = lkey.checkjstring();
            Object value = check(lvalue);
            
            builder.setDataKey(player, key, value);
            return RegisteredAchievementTable.this;
        }
    }
    
    class setName extends OneArgFunction {
        
        @Override
        public LuaValue call(LuaValue val) {
            String name = val.checkjstring();
            builder.setName(name);
            
            return RegisteredAchievementTable.this;
        }
    }
    
    class setDescription extends OneArgFunction {
        
        @Override
        public LuaValue call(LuaValue val) {
            String desc = val.checkjstring();
            builder.setDescription(desc);
            
            return RegisteredAchievementTable.this;
        }
    }
    
    class setType extends OneArgFunction {
        
        @Override
        public LuaValue call(LuaValue val) {
            String name = val.checkjstring();
            
            if (name != null) {
                if (name.equalsIgnoreCase("Global") || name.equalsIgnoreCase("Server")) {
                    builder.setType(name);
                }
            }
            
            return RegisteredAchievementTable.this;
        }
    }
    
    class setIsCompatible extends OneArgFunction {
        
        @Override
        public LuaValue call(LuaValue val) {
            if (val.isfunction()) {
                LuaFunction func = (LuaFunction) val;
                
                builder.setIsCompatible(func);
            } else {
                throw new RuntimeException(val.toString() + " is not a function");
            }
            return RegisteredAchievementTable.this;
        }
    }
    
    class setTestFunction extends OneArgFunction {
        
        @Override
        public LuaValue call(LuaValue val) {
            if (val.isfunction()) {
                LuaFunction func = (LuaFunction) val;
                
                builder.setTest(func);
            } else {
                throw new RuntimeException(val.toString() + " is not a function");
            }
            
            return RegisteredAchievementTable.this;
        }
    }
    
    class getAchievement extends LibFunction {
        
        @Override
        public LuaValue call() {
            if (registeredAchievement != null && registeredAchievementLua != null) {
                return registeredAchievementLua;
            } else {
                throw new LuaError("Achievement hasnt been registered yet.");
            }
        }
    }
    
    class getAchievementByNameOrId extends VarArgFunction {
        
        @Override
        public Varargs invoke(Varargs args) {
            LuaValue first = args.arg1();
            RegisteredAchievement ach = null;
            
            if (first.isint() || first.islong()) {
                long id = first.checklong();
                
                ach = AchievementApi.getRegisteredAchievement(id);
            } else if (first.isstring()) {
                String name = first.checkjstring();
                
                ach = AchievementApi.getRegisteredAchievement(name);
            }
            
            return ach == null ? null : CoerceJavaToLua.coerce(ach);
        }
    }
    
    class register extends LibFunction {
        
        @Override
        public LuaValue call() {
            if (registeredAchievement != null && registeredAchievementLua != null) {
                return registeredAchievementLua;
            }
            LuaAchievement ach = null;
            try {
                ach = builder.build(); 
            } catch (Exception e) {
                throw new LuaError(e.getMessage());
            }
            
            RegisteredAchievement registered = AchievementApi.registerAchievement(ach);
            
            boolean r = registered != null;
            
            if (r) {
                registeredAchievement = registered;
                registeredAchievementLua = CoerceJavaToLua.coerce(registered);
            }
            
            return r ? registeredAchievementLua : null;
        }
    }
    
    protected static Object check(LuaValue arg) {
        if (arg.isstring()) {
            return arg.checkjstring();
        } else if (arg.isboolean()) {
            return arg.checkboolean();
        } else if (arg.isuserdata()) {
            if (arg.isuserdata(OfflinePlayer.class)) {
                return arg.checkuserdata(OfflinePlayer.class);
            }
            
            return arg.checkuserdata();
        } else if (arg.isint()) {
            return arg.checkint();
        }

        return arg;
    }
}
