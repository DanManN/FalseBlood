/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import com.DanMan.utils.GeneralUtils;
import com.DanMan.utils.SNLMetaData;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author DAY
 */
public class VStakeListener implements Listener {

    FalseBlood plugin;
    Vampire vamp;

    public VStakeListener(FalseBlood plug) {
        plugin = plug;
    }

    @EventHandler
    public void onPlayerAttackVampW(EntityDamageByEntityEvent evt) {
        Entity damager = evt.getDamager();
        Entity damaged = evt.getEntity();
        if (damager instanceof Player && damaged instanceof Player) {
            Player patak = (Player) damager;
            Player pdefend = (Player) damaged;
            if (Vampire.isVampire(pdefend.getName(), plugin)) {
                vamp = SNLMetaData.getMetadata(pdefend, plugin);
                //stake booleans
                ItemStack stake = patak.getItemInHand();
                boolean wSword = stake.getType() == Material.WOOD_SWORD;
                boolean wAxe = stake.getType() == Material.WOOD_AXE;
                boolean wHoe = stake.getType() == Material.WOOD_HOE;
                boolean wPick = stake.getType() == Material.WOOD_PICKAXE;
                boolean wshovel = stake.getType() == Material.WOOD_SPADE;
                boolean torch = stake.getType() == Material.TORCH;
                boolean rTorchoff = stake.getType() == Material.REDSTONE_TORCH_OFF;
                boolean rTorchon = stake.getType() == Material.REDSTONE_TORCH_ON;
                boolean fence = stake.getType() == Material.FENCE;
                boolean stick = stake.getType() == Material.STICK;
                //combine stake booleans
                boolean hasWW = wSword || wAxe || wHoe || wPick || wshovel || torch || rTorchoff || rTorchon || rTorchon || fence || stick;
                //vamp wears armor booleans
                ItemStack boots = pdefend.getInventory().getBoots();
                ItemStack pants = pdefend.getInventory().getLeggings();
                ItemStack chestplate = pdefend.getInventory().getChestplate();
                ItemStack helmet = pdefend.getInventory().getHelmet();
                boolean missingBoots = boots == null;
                boolean missingPants = pants == null;
                boolean missingChestPlate = chestplate == null;
                boolean missingHelmet = helmet == null;
                double percent = 0.1;
                if (hasWW) {
                    if (missingBoots) {
                        percent = percent + 0.17;
                    }
                    if (missingPants) {
                        percent = percent + 0.2;
                    }
                    if (missingChestPlate) {
                        percent = percent + 0.3;
                    }
                    if (missingHelmet) {
                        percent = percent + 0.17;
                    }
                    if (GeneralUtils.random(percent)) {
                        pdefend.setHealth(0);
                        vamp.setVampire(false);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerAttackVampG(EntityDamageByEntityEvent evt) {
        Entity damager = evt.getDamager();
        Entity damaged = evt.getEntity();
        if (damager instanceof Player && damaged instanceof Player) {
            Player patak = (Player) damager;
            Player pdefend = (Player) damaged;
            if (Vampire.isVampire(pdefend.getName(), plugin)) {
                vamp = SNLMetaData.getMetadata(pdefend, plugin);
                //silver booleans
                ItemStack silver = patak.getItemInHand();
                boolean gSword = silver.getType() == Material.GOLD_SWORD;
                boolean gAxe = silver.getType() == Material.GOLD_AXE;
                boolean gHoe = silver.getType() == Material.GOLD_HOE;
                boolean gPick = silver.getType() == Material.GOLD_PICKAXE;
                boolean gshovel = silver.getType() == Material.GOLD_SPADE;
                boolean gBlock = silver.getType() == Material.GOLD_BLOCK;
                boolean gIngot = silver.getType() == Material.GOLD_INGOT;
                boolean gApple = silver.getType() == Material.GOLDEN_APPLE;
                boolean gNugget = silver.getType() == Material.GOLD_NUGGET;
                boolean gCarrot = silver.getType() == Material.GOLDEN_CARROT;
                boolean gBoots = silver.getType() == Material.GOLD_BOOTS;
                boolean gPants = silver.getType() == Material.GOLD_LEGGINGS;
                boolean gChestPlate = silver.getType() == Material.GOLD_CHESTPLATE;
                boolean gHelmet = silver.getType() == Material.GOLD_HELMET;
                //combine silver booleans
                boolean hasGW = gSword || gAxe || gHoe || gPick || gshovel || gBlock || gIngot || gApple || gNugget || gCarrot || gBoots || gPants || gChestPlate || gHelmet;
                if (hasGW) {
                    evt.setDamage(evt.getDamage() + 2);
                    pdefend.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 0));
                    pdefend.setFireTicks(20);
                }
            }
        }
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
                //silver armor booleans
                ItemStack boots = pdefend.getInventory().getBoots();
                ItemStack pants = pdefend.getInventory().getLeggings();
                ItemStack chestplate = pdefend.getInventory().getChestplate();
                ItemStack helmet = pdefend.getInventory().getHelmet();
                boolean hasBoots = boots != null ? (boots.getType() == Material.GOLD_BOOTS) : false;
                boolean hasPants = pants != null ?(pants.getType() == Material.GOLD_LEGGINGS) : false;
                boolean hasChestPlate = chestplate != null ?(chestplate.getType() == Material.GOLD_CHESTPLATE) : false;
                boolean hasHelmet = helmet != null ?(helmet.getType() == Material.GOLD_HELMET) : false;
                boolean hasGA = hasBoots || hasPants || hasChestPlate || hasHelmet;
                if (hasGA) {
                    patak.damage(1);
                }
            }
        }
    }
}
