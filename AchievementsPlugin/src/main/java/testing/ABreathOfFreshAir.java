/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package testing;

import codes.goblom.achievements.api.Achievement;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author Goblom
 */
public class ABreathOfFreshAir extends Achievement {

    public ABreathOfFreshAir() {
        super("A breath of fresh air", "Join the server for the first time.");
    }

    @Override
    public boolean isCompatibleEvent(Event event) {
        return event instanceof PlayerJoinEvent;
    }

    @Override
    public boolean test(Event t) {
        return true;
    }
    
}
