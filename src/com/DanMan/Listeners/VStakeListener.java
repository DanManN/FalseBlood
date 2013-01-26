/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
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
                ItemStack boots = patak.getInventory().getBoots();
                ItemStack pants = patak.getInventory().getLeggings();
                ItemStack chestplate = patak.getInventory().getChestplate();
                ItemStack helmet = patak.getInventory().getHelmet();
                boolean missingArmor = boots == null || pants == null || chestplate == null || helmet == null;
                if (hasWW && missingArmor) {
                    pdefend.setHealth(0);
                    vamp.setVampire(false);
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
                boolean wSword = silver.getType() == Material.GOLD_SWORD;
                boolean wAxe = silver.getType() == Material.GOLD_AXE;
                boolean wHoe = silver.getType() == Material.GOLD_HOE;
                boolean wPick = silver.getType() == Material.GOLD_PICKAXE;
                boolean wshovel = silver.getType() == Material.GOLD_SPADE;
                //combine stake booleans
                boolean hasGW = wSword || wAxe || wHoe || wPick || wshovel;
                //vamp wears armor booleans
                ItemStack boots = patak.getInventory().getBoots();
                ItemStack pants = patak.getInventory().getLeggings();
                ItemStack chestplate = patak.getInventory().getChestplate();
                ItemStack helmet = patak.getInventory().getHelmet();
                boolean hasboots = boots != null; 
                boolean haspants = pants != null; 
                boolean haschestplate = chestplate != null; 
                boolean hashelmet = helmet != null;
                if (hasGW) {
                    evt.setDamage(evt.getDamage() + 4);
                    pdefend.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
                    if(hasboots) boots.setDurability((short)(boots.getDurability() - 15));
                    if(haspants) pants.setDurability((short)(pants.getDurability() - 15));
                    if(haschestplate) chestplate.setDurability((short)(chestplate.getDurability() - 15));
                    if(hashelmet) helmet.setDurability((short)(helmet.getDurability() - 15));
                }
            }
        }
    }
}
