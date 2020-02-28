/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.listeners;

import codes.goblom.achievements.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

/**
 *
 * @author Goblom
 */
public class BlockListener extends BaseListener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        test(event.getPlayer(), event);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        test(event.getPlayer(), event);
    }

    @EventHandler
    public void onIgnite(BlockIgniteEvent event) {
        if (event.getPlayer() != null) {
            test(event.getPlayer(), event);
        }
    }

    @EventHandler
    public void onMultiPlace(BlockMultiPlaceEvent event) {
        test(event.getPlayer(), event);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        test(event.getPlayer(), event);
    }
}
