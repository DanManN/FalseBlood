/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.utils;

import com.DanMan.main.Vampire;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author DAY
 */
public class VampTrackerTasks {
    
    public static void vampFlyMngr(Vampire vamp, Player player) {
        if (vamp.getAge() >= 50 && !player.getAllowFlight()) {
            player.setAllowFlight(true);
        }
        if (player.getFoodLevel() < 6) {
            player.setFlying(false);
            return;
        }
        int agebuff = vamp.getAge() / 5;
        agebuff = agebuff == 0 ? 1 : agebuff;
        if (player.isFlying()) {
            player.setExhaustion(player.getExhaustion() + (float) (10 / agebuff));
        }
    }
    
    public static void vampSprintMngr(Vampire vamp, Player player) {
        int agebuff = 0;
        if (player.isSprinting()) {
            if (vamp.getAge() >= 50) {
                agebuff = 7;
            } else if (vamp.getAge() >= 40) {
                agebuff = 6;
            } else if (vamp.getAge() >= 30) {
                agebuff = 5;
            } else if (vamp.getAge() >= 20) {
                agebuff = 4;
            } else if (vamp.getAge() >= 15) {
                agebuff = 3;
            } else if (vamp.getAge() >= 10) {
                agebuff = 2;
            } else if (vamp.getAge() >= 5) {
                agebuff = 1;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, agebuff), true);
            agebuff = agebuff == 0 ? 1 : agebuff;
            player.setExhaustion(player.getExhaustion() + 2 * (1 / agebuff));
        }
    }
    
    public static void vampHealthMngr(Vampire vamp, Player player) {
        int age = vamp.getAge() / 10;
        int agebuff = age > 10 ? 10 : age;
        if (player.getHealth() != 20) {
            if (player.getFoodLevel() > 0) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 160, agebuff), true);
                agebuff = agebuff == 0 ? 1 : agebuff;
                player.setExhaustion(player.getExhaustion() + 5 * (1 / agebuff));
            }
        } else {
            player.removePotionEffect(PotionEffectType.REGENERATION);
        }
    }
    
    public static void vampStrengthMngr(Vampire vamp, Player player) {
        int age = vamp.getAge() / 20;
        int agebuff = age > 5 ? 5 : age;
        if (player.getFoodLevel() > 0 && !vamp.isBloodSucking()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, agebuff), true);
            //player.setExhaustion(player.getExhaustion() + (1 / agebuff));
        }
    }
    public static void vampTouchGold(Player player) {
        Location ploc = player.getLocation();
        ploc.subtract(0, 1, 0);
        //get block at player location
        Block block = ploc.getBlock();
        if (block.getType() == Material.GOLD_BLOCK && player.getInventory().getBoots() != null) {
            player.setFireTicks(20);
        }
    }
    
}
