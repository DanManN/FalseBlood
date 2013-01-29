/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 *
 * @author DAY
 */
public class VMobNeutralityListener implements Listener {

    FalseBlood plugin;
    Player player;

    public VMobNeutralityListener(FalseBlood plug) {
        plugin = plug;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVampSleep(EntityTargetEvent evt) {
        if (evt.getTarget() instanceof Player && evt.getEntity() instanceof Monster) {
            player = (Player) evt.getTarget();
            Monster mon = (Monster) evt.getEntity();
            if (Vampire.isVampire(player.getName(), plugin)) {
                if (!(mon instanceof Blaze || mon instanceof PigZombie || mon instanceof Silverfish || mon instanceof Witch || mon instanceof Wither)) {
                    return;
                }
                if (mon.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent evt2 = (EntityDamageByEntityEvent) mon.getLastDamageCause();
                    if (evt2.getDamager() instanceof Player) {
                        Player player2 = (Player) evt2.getDamager();
                        if (player2 == player) {
                            return;
                        }
                    }
                }
                evt.setCancelled(true);
            }
        }
    }
}
