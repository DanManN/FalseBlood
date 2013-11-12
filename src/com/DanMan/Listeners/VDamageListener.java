/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author DAY
 */
public class VDamageListener implements Listener {

    Vampire vamp;
    FalseBlood plugin;
    Player player;

    public VDamageListener(FalseBlood plug) {
        plugin = plug;
    }

    @EventHandler
    //listener for suffacation
    public void onVampIsDamaged(EntityDamageEvent evt) {
        if (evt.getEntity() instanceof Player) {
            player = (Player) evt.getEntity();
            if (Vampire.isVampire(player.getName(), plugin)) {
                //vampires don't need to breath
                if (evt.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                    evt.setCancelled(true);
                }
            }
        }
    }
}