/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.api;

import java.util.function.Predicate;
import org.bukkit.event.Event;

/**
 * BE AWARE THIS CAN BE RAN ASYNC
 * 
 * @author Goblom
 */
interface AchievementCallback extends Predicate<Event> {
    
    // Should use with instanceof
    public boolean isCompatibleEvent(Event event);
}
