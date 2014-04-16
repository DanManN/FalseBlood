/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.main;

import com.DanMan.FalseBlood.utils.Stats;
import java.util.UUID;
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
        FalseBlood plugin = vamp.getPlugin();
        final Player player = vamp.getPlayer();
        vamp.setBloodSucking(true);
        vampTaskScheduler(vamp, player, plugin);
    }

    public static void stopVampTracker(Vampire vamp) {
        int sId = vamp.getsId();
        FalseBlood plugin = vamp.getPlugin();
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
            UUID pId = player.getUniqueId();
            if (Vampire.isVampire(pId, plug)) {
                Stats.logMDtoFile(pId, plug);
            }
        }
    }

    public static void vampTaskScheduler(final Vampire vamp, final Player player, Plugin plugin) {

        int sId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            @Override
            public void run() {
                //every vamp tick is one fourth a second
                vamp.setTick(vamp.getTick() + 1);
                if (player != null) {
                    afkManager(vamp, player);
                    SunTime.vSunBurn(player);
                    VampTrackerTasks.vampTouchGold(player);
                    VampTrackerTasks.vampHealthMngr(vamp, player);
                    VampTrackerTasks.vampFlyMngr(vamp, player);
                    VampTrackerTasks.vampSprintMngr(vamp, player);
                    VampTrackerTasks.vampStrengthMngr(vamp, player);
                    //level up age every hour
                    if (vamp.getTick() >= 14400 && !vamp.isAfk()) {
                        vamp.addAge(1);
                        vamp.setTick(0);
                    }
                    //add perks
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 240, 0), true);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 240, 0), true);
                }
            }
        }, 1, 1);
        vamp.setsId(sId);
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
        if (idle >= 1200) {
            if (!vamp.isAfk()) {
                vamp.setAfk(true);
            }
            idle = 0;
        }
    }
}
