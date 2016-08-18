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

    public static boolean getDay(Player player) {
        long time = player.getWorld().getTime();
        return time < 13000 || time > 23000;
	//return time < 12300 || time > 23850;

    }

    public static int lightLevel(Player player) {
        Location ploc = player.getLocation();
        //get block at player location
        Block block = ploc.getBlock();
        int lightlevel = block.getLightFromSky();
        return lightlevel;

    }

    public static void vSunBurn(Player player) {
        if (SunTime.getDay(player) && player.getWorld().getEnvironment() == World.Environment.NORMAL) {
            int ll = lightLevel(player);
            int dmg = ll / 5;
            EntityDamageEvent burn = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.FIRE_TICK, dmg);
            if (ll > 4) {
                player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 100);
                Bukkit.getServer().getPluginManager().callEvent(burn);
                player.damage(dmg);
                if (ll == 15) {
                    player.setFireTicks(21);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 0), false);
                }
            }
        }
//        System.out.println("Light Level: " + ll);
//        System.out.println("Damage : " + dmg);
    }
}
