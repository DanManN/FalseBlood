/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 
package com.DanMan.FalseBlood.Listeners;

import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.Vampire;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author DAY
 */
public class VClockListener implements Listener {

    FalseBlood plugin;
    Player player;
    //making sure the clock doesn't dissapear

    public VClockListener(FalseBlood plug) {
        plugin = plug;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClockMove(InventoryClickEvent evt) {
        player = (Player) evt.getWhoClicked();
        if (evt.getCurrentItem() != null) {
            Material watch = evt.getCurrentItem().getType();
            if (Vampire.isVampire(player.getUniqueId(), plugin) && (watch == Material.WATCH || watch.toString().startsWith("GOLD"))) {
                evt.setCancelled(true);
            }
        }
    }
    //making sure the clock doesn't dissapear

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVampDropClock(PlayerDropItemEvent evt) {
        player = (Player) evt.getPlayer();
        if (Vampire.isVampire(player.getUniqueId(), plugin) && evt.getItemDrop().getItemStack().getType() == Material.WATCH) {
            evt.setCancelled(true);
        }
    }
    //making sure we dont have too many clocks

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVampPickUpClock(PlayerPickupItemEvent evt) {
        player = evt.getPlayer();
        if (Vampire.isVampire(player.getUniqueId(), plugin) && evt.getItem().getItemStack().getType() == Material.WATCH) {
            evt.setCancelled(true);
        }
    }
    //giving another clock after death

    @EventHandler
    public void onVampRespawn(PlayerRespawnEvent evt) {
        player = evt.getPlayer();
        if (Vampire.isVampire(player.getUniqueId(), plugin)) {
            Vampire.addClock(player);
        }
    }
}
