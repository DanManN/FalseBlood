/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.Listeners;
 
import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.Vampire;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author DAY
 */
public class VRestrictionsListener implements Listener {

    //private static FalseBlood plugin;

    public VRestrictionsListener(FalseBlood plug) {
    //    plugin = plug;
    }

	@EventHandler
	public void onEatFood(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();
		if ((evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) && evt.getHand() == EquipmentSlot.HAND) {
			if (Vampire.isVampire(player.getUniqueId())) {
            			ItemStack Food = player.getInventory().getItemInMainHand();
				//food booleans
				boolean eatBread = Food.getType() == Material.BREAD;
				boolean eatCookie = Food.getType() == Material.COOKIE;
				boolean eatMelon = Food.getType() == Material.MELON;
				boolean eatStew = Food.getType() == Material.MUSHROOM_SOUP;
				boolean eatRChicken = Food.getType() == Material.RAW_CHICKEN;
				boolean eatCChicken = Food.getType() == Material.COOKED_CHICKEN;
				boolean eatBeef = Food.getType() == Material.RAW_BEEF;
				boolean eatSteak = Food.getType() == Material.COOKED_BEEF;
				boolean eatPork = Food.getType() == Material.PORK;
				boolean eatGPork = Food.getType() == Material.GRILLED_PORK;
				boolean eatRFish = Food.getType() == Material.RAW_FISH;
				boolean eatCFish = Food.getType() == Material.COOKED_FISH;
				boolean eatApple = Food.getType() == Material.APPLE;
				boolean eatGApple = Food.getType() == Material.GOLDEN_APPLE;
				boolean drinkMilk = Food.getType() == Material.MILK_BUCKET;
				boolean eatFlesh = Food.getType() == Material.ROTTEN_FLESH;
				boolean eatPotato = Food.getType() == Material.POTATO || Food.getType() == Material.POTATO_ITEM;
				boolean eatBPotato = Food.getType() == Material.BAKED_POTATO;
				boolean eatPPotato = Food.getType() == Material.POISONOUS_POTATO;
				boolean eatCarrot = Food.getType() == Material.CARROT;
				boolean eatGCarrot = Food.getType() == Material.GOLDEN_CARROT;
				boolean eatPPie = Food.getType() == Material.PUMPKIN_PIE;
				boolean eatRabbit = Food.getType() == Material.RABBIT;
				boolean eatRabbitStew = Food.getType() == Material.RABBIT_STEW;
				boolean eatCRabbit = Food.getType() == Material.COOKED_RABBIT;
				boolean eatMutton = Food.getType() == Material.MUTTON;
				boolean eatCMutton = Food.getType() == Material.COOKED_MUTTON;
				boolean eatBeat = Food.getType() == Material.BEETROOT;
				boolean eatBeatSoup = Food.getType() == Material.BEETROOT_SOUP;

				//boolean combining these booleans
				boolean eatFood = eatBread || eatCookie || eatMelon || eatStew || eatRChicken 
						|| eatCChicken || eatBeef || eatSteak || eatPork || eatGPork 
						|| eatRFish || eatCFish || eatApple || eatGApple || drinkMilk 
						|| eatFlesh || eatPotato || eatBPotato || eatRabbitStew || eatCRabbit
						|| eatMutton || eatCMutton || eatBeat || eatBeatSoup || eatPPotato || eatCarrot 
						|| eatGCarrot || eatPPie || eatRabbit;  

				if (eatFood) {
					evt.setCancelled(true);
					//player.getInventory().remove(Food);
					//player.updateInventory();
				}
			}
		}
	}

    @EventHandler
    public void onVampPickupGold(PlayerPickupItemEvent evt) {
        Player player = evt.getPlayer();
        if (Vampire.isVampire(player.getUniqueId())) {

            ItemStack gold = evt.getItem().getItemStack();
//            boolean gSword = gold.getType() == Material.GOLD_SWORD;
//            boolean gAxe = gold.getType() == Material.GOLD_AXE;
//            boolean gHoe = gold.getType() == Material.GOLD_HOE;
//            boolean gPick = gold.getType() == Material.GOLD_PICKAXE;
//            boolean gshovel = gold.getType() == Material.GOLD_SPADE;
//            boolean gBlock = gold.getType() == Material.GOLD_BLOCK;
//            boolean gIngot = gold.getType() == Material.GOLD_INGOT;
//            boolean gApple = gold.getType() == Material.GOLDEN_APPLE;
//            boolean gNugget = gold.getType() == Material.GOLD_NUGGET;
//            boolean gCarrot = gold.getType() == Material.GOLDEN_CARROT;
//            boolean gBoots = gold.getType() == Material.GOLD_BOOTS;
//            boolean gPants = gold.getType() == Material.GOLD_LEGGINGS;
//            boolean gChestPlate = gold.getType() == Material.GOLD_CHESTPLATE;
//            boolean gHelmet = gold.getType() == Material.GOLD_HELMET;
//            boolean gHorseArmor = gold.getType() == Material.GOLD_BARDING;
//            boolean gPlate = gold.getType() == Material.GOLD_PLATE;
//            //boolean combining these booleans
//            boolean pickUpGold = gSword || gAxe || gHoe || gPick || gshovel || gBlock 
//                    || gIngot || gApple || gNugget || gCarrot || gBoots || gPants 
//                    || gChestPlate || gHelmet || gHorseArmor || gPlate;
            if (gold.getType().toString().startsWith("GOLD")) {
                evt.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onVampClickGold(BlockDamageEvent evt) {
        Player player = evt.getPlayer();
        Material pick = player.getInventory().getItemInMainHand().getType();
        boolean pickaxe = pick == Material.WOOD_PICKAXE || pick == Material.STONE_PICKAXE || pick == Material.IRON_PICKAXE || pick == Material.DIAMOND_PICKAXE;
        boolean goldstuff = (evt.getBlock().getType() == Material.GOLD_BLOCK) || (evt.getBlock().getType() == Material.GOLD_PLATE);
        if (goldstuff && Vampire.isVampire(player.getUniqueId())) {
            if (!pickaxe) {
                player.damage(2);
            }
        }
    }
}
