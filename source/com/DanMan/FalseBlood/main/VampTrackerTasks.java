/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.main;

import org.bukkit.GameMode;
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

	public static void vampFlyMngr(Vampire vamp, Player player) {
		if (player.getGameMode() == GameMode.CREATIVE) {
			return;
		}
		if (vamp.getAge() >= 50) {
			if (!player.getAllowFlight()) {
				player.setAllowFlight(true);
			}
			if (player.getFoodLevel() < 6) {
				player.setFlying(false);
				return;
			}
			float agebuff = (float)(vamp.getAge() / 10.0);
			if (player.isFlying()) {
				player.setExhaustion(player.getExhaustion() + (float)(10 / agebuff));
			}
		} else {
			player.setAllowFlight(false);
		}
	}

	public static void vampSprintMngr(Vampire vamp, Player player) {
		float agebuff = vamp.getAge() > 50 ? 10 : (float)(vamp.getAge() / 5.0);
		if (player.isSprinting() && vamp.isBloodSucking()) {
			player.addPotionEffect(
				new PotionEffect(PotionEffectType.SPEED, 10, (int)agebuff + 5), true);
			agebuff = agebuff == 0 ? 1 : agebuff;
			player.setExhaustion(player.getExhaustion() + (2.5F / agebuff));
		}
	}

	public static void vampHealthMngr(Vampire vamp, Player player) {
		float agebuff = vamp.getAge() > 50 ? 10 : (float)(vamp.getAge() / 5.0);
		double damageTaken = 20 - player.getHealth();
		int duration = (int)((50.0 * damageTaken) / ((int)agebuff + 1));
		if (player.getHealth() <= 19) {
			if (player.getFoodLevel() > 0 &&
				!player.hasPotionEffect(PotionEffectType.REGENERATION)) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,
														duration, (int)agebuff),
									   true);
				agebuff = agebuff == 0 ? 1 : agebuff;
				player.setExhaustion(player.getExhaustion() +
									 (float)((damageTaken * 4.0) / agebuff));
			}
		} else {
			player.removePotionEffect(PotionEffectType.REGENERATION);
		}
	}

	public static void vampStrengthMngr(Vampire vamp, Player player) {
		int agebuff = vamp.getAge() > 50 ? 5 : vamp.getAge() / 10;
		if (player.getFoodLevel() > 0 && !vamp.isBloodSucking()) {
			player.addPotionEffect(
				new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, agebuff), true);
		}
	}

	public static void vampHasteMngr(Vampire vamp, Player player) {
		int agebuff = vamp.getAge() > 50 ? 10 : vamp.getAge() / 5;
		if (player.getFoodLevel() > 0) {
			player.addPotionEffect(
				new PotionEffect(PotionEffectType.FAST_DIGGING, 20, agebuff), true);
		}
	}

	public static void vampTouchGold(Player player) {
		Location ploc = player.getLocation();
		// get block at player location
		Block block1 = ploc.getBlock();
		Block block2 = ploc.subtract(0, 1, 0).getBlock();
		ItemStack boot = player.getInventory().getBoots();
		Material boots = boot == null ? Material.AIR : boot.getType();
		boolean goldstuff = (block2.getType() == Material.GOLD_BLOCK) ||
							(block1.getType() == Material.LEGACY_GOLD_PLATE);
		if (goldstuff && boots == Material.AIR) {
			player.setFireTicks(40);
		}
	}
}
