/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package testing;

import codes.goblom.achievements.api.Achievement;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 *
 * @author Goblom
 */
public class BlameGoblom extends Achievement {

    public BlameGoblom() {
        super("Blame Goblom", "He is the source of our problems.");
    }

    @Override
    public boolean isCompatibleEvent(Event event) {
        return event instanceof AsyncPlayerChatEvent;
    }

    @Override
    public boolean test(Event t) {
        AsyncPlayerChatEvent event = (AsyncPlayerChatEvent) t;
        return event.getMessage().contains("#BlameGoblom");
    }
}
