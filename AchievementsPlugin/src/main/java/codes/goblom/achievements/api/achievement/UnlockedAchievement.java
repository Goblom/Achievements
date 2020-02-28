/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.api.achievement;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.Getter;
import codes.goblom.achievements.AchievementApi;
import codes.goblom.achievements.model.UnlockedModel;

/**
 *
 * @author Goblom
 */
@Getter
public final class UnlockedAchievement  {

    private final long unlockId;
    private final String playerName;
    private final UUID playerUUID;
    private final String unlockedServer;
    private final Timestamp unlocked;
    
    private RegisteredAchievement registeredStat;
    
    protected UnlockedAchievement(long unlockId, String playerName, UUID playerUUID, String unlockedServer, Timestamp unlocked, RegisteredAchievement registeredStat) {
        this.unlockId = unlockId;
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.unlockedServer = unlockedServer;
        this.unlocked = unlocked;
        this.registeredStat = registeredStat;
    }
    
    public void setStat(RegisteredAchievement stat) {
        if (registeredStat != null) {
            return;
        }
        
        this.registeredStat = stat;
    }
    
    public static UnlockedAchievement fromModel(UnlockedModel model) {
        RegisteredAchievement stat = AchievementApi.getRegisteredAchievement(model.getAchievementId());
        long id = model.getId();
        UUID uuid = UUID.fromString(model.getUuid());
        String unlockedServer = model.getUnlockedServer();
        String playerName = model.getLastKnownName();
        Timestamp unlocked = model.getUnlocked();
        
        return new UnlockedAchievement(id, playerName, uuid, unlockedServer, unlocked, stat);
    }
}
