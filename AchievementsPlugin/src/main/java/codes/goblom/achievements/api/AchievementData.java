/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.api;

import codes.goblom.achievements.AchievementPlugin;
import codes.goblom.achievements.api.achievement.RegisteredAchievement;
import codes.goblom.achievements.misc.ExpiringMap;
import codes.goblom.achievements.model.UnlockedModel;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import com.avaje.ebean.ExpressionList;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Goblom
 */
public class AchievementData {
    private static Map<UUID, Map<Long, Boolean>> UNLOCKED_STORAGE = Maps.newConcurrentMap();
    
    @Getter
    private final @NonNull String name, description;
    @Getter
    private final @NonNull Type type;
    private RegisteredAchievement stat = null;
    
    protected AchievementData(String name, String description) {
        this(name, description, Type.GLOBAL);
    }
    
    protected AchievementData(@NonNull String name, @NonNull String description, Type type) {
        this.name = ChatColor.stripColor(name);
        this.description = ChatColor.stripColor(description);
        this.type = type == null ? Type.GLOBAL : type;
    }
    
    /**
     * This does not affect the global achievement database
     */
    protected static void setLocalUnlocked(OfflinePlayer player, long id, boolean unlocked) {
        if (!UNLOCKED_STORAGE.containsKey(player.getUniqueId())) {
            UNLOCKED_STORAGE.put(player.getUniqueId(), ExpiringMap.builder().expiration(5, TimeUnit.MINUTES).build());
        }
        
        Map<Long, Boolean> storage = UNLOCKED_STORAGE.get(player.getUniqueId());
        storage.put(id, unlocked);
    }
    
    /**
     * This does not affect the global achievement database
     */
    protected void setLocalUnlocked(OfflinePlayer player, boolean unlocked) {
        if (!UNLOCKED_STORAGE.containsKey(player.getUniqueId())) {
            UNLOCKED_STORAGE.put(player.getUniqueId(), ExpiringMap.builder().expiration(5, TimeUnit.MINUTES).build());
        }
        
        Map<Long, Boolean> storage = UNLOCKED_STORAGE.get(player.getUniqueId());
        storage.put(getRegisteredStat().getId(), unlocked);
    }
    
    public boolean hasUnlocked(OfflinePlayer player) {
        if (!UNLOCKED_STORAGE.containsKey(player.getUniqueId())) {
            UNLOCKED_STORAGE.put(player.getUniqueId(), ExpiringMap.builder().expiration(5, TimeUnit.MINUTES).build());
            
            return false;
        }
        
        Map<Long, Boolean> storage = UNLOCKED_STORAGE.get(player.getUniqueId());
        if (storage.containsKey(getRegisteredStat().getId())) {
            return storage.get(getRegisteredStat().getId());
        }
        
        ExpressionList<UnlockedModel> query = AchievementPlugin.getPlugin().getDatabase().find(UnlockedModel.class).where()
                .ieq("uuid", player.getUniqueId().toString())
                .ieq("achievement_id", String.valueOf(getRegisteredStat().getId()));
                if (getType() != AchievementData.Type.GLOBAL) {
                    query.ieq("unlocked_server", AchievementPlugin.getPlugin().getEnglishServerName());
                }
        
        UnlockedModel model = query.findUnique();
        
        if (model != null) {
            storage.put(getRegisteredStat().getId(), Boolean.TRUE);
        }
        
        return model != null;
    }
    
    public String getServer() {
        return type == Type.GLOBAL ? "Global" : AchievementPlugin.getPlugin().getEnglishServerName();
    }
    
    public void setStat(RegisteredAchievement stat) {
        if (this instanceof RegisteredAchievement) {
            return;
        }
        
        AchievementPlugin.getPlugin().debug("Setting stat for [" + getName() + "] with [" + stat.getName() + "]");
        
        if (this.stat != null) {
            throw new RuntimeException("Achievement[" + getName() + "] already has a RegisteredStat attached to it");
        }
        
        this.stat = stat;
    }
    
    // When being called locally you should check if the class is instanceof RegisterdStat so you do not cause null pointers
    public RegisteredAchievement getRegisteredStat() {
        if (this instanceof RegisteredAchievement) {
            return (RegisteredAchievement) this;
        }
        
        if (this.stat == null) {
            throw new RuntimeException("Achievement[" + getName() + "] is not yet registered.");
        }
        
        if (!this.stat.getName().equals(getName()) || !this.stat.getDescription().equals(getDescription())) {
            throw new RuntimeException("Achievement[" + getName() + "] was not registered correctly.");
        }
        
        return this.stat;
    }
    
    public static enum Type {
        GLOBAL, SERVER;
    }
}
