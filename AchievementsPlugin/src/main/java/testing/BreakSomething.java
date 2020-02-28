/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package testing;

import codes.goblom.achievements.api.Achievement;
import org.bukkit.GameMode;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

/**
 *
 * @author Goblom
 */
public class BreakSomething extends Achievement {

    public BreakSomething() {
        super("You broke me!", "Break a block in survival.", Type.SERVER);
    }

    @Override
    public boolean isCompatibleEvent(Event event) {
        return event instanceof BlockBreakEvent;
    }

    @Override
    public boolean test(Event e) {
        BlockBreakEvent event = (BlockBreakEvent) e;
        
        return event.getPlayer().getGameMode() != GameMode.CREATIVE;
    }
    
}
