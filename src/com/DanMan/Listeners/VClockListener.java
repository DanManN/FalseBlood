/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
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
        Material watch = evt.getCurrentItem().getType();
        if (Vampire.isVampire(player.getName(), plugin) && watch == Material.WATCH) {
            evt.setCancelled(true);
        }
    }
    //making sure the clock doesn't dissapear

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVampDropClock(PlayerDropItemEvent evt) {
        player = (Player) evt.getPlayer();
        if (Vampire.isVampire(player.getName(), plugin) && evt.getItemDrop().getItemStack().getType() == Material.WATCH) {
            evt.setCancelled(true);
        }
    }
    //making sure we dont have too many clocks

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVampPickUpClock(PlayerPickupItemEvent evt) {
        player = evt.getPlayer();
        if (Vampire.isVampire(player.getName(), plugin) && evt.getItem().getItemStack().getType() == Material.WATCH) {
            evt.setCancelled(true);
        }
    }
    //giving another clock after death

    @EventHandler
    public void onVampRespawn(PlayerRespawnEvent evt) {
        player = evt.getPlayer();
        if (Vampire.isVampire(player.getName(), plugin)) {
            Vampire.addClock(player);
        }
    }
}
