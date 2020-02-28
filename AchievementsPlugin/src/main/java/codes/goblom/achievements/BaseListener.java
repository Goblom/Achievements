/*
 * Copyright 2015 Goblom.
 * 
 * All Rights Reserved unless otherwise explicitly stated.
 */
package codes.goblom.achievements;

import codes.goblom.achievements.api.events.AchievementAwardedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Goblom
 */
public abstract class BaseListener implements Listener {
    
    protected final void testAsync(final Player player, final Event event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                test(player, event);
            }
        }.runTaskAsynchronously(AchievementPlugin.getPlugin());
    }
    
    protected final void test(final Player player, Event event) {
        AchievementApi.getLocallyRegisteredAchievements().stream().filter((ach) -> (ach.isCompatibleEvent(event) && !ach.hasUnlocked(player))).filter((ach) -> (ach.test(event))).forEach((ach) -> {
//            AchievementAwardedEvent aae = new AchievementAwardedEvent(ach.getRegisteredStat(), player, AchievementApi.getLocallyRegisteredAchievement(ach.getName()));
//            Bukkit.getPluginManager().callEvent(aae);

            new BukkitRunnable() {
                @Override
                public void run() {
//                    if (aae.isCancelled()) {
//                        return;
//                    }

                    try {
                        ach.getRegisteredStat().award(player);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }.runTaskLater(AchievementPlugin.getPlugin(), 1);
            
        });
    }
}
