/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.main;

import com.DanMan.utils.VampTrackerTasks;
import com.DanMan.utils.Stats;
import com.DanMan.utils.SunTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author DAY
 */
public class VampTracker {

    public static void startVampTracker(final Vampire vamp) {
        FalseBlood plugin = vamp.plugin;
        final Player player = vamp.getPlayer();
        vamp.setBloodSucking(true);
        vampTaskScheduler(vamp, player, plugin);
    }

    public static void stopVampTracker(Vampire vamp) {
        int sId = vamp.sId;
        FalseBlood plugin = vamp.plugin;
        Player player = vamp.getPlayer();
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        player.removePotionEffect(PotionEffectType.WATER_BREATHING);
        if (sId != -1) {
            plugin.getServer().getScheduler().cancelTask(sId);
        } else {
            System.err.println("Error: Could not stop age counter because it never started.");
        }
    }

    public static void vampUnload(Plugin plug) {
        plug.getServer().getScheduler().cancelTasks(plug);
        for (Player player : Bukkit.getOnlinePlayers()) {
            String pname = player.getName();
            if (Vampire.isVampire(pname, plug)) {
                Stats.logMDtoFile(pname, plug);
            }
        }
    }

    public static void vampTaskScheduler(final Vampire vamp, final Player player, Plugin plugin) {

        vamp.sId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            @Override
            public void run() {
                //every tick is one second
                vamp.tick++;
                if (player != null) {
                    afkManager(vamp, player);
                    SunTime.vSunBurn(vamp.getPlayer());
                    VampTrackerTasks.vampTouchGold(player);
                    VampTrackerTasks.vampHealthMngr(vamp, player);
                    VampTrackerTasks.vampFlyMngr(vamp, player);
                    VampTrackerTasks.vampSprintMngr(vamp, player);
                    VampTrackerTasks.vampStrengthMngr(vamp, player);

                    //level up age every hour
                    if (vamp.tick >= 3600 && !vamp.isAfk()) {
                        vamp.addAge(1);
                        vamp.tick = 0;
                    }
                    //add perks
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 240, 0), true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 240, 0), true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 240, 0), true);
                    //let vampire eat
//                    if (vamp.isBloodSucking()) {
//                        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
//                    }
                }
            }
        }, 20, 20);
    }

    public static void afkManager(Vampire vamp, Player player) {
        //check if player is idle
        int idle = 0;
        if (player.getWalkSpeed() > 0) {
            idle = 0;
            if (vamp.isAfk()) {
                vamp.setAfk(false);
            }
        } else {
            idle++;
        }
        if (idle >= 300) {
            if (!vamp.isAfk()) {
                vamp.setAfk(true);
            }
            idle = 0;
        }
    }
}
