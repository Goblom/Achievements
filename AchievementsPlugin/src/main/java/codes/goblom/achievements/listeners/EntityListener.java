/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements.listeners;

import codes.goblom.achievements.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 *
 * @author Goblom
 */
public class EntityListener extends BaseListener {
    
    Class[] classes = {
        EntityDamageByEntityEvent.class,
        EntityDeathEvent.class,
        EntityDamageEvent.class,
        EntityPortalEnterEvent.class,
        EntityPortalExitEvent.class,
        EntityRegainHealthEvent.class,
        EntityTameEvent.class,
        PlayerDeathEvent.class,
        PlayerLeashEntityEvent.class,
        PotionSplashEvent.class,
        ProjectileHitEvent.class,
        EntityShootBowEvent.class,
        EntityRegainHealthEvent.class
    };
    
    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            test((Player) event.getEntity(), event);
        }
    }
    
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            test((Player) event.getEntity(), event);
        }
    }
    
    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (event.getPotion().getShooter() instanceof Player) {
            test((Player) event.getPotion().getShooter(), event);
        }
    }
    
    @EventHandler
    public void onPlayerLeashEntity(PlayerLeashEntityEvent event) {
        test(event.getPlayer(), event);
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        test(event.getEntity(), event);
    }
    
    @EventHandler
    public void onEntityTame(EntityTameEvent event) {
        if (event.getOwner() instanceof Player) {
            test((Player) event.getOwner(), event);
        }
    }
    
    @EventHandler
    public void onPortalExit(EntityPortalExitEvent event) {
        if (event.getEntity() instanceof Player) {
            test((Player) event.getEntity(), event);
        }
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            test((Player) event.getDamager(), event);
        }
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            test(event.getEntity().getKiller(), event);
        }
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            test((Player) event.getEntity(), event);
        }
    }
    
    @EventHandler
    public void onPortalEnter(EntityPortalEnterEvent event) {
        if (event.getEntity() instanceof Player) {
            test((Player) event.getEntity(), event);
        }
    }
}
