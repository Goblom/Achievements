/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.api;

import java.util.Map;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import com.google.common.collect.Maps;

/**
 *
 * @author Goblom
 */
public abstract class Achievement extends AchievementData implements AchievementCallback {

    private final Map<UUID, Map<String, Object>> tempData = Maps.newConcurrentMap();
    
    public Achievement(String name, String description) {
        super(name, description);
    }
    
    public Achievement(String name, String description, Type type) {
        super(name, description, type);
    }
    
    private final void testData(OfflinePlayer player) {
        if (!tempData.containsKey(player.getUniqueId())) {
            tempData.put(player.getUniqueId(), Maps.<String, Object>newConcurrentMap());
        }
    }
    
    /**
     * Please use {@link RegisteredStat#award(org.bukkit.OfflinePlayer) instead
     * @deprecated
     */
    @Deprecated
    public void award(OfflinePlayer player) {
        getRegisteredStat().award(player);
    }
    
    public Map<String, Object> getData(OfflinePlayer player) {
        testData(player);
        return tempData.get(player.getUniqueId());
    }
    
    public <T> T getDataKeyOrDefault(OfflinePlayer player, String key, T def) {
        T val = getDataKey(player, key);
        
        if (val == null) {
            val = setDataKey(player, key, def);
        }
        
        return val;
    }
    
    public <T> T getDataKey(OfflinePlayer player, String key) {
        testData(player);
        return (T) getData(player).get(key);
    }
    
    public <T> T setDataKey(OfflinePlayer player, String key, T value) {
        testData(player);
        getData(player).put(key, value);
        return value;
    }
    
    public <T> T removeDataKey(OfflinePlayer player, String key) {
        testData(player);
        return (T) getData(player).remove(key);
    }
}
