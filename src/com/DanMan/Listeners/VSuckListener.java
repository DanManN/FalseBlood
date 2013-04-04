/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import com.DanMan.utils.GeneralUtils;
import com.DanMan.utils.SNLMetaData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author DAY
 */
public class VSuckListener implements Listener {

    FalseBlood plugin;
    Vampire vamp;

    public VSuckListener(FalseBlood plug) {
        plugin = plug;
    }

    @EventHandler
    public void onVampAttackPlayer(EntityDamageByEntityEvent evt) {
        Entity damager = evt.getDamager();
        Entity damaged = evt.getEntity();
        if (damager instanceof Player && damaged instanceof Player) {
            Player patak = (Player) damager;
            Player pdefend = (Player) damaged;
            if (Vampire.isVampire(patak.getName(), plugin)) {
                vamp = SNLMetaData.getMetadata(patak, plugin);
                if (!Vampire.isVampire(pdefend.getName(), plugin) && vamp.isBloodSucking()) {
                    pdefend.setFoodLevel(pdefend.getFoodLevel() - 1);
                    vamp.setBloodLevel(vamp.getBloodLevel() + 2);
                    patak.setSaturation(patak.getSaturation() + 2.4F);
                    if (pdefend.getFoodLevel() == 2) {
                        patak.sendMessage(ChatColor.RED + "As you drain your victim their heart-beat slows.");
                        patak.sendMessage(ChatColor.RED + "You know they have just enough blood to stay alive.");
                    } else if (pdefend.getFoodLevel() == 0) {
                        patak.sendMessage(ChatColor.RED + "You have completely endulged in your victim's blood.");
                        patak.sendMessage(ChatColor.RED + "If you wish to make your victim a child vampire, right-click to give them your blood");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onVampAttackOther(EntityDamageByEntityEvent evt) {
        Entity damager = evt.getDamager();
        Entity damaged = evt.getEntity();
        if (damager instanceof Player) {
            Player patak = (Player) damager;
            if (Vampire.isVampire(patak.getName(), plugin)) {
                vamp = SNLMetaData.getMetadata(patak, plugin);
                if (vamp.isBloodSucking()) {
                    if (damaged instanceof Villager) {
                        vamp.setBloodLevel(vamp.getBloodLevel() + 3);
                        patak.setSaturation(patak.getSaturation() + 4.8F);
                        if (GeneralUtils.random(0.2)) {
                            patak.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 0));
                        }
                    } else if (damaged instanceof Zombie) {
                        vamp.setBloodLevel(vamp.getBloodLevel() + 1);
                        patak.setSaturation(patak.getSaturation() + 0.2F);
                        if (GeneralUtils.random(0.7)) {
                            patak.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 0));
                        }
                    } else if (damaged instanceof Enderman) {
                        vamp.setBloodLevel(vamp.getBloodLevel() + 5);
                        patak.setSaturation(patak.getSaturation() + 6);
                        if (GeneralUtils.random(0.2)) {
                            patak.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 0));
                        }
                    }
                }
            }
        }
    }
    //use clock to change vampire modes

    @EventHandler
    public void onVampRightClick(PlayerInteractEvent evt) {
        if ((evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Player player = evt.getPlayer();
            if (player.getItemInHand().getType() == Material.WATCH) {
                if (Vampire.isVampire(player.getName(), plugin)) {
                    vamp = SNLMetaData.getMetadata(player, plugin);
                    if (!vamp.isBloodSucking()) {
                        vamp.setBloodSucking(true);
                        player.sendMessage(ChatColor.RED + "You are ready to feast on blood.");
                    } else {
                        vamp.setBloodSucking(false);
                        player.sendMessage(ChatColor.RED + "You are ready to kick natures ass.");
                    }
                }
            }
        }
    }
}
