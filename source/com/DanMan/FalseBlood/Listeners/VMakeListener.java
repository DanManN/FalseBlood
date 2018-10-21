package com.DanMan.FalseBlood.Listeners;

import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.Vampire;
import com.DanMan.FalseBlood.utils.SNLMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import java.io.*;

/**
 *
 * @author DAY
 */
public class VMakeListener implements Listener {

	private static FalseBlood plugin;

	public VMakeListener(FalseBlood plug) {
		plugin = plug;
	}

	@EventHandler
	public void onVampGiveBlood(PlayerInteractEntityEvent evt) {
		Player player = evt.getPlayer();
		Entity clicked = evt.getRightClicked();
		if (clicked instanceof Player && Vampire.isVampire(player.getUniqueId())) {
       			Player victim = (Player) clicked;
			Vampire vamp = SNLMetaData.getMetadata(player, plugin);
			if (!Vampire.isVampire(victim.getUniqueId())) {
				if (victim.getFoodLevel() > 0 && vamp.getBloodLevel() > 0) {
					if (victim.getHealth() < 20) {
						vamp.setBloodLevel(vamp.getBloodLevel() - 1);
						int health = (int) victim.getHealth() + 2;
						health = health > 20 ? 20 : health;
						victim.setHealth(health);
					} else if (!victim.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
						vamp.setBloodLevel(vamp.getBloodLevel() - 1);
						victim.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 0));
					}
				} else if (victim.getFoodLevel() == 0 && vamp.getBloodLevel() > 10) {
					vamp.setBloodLevel(vamp.getBloodLevel() - 10);
					victim.setFoodLevel(20);
					delayMessage(100L, victim, "You feel a stranger to your body.");
					delayMessage(200L, victim, "Your blood has been sucked out of your veins and refilled with...");
					delayMessage(320L, victim, "something else.");
					delayMessage(500L, victim, "Suddenly your heart stops and you think you're dead, but you feel so ALIVE!");
					victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
					victim.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 0));
					player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.INSTANT_HEAL.getDamageValue());
					player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.INSTANT_DAMAGE.getDamageValue());
					player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.STRENGTH.getDamageValue());
					delayVMake(600L, 0, victim);
				}
			}
		}
	}

	@EventHandler
	public void onClockInEndInteract(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();
		if ((evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) && evt.getHand() == EquipmentSlot.HAND) {

			//click watch boolean
			Boolean clicksWatch = player.getInventory().getItemInMainHand().getType() == Material.CLOCK;
			//in End Realm boolean
			Boolean inWorldEnd = player.getWorld() == Bukkit.getServer().getWorld("world_the_end");

			if (inWorldEnd && clicksWatch) {
				makeVampire(player);		
				File sFile = new File("plugins/FalseBlood/users/" + player.getUniqueId() + ".dat");
      				try {
        			   sFile.createNewFile();
        			} catch (IOException e) {
         			   System.err.println("Error: Could not create file due to illegal characters." + e);
       				}
			}
		}
	}

	//Items Needed to become vampire
    	ItemStack star = new ItemStack(Material.NETHER_STAR);
    	ItemStack egg = new ItemStack(Material.DRAGON_EGG);
	ItemStack elytra = new ItemStack(Material.ELYTRA);

	public void makeVampire(Player player) {
		//has necessary items
	        Boolean hasStar = player.getInventory().contains(star);
	        Boolean hasEgg = player.getInventory().contains(egg);
	        Boolean hasElytra = player.getInventory().contains(elytra);
	        //give age boost depending on how many items
		int age = 0;
	        if (!Vampire.isVampire(player.getUniqueId())) {
			if (hasElytra) {
				player.getInventory().remove(elytra);
				age += 10;
			} else if (hasEgg) {
				player.getInventory().remove(egg);
				age += 10;
			} else if (hasStar) {
				player.getInventory().remove(star);
				age += 10;
			}
			vampEffects(player);
			delayVMake(410L, age, player);
			//player.getInventory().remove(Material.WATCH);
			player.updateInventory();
		}
	}

	public void vampEffects(final Player player) {
	        if (player.getFoodLevel() > 18) {
			player.setFoodLevel(player.getFoodLevel() - 2);
		}
	        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
	        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 0));
	        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 380, 0));
	        delayMessage(100L, player, "I feel cursed and sick.");
	        delayMessage(200L, player, "My heart! It's beating slower and slower...");
	        delayMessage(300L, player, "It stopped!");
	        delayMessage(340L, player, "Am I dead?");
	        delayMessage(410L, player, "No, no I have never felt so ALIVE!!!");
	        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				player.getWorld().strikeLightningEffect(player.getLocation());
				player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.INSTANT_HEAL.getDamageValue());
				player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.INSTANT_DAMAGE.getDamageValue());
				player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.STRENGTH.getDamageValue());
			}
		}, 410L);
	}

	public void delayVMake(Long delay, final int age, final Player player) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
		                Vampire vamp = new Vampire(player, plugin);
		                if (age > 0) {
					vamp.setAge(age);
				}
			}
		}, delay);
	}

	public void delayMessage(Long delay, final Player player, final String message) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			@Override
			public void run() {
				player.sendMessage(ChatColor.RED + message);
			}
		}, delay);
	}
}
