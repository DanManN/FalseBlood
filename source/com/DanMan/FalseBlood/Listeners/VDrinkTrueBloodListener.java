/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.Listeners;

import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.Vampire;
import com.DanMan.FalseBlood.utils.SNLMetaData;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.Cauldron;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 *
 * @author DAY
 */
public class VDrinkTrueBloodListener implements Listener {
	private static FalseBlood plugin;

	static final ItemStack trueblood = new ItemStack(Material.POTION, 1, (short)373);

	public VDrinkTrueBloodListener(FalseBlood plug)
	{
		plugin = plug;
	}

	@EventHandler public void onDrinkPotion(PlayerInteractEvent evt)
	{
		Player player = evt.getPlayer();
		// player.sendMessage("You are currently holding: " +
		// evt.getPlayer().getItemInHand() + "/" +
		// evt.getPlayer().getItemInHand().isSimilar(trueblood));
		if (evt.getAction() == Action.RIGHT_CLICK_AIR ||
		    evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack tb = player.getInventory().getItemInMainHand();
			if (tb.getType() == Material.POTION) {
				PotionMeta pm = (PotionMeta)tb.getItemMeta();
				if (pm.getBasePotionData().getType() ==
				    PotionType.UNCRAFTABLE) {
					if (Vampire.isVampire(player.getUniqueId()) &&
					    player.getFoodLevel() >= 20) {
						return;
					}

					final int slot =
						player.getInventory().getHeldItemSlot();
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override public void run()
						{
							if (player.getInventory()
								    .getItem(slot)
								    .getType() ==
							    Material.GLASS_BOTTLE) {
								if (Vampire.isVampire(
									    player.getUniqueId())) {
									Vampire vamp =
										SNLMetaData.getMetadata(
											player,
											plugin);
									vamp.setBloodLevel(
										vamp.getBloodLevel() +
										4);
									player.setSaturation(
										player.getSaturation() +
										4);
								} else {
									player.addPotionEffect(
										new PotionEffect(
											PotionEffectType
												.CONFUSION,
											600,
											0));
								}
							}
						}
					}, 33L);
				}
			}
		}
	}
	//	  //temporary method
	//	  @EventHandler
	//	  public void onUseFurnace(InventoryClickEvent evt) {
	//		  Inventory i = evt.getInventory();
	////		System.out.println("Invenotry Type: " + i.getType());
	//		  if (i.getType() == InventoryType.FURNACE) {
	////			System.out.println("Slot #" + evt.getSlot());
	//			  FurnaceInventory fi = (FurnaceInventory) i;
	//			  ItemStack water = new ItemStack(Material.POTION, 1, (short) 0);
	//			  ItemStack eeye = new ItemStack(Material.EYE_OF_ENDER);
	//			  if (fi.getFuel() != null && fi.getSmelting() != null) {
	////				System.out.println("Has EnderEye: " + (fi.getFuel().getType() ==
	///eeye.getType())); /				System.out.println("Has Water: " +
	///(fi.getSmelting().getType() == water.getType()));
	//				  if (fi.getFuel().getType() == eeye.getType() &&
	//fi.getSmelting().getType() == water.getType()) { 					  fi.remove(eeye);
	//					  fi.remove(water);
	//					  fi.setResult(trueblood);
	//				  }
	//			  }
	//		  }
	//	  }

	@EventHandler public void onBrewPotion(PlayerInteractEvent evt)
	{
		if (evt.hasBlock()) {
			if (evt.getAction() == Action.RIGHT_CLICK_AIR ||
			    evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
				// System.out.println("Triggered");

				Player player = evt.getPlayer();
				ItemStack inHand = player.getInventory().getItemInMainHand();
				Block caul = evt.getClickedBlock();
				if (caul.getType() == Material.CAULDRON &&
				    inHand.getType() == Material.GLASS_BOTTLE) {
					Cauldron c = (Cauldron)caul.getState().getData();
					BlockState cs = caul.getState();
					Block hop = caul.getRelative(BlockFace.UP);

					// System.out.println("Hopper: " + hop);

					if (!c.isEmpty() &&
					    (hop.getType() == Material.HOPPER)) {
						// System.out.println("Hopper exists");

						Hopper h = (Hopper)hop.getState();
						Inventory hopi = h.getInventory();
						if (hopi.contains(Material.BEETROOT)) {
							ItemStack iEye = hopi.getItem(
								hopi.first(Material.BEETROOT));
							if (iEye.getAmount() > 1) {
								iEye.setAmount(
									iEye.getAmount() - 1);
							} else {
								hopi.clear(hopi.first(iEye));
							}
							// System.out.println("Item exists in proper location");
							ItemMeta meta = trueblood.getItemMeta();
							meta.setDisplayName(ChatColor.DARK_RED +
									    "False Blood");
							List<String> lore =
								new ArrayList<String>();
							lore.clear();
							lore.add(
								"Replenishes 2 vampire blood bars.");
							meta.setLore(lore);
							trueblood.setItemMeta(meta);
							if (inHand.getAmount() > 1) {
								ItemStack less = new ItemStack(
									inHand.getType(),
									(inHand.getAmount() -
									 1));
								player.setItemInHand(less);
								player.getInventory().addItem(
									trueblood);
							} else {
								player.setItemInHand(trueblood);
							}
							evt.setCancelled(true);
							cs.getData().setData(
								(byte)(c.getData() - 1));
							cs.update();
							player.updateInventory();
						}
					}
				}
			}
		}
	}
}
