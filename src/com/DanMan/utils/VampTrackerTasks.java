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
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author DAY
 */
public class VampTrackerTasks {

    private static int age;
    private static int agebuff;

    public static void vampFlyMngr(Vampire vamp, Player player) {
        if (vamp.getAge() >= 50) {
            if (!player.getAllowFlight()) {
                player.setAllowFlight(true);
            }
            if (player.getFoodLevel() < 6) {
                player.setFlying(false);
                return;
            }
            agebuff = vamp.getAge() / 10;
            if (player.isFlying()) {
                player.setExhaustion(player.getExhaustion() + (float) (10 / agebuff));
            }
        }
    }

    public static void vampSprintMngr(Vampire vamp, Player player) {
        age = vamp.getAge() / 10;
        agebuff = age > 10 ? 10 : age;
        if (player.isSprinting()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, agebuff), true);
            agebuff = agebuff == 0 ? 1 : agebuff;
            player.setExhaustion(player.getExhaustion() + 2 * (1 / agebuff));
        }
    }

    public static void vampHealthMngr(Vampire vamp, Player player) {
        age = vamp.getAge() / 10;
        agebuff = age > 10 ? 10 : age;
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
        age = vamp.getAge() / 20;
        agebuff = age > 5 ? 5 : age;
        if (player.getFoodLevel() > 0 && !vamp.isBloodSucking()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, agebuff), true);
        }
    }

    public static void vampHasteMngr(Vampire vamp, Player player) {
        age = vamp.getAge() / 10;
        agebuff = age > 10 ? 10 : age;
        if (player.getFoodLevel() > 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, agebuff), true);
        }
    }

    public static void vampTouchGold(Player player) {
        Location ploc = player.getLocation();
        ploc.subtract(0, 1, 0);
        //get block at player location
        Block block = ploc.getBlock();
        ItemStack boot = player.getInventory().getBoots();
        Material boots = boot == null ? Material.AIR : boot.getType();
        if (block.getType() == Material.GOLD_BLOCK && boots == Material.AIR) {
            player.setFireTicks(40);
        }
    }
}
