/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.Listeners;

import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.Vampire;
import com.DanMan.FalseBlood.utils.GeneralUtils;
import com.DanMan.FalseBlood.utils.SNLMetaData;
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
            if (Vampire.isVampire(pdefend.getUniqueId(), plugin)) {
                vamp = SNLMetaData.getMetadata(pdefend, plugin);
                //stake booleans
                ItemStack stake = patak.getInventory().getItemInMainHand();
                boolean wSword = stake.getType() == Material.WOOD_SWORD;
                boolean wAxe = stake.getType() == Material.WOOD_AXE;
                boolean wHoe = stake.getType() == Material.WOOD_HOE;
                boolean wPick = stake.getType() == Material.WOOD_PICKAXE;
                boolean wshovel = stake.getType() == Material.WOOD_SPADE;
                boolean torch = stake.getType() == Material.TORCH;
                boolean rTorchoff = stake.getType() == Material.REDSTONE_TORCH_OFF;
                boolean rTorchon = stake.getType() == Material.REDSTONE_TORCH_ON;
                boolean stick = stake.getType() == Material.STICK;
                boolean fence = stake.getType().toString().contains("FENCE");
                boolean sign = stake.getType() == Material.SIGN;
                boolean signPost = stake.getType() == Material.SIGN_POST;
                boolean fishRod = stake.getType() == Material.FISHING_ROD;
                boolean carrotStick = stake.getType() == Material.CARROT_STICK;
                boolean armorStand = stake.getType() == Material.ARMOR_STAND;
                boolean banner = stake.getType() == Material.BANNER;
                //combine stake booleans
                boolean hasWW = wSword || wAxe || wHoe || wPick || wshovel || torch || rTorchoff || rTorchon || rTorchon || fence || stick 
			|| sign || signPost || fishRod || carrotStick || armorStand || banner;

                //vamp wears armor booleans
                ItemStack boots = pdefend.getInventory().getBoots();
                ItemStack pants = pdefend.getInventory().getLeggings();
                ItemStack chestplate = pdefend.getInventory().getChestplate();
                ItemStack helmet = pdefend.getInventory().getHelmet();
                if (hasWW) {
                    double percent = 84;
                    //if (missingBoots) {
                    //    percent = percent + 0.17;
                    //}
                    //if (missingPants) {
                    //    percent = percent + 0.2;
                    //}
                    //if (missingChestPlate) {
                    //    percent = percent + 0.3;
                    //}
                    //if (missingHelmet) {
                    //    percent = percent + 0.17;
                    //}
		    percent -= boots != null ? 0.17 * (1 - (boots.getDurability()/boots.getType().getMaxDurability())) : 0;
		    percent -= pants != null ? 0.2 * (1 - (pants.getDurability()/pants.getType().getMaxDurability())) : 0;
		    percent -= chestplate != null ? 0.3 * (1 - (chestplate.getDurability()/chestplate.getType().getMaxDurability())) : 0;
		    percent -= helmet != null ? 0.17 * (1 - (helmet.getDurability()/helmet.getType().getMaxDurability())) : 0;
		    System.out.println("Percent: " + percent);
                    if (GeneralUtils.random(percent)) {
                        vamp.setVampire(false);
                        pdefend.setHealth(0);
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
            if (Vampire.isVampire(pdefend.getUniqueId(), plugin)) {
                vamp = SNLMetaData.getMetadata(pdefend, plugin);
                //silver booleans
                ItemStack silver = patak.getInventory().getItemInMainHand();
                //boolean gSword = silver.getType() == Material.GOLD_SWORD;
                //boolean gAxe = silver.getType() == Material.GOLD_AXE;
                //boolean gHoe = silver.getType() == Material.GOLD_HOE;
                //boolean gPick = silver.getType() == Material.GOLD_PICKAXE;
                //boolean gshovel = silver.getType() == Material.GOLD_SPADE;
                //boolean gBlock = silver.getType() == Material.GOLD_BLOCK;
                //boolean gIngot = silver.getType() == Material.GOLD_INGOT;
                //boolean gApple = silver.getType() == Material.GOLDEN_APPLE;
                //boolean gNugget = silver.getType() == Material.GOLD_NUGGET;
                //boolean gCarrot = silver.getType() == Material.GOLDEN_CARROT;
                //boolean gBoots = silver.getType() == Material.GOLD_BOOTS;
                //boolean gPants = silver.getType() == Material.GOLD_LEGGINGS;
                //boolean gChestPlate = silver.getType() == Material.GOLD_CHESTPLATE;
                //boolean gHelmet = silver.getType() == Material.GOLD_HELMET;
                //combine silver booleans
                boolean hasGW = silver.getType().toString().startsWith("GOLD");//gSword || gAxe || gHoe || gPick || gshovel || gBlock || gIngot || gApple || gNugget || gCarrot || gBoots || gPants || gChestPlate || gHelmet;
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
            if (Vampire.isVampire(patak.getUniqueId(), plugin)) {
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
