/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.main;

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
        age = (vamp.getAge() / 2) + 10;
        agebuff = age > 35 ? 35 : age;
        if (player.isSprinting()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, agebuff), true);
            agebuff = agebuff == 0 ? 1 : agebuff;
            player.setExhaustion(player.getExhaustion() + (2.5F / agebuff));
        }
    }

    public static void vampHealthMngr(Vampire vamp, Player player) {
        age = vamp.getAge() / 5;
        agebuff = age > 10 ? 10 : age;
        int damageTaken = (int) (20 - player.getHealth());
        int duration = (50 * damageTaken) / (agebuff + 1);
        if (player.getHealth() <= 19) {
            if (player.getFoodLevel() > 0 && !player.hasPotionEffect(PotionEffectType.REGENERATION)) {               
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration, agebuff), true);
                agebuff = agebuff == 0 ? 1 : agebuff;
                player.setExhaustion(player.getExhaustion() + ((damageTaken * 4)/ agebuff));
            }
        } else {
            player.removePotionEffect(PotionEffectType.REGENERATION);
        }
    }

    public static void vampStrengthMngr(Vampire vamp, Player player) {
        age = vamp.getAge() / 10;
        agebuff = age > 5 ? 5 : age;
        if (player.getFoodLevel() > 0 && !vamp.isBloodSucking()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, agebuff), true);
        }
    }

    public static void vampHasteMngr(Vampire vamp, Player player) {
        age = vamp.getAge() / 5;
        agebuff = age > 10 ? 10 : age;
        if (player.getFoodLevel() > 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, agebuff), true);
        }
    }

    public static void vampTouchGold(Player player) {
        Location ploc = player.getLocation();
        //get block at player location
        Block block1 = ploc.getBlock();
        Block block2 = ploc.subtract(0, 1, 0).getBlock();
        ItemStack boot = player.getInventory().getBoots();
        Material boots = boot == null ? Material.AIR : boot.getType();
        boolean goldstuff = (block2.getType() == Material.GOLD_BLOCK) || (block1.getType() == Material.GOLD_PLATE);
        if (goldstuff && boots == Material.AIR) {
            player.setFireTicks(40);
        }
    }
}
