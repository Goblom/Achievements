/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.listeners;

import codes.goblom.achievements.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

/**
 *
 * @author Goblom
 */
public class VehicleListener extends BaseListener {
    
    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player) {
            test((Player) event.getEntered(), event);
        }
    }
    
    @EventHandler
    public void onVechileDamage(VehicleDamageEvent event) {
        if (event.getAttacker() instanceof Player) {
            test((Player) event.getAttacker(), event);
        }
    }
    
    @EventHandler
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        if (event.getAttacker() instanceof Player) {
            test((Player) event.getAttacker(), event);
        }
    }
    
    @EventHandler
    public void onVehicleEntityCollision(VehicleEntityCollisionEvent event) {
        if (event.getEntity() instanceof Player) {
            test((Player) event.getEntity(), event);
        }
    }
    
    @EventHandler
    public void onVehicleExit(VehicleExitEvent event) {
        if (event.getExited() instanceof Player) {
            test((Player) event.getExited(), event);
        }
    }
}
