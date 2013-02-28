/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import com.DanMan.utils.SNLMetaData;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_4_R1.block.CraftFurnace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Cauldron;

/**
 *
 * @author DAY
 */
public class VDrinkTrueBloodListener implements Listener {

    FalseBlood plugin;
    Vampire vamp;
    Player player;

    public VDrinkTrueBloodListener(FalseBlood plug) {
        plugin = plug;
    }

    @EventHandler
    public void onDrinkPotion(PlayerInteractEvent evt) {
        player = evt.getPlayer();
        //player.sendMessage("You are currently holding: " + evt.getPlayer().getItemInHand() + "/" + evt.getPlayer().getItemInHand().getDurability());
        if (Vampire.isVampire(player.getName(), null)) {
            if (evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
                vamp = SNLMetaData.getMetadata(player, plugin);
                if (player.getItemInHand().getType() == Material.POTION) {
                    if (player.getItemInHand().getDurability() == 59) {
                        final int slot = player.getInventory().getHeldItemSlot();
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                            @Override
                            public void run() {
                                if (player.getInventory().getItem(slot).getType() != Material.POTION) {
                                    vamp.setBloodLevel(vamp.getBloodLevel() + 4);
                                    player.setSaturation(player.getSaturation() + 4);
                                }
                            }
                        }, 33L);
                    } else {
                        evt.setCancelled(true);
                    }
                }
            }
        }
    }
    ItemStack trueblood = new ItemStack(Material.POTION, 1, (short) 59);
    World world = Bukkit.getWorld("world");

    //temporary method
    @EventHandler
    public void onUseFurnace(InventoryClickEvent evt) {
        Inventory i = evt.getInventory();
        System.out.println("Invenotry Type: " + i.getType());
        if (i.getType() == InventoryType.FURNACE) {
            System.out.println("Slot #" + evt.getSlot());
            FurnaceInventory fi = (FurnaceInventory) i;
            ItemStack water = new ItemStack(Material.POTION, 1, (short) 0);
            ItemStack eeye = new ItemStack(Material.EYE_OF_ENDER);
            if (fi.getFuel() != null && fi.getSmelting() != null) {
                System.out.println("Has EnderEye: " + (fi.getFuel().getType() == eeye.getType()));
                System.out.println("Has Water: " + (fi.getSmelting().getType() == water.getType()));
                if (fi.getFuel().getType() == eeye.getType() && fi.getSmelting().getType() == water.getType()) {
                    fi.remove(eeye);
                    fi.remove(water);
                    fi.setResult(trueblood);
                }
            }
        }
    }

//    @EventHandler
//    public void onBrewPotion(PlayerInteractEvent evt) {
//        if (evt.hasBlock()) {
//            player = evt.getPlayer();
//            ItemStack inHand = player.getItemInHand();
//            Block caul = evt.getClickedBlock();
//            Location cl = caul.getLocation();
//            if (caul.getType() == Material.CAULDRON) {
//                Cauldron cauldron = new Cauldron(caul.getData());
//                //System.out.println("Cauldron Data: " + cauldron.getData());
//                if (cauldron.getData() > 0 && inHand.getType() == Material.GLASS_BOTTLE) {
//                    Item iEye = null;
//                    for (Iterator<Item> it = world.getEntitiesByClass(Item.class).iterator(); it.hasNext();) {
//                        Item item = it.next();
//                        //System.out.println("Item: " + item.getItemStack().toString());
//                        Location iLoc = item.getLocation();
//                        //System.out.println("Item: " + item + "\nLocation: " + iLoc);
//                        Location altILoc = new Location(world, iLoc.getBlockX(), iLoc.getBlockY(), iLoc.getBlockZ());
//                        //System.out.println("Altered Location: " + altILoc + "\nis Equal to Cauldron location: " + (altILoc == cl));
//                        //System.out.println("is iEye: " + (item.getItemStack().getType() == Material.EYE_OF_ENDER));
//                        if (item.getItemStack().getType() == Material.EYE_OF_ENDER && altILoc.equals(cl)) {
//                            //System.out.println("iEye is true");
//                            iEye = item;
//                            break;
//                        }
//                    }
//                    if (iEye != null) {
//                        //System.out.println("Item exists in proper location");
//                        iEye.remove();
//                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//
//                            @Override
//                            public void run() {
//                                ItemStack inHand = player.getItemInHand();
//                                removeOneItem(player, new ItemStack(Material.POTION, 1, (short) 0));
//                                if (inHand.getAmount() > 1) {
//                                    ItemStack less = new ItemStack(inHand.getType(), inHand.getAmount() - 1);
//                                    player.setItemInHand(less);
//                                }
//                                player.getInventory().addItem(trueblood);
//                            }
//                        }, 1);
//                        //world.dropItem(new Location(world, cl.getX(), cl.getY(), cl.getZ() + 1), trueblood);
//                    }
//                }
//            }
//        }
//    }
    public void removeOneItem(Player play, ItemStack item) {
        int itemslot;
        if (play.getInventory().contains(item)) {
            itemslot = play.getInventory().first(item);
        } else {
            return;
        }
        play.getInventory().clear(itemslot);
    }
}
