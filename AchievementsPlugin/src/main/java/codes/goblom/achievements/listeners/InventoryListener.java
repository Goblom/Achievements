/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import codes.goblom.achievements.BaseListener;

/**
 *
 * @author Goblom
 */
public class InventoryListener extends BaseListener {
    
    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        test(event.getEnchanter(), event);
    }
    
    @EventHandler
    public void onPreparteEnchant(PrepareItemEnchantEvent event) {
        test(event.getEnchanter(), event);
    }
    
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
            test((Player) event.getPlayer(), event);
        }
    }
    
    //TODO: Figure out InventoryMoveItemEvent
//    @EventHandler
//    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
//        
//    }
    
    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            test((Player) event.getWhoClicked(), event);
        }
    }
    
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            test((Player) event.getWhoClicked(), event);
        }
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            test((Player) event.getPlayer(), event);
        }
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            test((Player) event.getWhoClicked(), event);
        }
    }
    
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            test((Player) event.getWhoClicked(), event);
        }
    }
    
    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        test(event.getPlayer(), event);
    }
}
