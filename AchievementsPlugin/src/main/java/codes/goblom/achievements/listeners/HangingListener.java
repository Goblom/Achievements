/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.listeners;

import codes.goblom.achievements.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

/**
 *
 * @author Goblom
 */
public class HangingListener extends BaseListener {
    
    @EventHandler
    public void onHangingPlace(HangingPlaceEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
        if (event.getRemover() instanceof Player) {
            test((Player) event.getRemover(), event);
        }
    }
}
