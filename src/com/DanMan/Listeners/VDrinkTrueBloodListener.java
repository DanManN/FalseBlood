/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import com.DanMan.utils.SNLMetaData;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
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
                ItemStack tb = player.getItemInHand();
                if (tb.getType() == Material.POTION) {
                    if (tb.getDurability() == 59) {
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
//    //temporary method
//    @EventHandler
//    public void onUseFurnace(InventoryClickEvent evt) {
//        Inventory i = evt.getInventory();
////        System.out.println("Invenotry Type: " + i.getType());
//        if (i.getType() == InventoryType.FURNACE) {
////            System.out.println("Slot #" + evt.getSlot());
//            FurnaceInventory fi = (FurnaceInventory) i;
//            ItemStack water = new ItemStack(Material.POTION, 1, (short) 0);
//            ItemStack eeye = new ItemStack(Material.EYE_OF_ENDER);
//            if (fi.getFuel() != null && fi.getSmelting() != null) {
////                System.out.println("Has EnderEye: " + (fi.getFuel().getType() == eeye.getType()));
////                System.out.println("Has Water: " + (fi.getSmelting().getType() == water.getType()));
//                if (fi.getFuel().getType() == eeye.getType() && fi.getSmelting().getType() == water.getType()) {
//                    fi.remove(eeye);
//                    fi.remove(water);
//                    fi.setResult(trueblood);
//                }
//            }
//        }
//    }

    @EventHandler
    public void onBrewPotion(PlayerInteractEvent evt) {
        if (evt.hasBlock()) {

            //System.out.println("Triggered");

            player = evt.getPlayer();
            ItemStack inHand = player.getItemInHand();
            Block caul = evt.getClickedBlock();
            if (caul.getType() == Material.CAULDRON && inHand.getType() == Material.GLASS_BOTTLE) {
                Cauldron c = (Cauldron) caul.getState().getData();
                Block hop = caul.getRelative(BlockFace.UP);

                //System.out.println("Hopper: " + hop);

                if (!c.isEmpty() && (hop.getType() == Material.HOPPER)) {

                    //System.out.println("Hopper exists");

                    Hopper h = (Hopper) hop.getState();
                    Inventory hopi = h.getInventory();
                    ItemStack iEye = hopi.getItem(hopi.first(Material.EYE_OF_ENDER));
                    if (iEye != null) {
                        if (iEye.getAmount() > 1) {
                            iEye.setAmount(iEye.getAmount() - 1);
                        } else {
                            hopi.clear(hopi.first(iEye));
                        }
                        //System.out.println("Item exists in proper location");
                        //evt.setCancelled(true);
                        ItemStack trueblood = new ItemStack(Material.POTION, 1, (short) 59);
                        ItemMeta meta = trueblood.getItemMeta();
                        meta.setDisplayName(ChatColor.DARK_RED + "TrueBlood");
                        List<String> lore = new ArrayList<String>();
                        lore.clear();
                        lore.add("Replenishes 2 vampire blood bars.");
                        meta.setLore(lore);
                        trueblood.setItemMeta(meta);
//                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//
//                            @Override
//                            public void run() {
                                if (inHand.getAmount() > 1) {
                                    ItemStack less = new ItemStack(inHand.getType(), (inHand.getAmount() - 1));
                                    player.setItemInHand(less);
                                } else {
                                    player.setItemInHand(trueblood);
                                }
//                            }
//                        }, 1);
                        c.setData((byte) (c.getData() - 1));
                        evt.setCancelled(true);
                        player.updateInventory();
                    }
                }
            }
        }
    }
}
