/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
//import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
//import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

import codes.goblom.achievements.BaseListener;

/**
 *
 * @author Goblom
 */
public class PlayerListener extends BaseListener {
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        test(event.getPlayer(), event);
    }
    
//    @EventHandler
//    public void onAchievementAwarded(PlayerAchievementAwardedEvent event) {
//        test(event.getPlayer(), event);
//    }
    
    @EventHandler
    public void onAnimation(PlayerAnimationEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onLeaveBed(PlayerBedLeaveEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        test(event.getPlayer(), event);
    }
    
//    @EventHandler
//    public void onPlayerChat2(PlayerChatEvent event) {
//        test(event.getPlayer(), event);
//    }
    
//    @EventHandler
//    public void onTabComplete(PlayerChatTabCompleteEvent event) {
//        test(event.getPlayer(), event);
//    }
    
    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onEditBook(PlayerEditBookEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onEggThrow(PlayerEggThrowEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onFish(PlayerFishEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        test(event.getPlayer(), event);
    }
    
//    @EventHandler
//    public void onInventory(PlayerInventoryEvent event) {
//        test(event.getPlayer(), event);
//    }
    
    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onKick(PlayerKickEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        testAsync(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onRegisterChannel(PlayerRegisterChannelEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onShearEntity(PlayerShearEntityEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onStatisticIncrement(PlayerStatisticIncrementEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onToggleSpring(PlayerToggleSprintEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onUnleashEntity(PlayerUnleashEntityEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onUnregisterChannel(PlayerUnregisterChannelEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onVelocityChange(PlayerVelocityEvent event) {
        testAsync(event.getPlayer(), event);
    }
}
