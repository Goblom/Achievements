/*
 * Copyright 2016 Goblom.
 */
package codes.goblom.achievements.lua;

import codes.goblom.achievements.api.AchievementData;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.luaj.vm2.LuaFunction;

/**
 *
 * @author Goblom
 */
@RequiredArgsConstructor( access = AccessLevel.PROTECTED )
class LuaAchievementBuilder {
    
    private final String fileName;
    private final Map<UUID, Map<String, Object>> tempData = Maps.newHashMap();
    
    @Setter
    private String name, 
                   description = "No Description";
    
    @Setter
    private LuaFunction isCompatible, test;
    
    private String type = "GLOBAL";
    
    public void setType(String type) {
        this.type = type.toUpperCase();
    }
    
    public LuaAchievement build() {
        if (name == null || name.isEmpty()) throw new RuntimeException(String.format("Achievement doesnt have a name() [%s]", fileName));
        if (isCompatible == null || test == null) throw new RuntimeException(String.format("Achievement missing required functions. (isCompatible, test) [%s]", fileName));
        if (!isCompatible.call("ServerCommandEvent").isboolean()) throw new RuntimeException(String.format("Achievement does not have correct return data for isCompatible [%s]", fileName));
        LuaAchievement ach = new LuaAchievement(name, description, AchievementData.Type.valueOf(type), isCompatible, test);
        
        if (!tempData.isEmpty()) {
            tempData.forEach((uuid, map) -> {
                map.forEach((key, value) -> {
                    ach.setDataKey(Bukkit.getOfflinePlayer(uuid), key, value);
                });
            });
            
        }
        
        return ach;
    }
    
    // *******************************************
    // Data Stuff, cause complicated
    // *******************************************
    
    private final void testData(OfflinePlayer player) {
        if (!tempData.containsKey(player.getUniqueId())) {
            tempData.put(player.getUniqueId(), Maps.<String, Object>newConcurrentMap());
        }
    }
    
    <T> T getDataKeyOrDefault(OfflinePlayer player, String key, T def) {
        T val = getDataKey(player, key);
        
        if (val == null) {
            val = setDataKey(player, key, def);
        }
        
        return val;
    }
    
    Map<String, Object> getData(OfflinePlayer player) {
        testData(player);
        return tempData.get(player.getUniqueId());
    }
    
    <T> T getDataKey(OfflinePlayer player, String key) {
        testData(player);
        return (T) getData(player).get(key);
    }
    
    <T> T setDataKey(OfflinePlayer player, String key, T value) {
        testData(player);
        getData(player).put(key, value);
        return value;
    }
    
    <T> T removeDataKey(OfflinePlayer player, String key) {
        testData(player);
        return (T) getData(player).remove(key);
    }
}
