/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package testing;

import codes.goblom.achievements.api.Achievement;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author Goblom
 */
public class SoYouArentMute extends Achievement {

    public SoYouArentMute() {
        super("So your aren't mute.", "Speak your first words.");
    }

    @Override
    public boolean isCompatibleEvent(Event event) {
        return event instanceof AsyncPlayerChatEvent;
    }

    @Override
    public boolean test(Event t) {
        return true;
    }
    
}
