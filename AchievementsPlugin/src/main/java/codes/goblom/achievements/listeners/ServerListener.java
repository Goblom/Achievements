/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.listeners;

import codes.goblom.achievements.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerCommandEvent;

/**
 *
 * @author Goblom
 */
public class ServerListener extends BaseListener {
    
    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        if (event.getSender() instanceof Player) {
            test((Player) event.getSender(), event);
        }
    }
}
