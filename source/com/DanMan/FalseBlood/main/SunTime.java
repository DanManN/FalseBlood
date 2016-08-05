/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.main;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author DAY
 */
public class SunTime {

    public static boolean getDay() {
        Bukkit.getWorlds().get(0);
        long time = Bukkit.getWorlds().get(0).getTime();
        if (time < 22200 && time > 13800) {
            return false;
        } else {
            return true;
        }
    }

    public static int lightLevel(Player player) {
        Location ploc = player.getLocation();
        //get block at player location
        Block block = ploc.getBlock();
        int lightlevel = block.getLightFromSky();
        return lightlevel;

    }

    public static void vSunBurn(Player player) {
        if (SunTime.getDay() && player.getWorld().getEnvironment() == World.Environment.NORMAL) {
            int ll = lightLevel(player);
            int dmg = ll / 5;
            EntityDamageEvent burn = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.FIRE_TICK, dmg);
            if (ll > 4) {
                player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
                player.damage(dmg);
                Bukkit.getServer().getPluginManager().callEvent(burn);
                if (ll == 15) {
                    player.setFireTicks(21);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20, 0), true);
                }
            }
        }
//        System.out.println("Light Level: " + ll);
//        System.out.println("Damage : " + dmg);
    }
}
