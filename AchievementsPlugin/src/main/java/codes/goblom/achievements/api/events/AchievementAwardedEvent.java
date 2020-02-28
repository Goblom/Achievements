/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.api.events;

import codes.goblom.achievements.api.Achievement;
import codes.goblom.achievements.api.achievement.RegisteredAchievement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Goblom
 */
@RequiredArgsConstructor
public class AchievementAwardedEvent extends Event implements Cancellable {
    
    @Getter
    private static final HandlerList handlerList = new HandlerList();
    
    @Getter @Setter
    private boolean cancelled = false;
    
    @Getter
    private final RegisteredAchievement registeredAchievement;
    
    @Getter
    private final OfflinePlayer offlinePlayer;
    
    /**
     * May be null at times
     */
    private final Achievement achievement;
    
    /**
     * Get Player associated with this event
     */
    public OfflinePlayer getPlayer() {
        return offlinePlayer;
    }
    
    /**
     * May return null if achievement is not locally registered on this server
     */
    public Achievement getAchievement() {
        return achievement;
    }
    
    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }
}
