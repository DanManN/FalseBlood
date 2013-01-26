/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author DAY
 */
public class VFoodListener implements Listener {

    FalseBlood plugin;

    public VFoodListener(FalseBlood plug) {
        plugin = plug;
    }

    @EventHandler
    public void onEatFood(PlayerInteractEvent evt) {
        Player player = evt.getPlayer();
        if (Vampire.isVampire(player.getName(), plugin)) {
            ItemStack Food = player.getItemInHand();
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
            //boolean combining these booleans
            boolean eatFood = eatBread || eatCookie || eatMelon || eatStew || eatRChicken || eatCChicken || eatBeef || eatSteak || eatPork || eatGPork || eatRFish || eatCFish || eatApple || eatGApple || drinkMilk || eatFlesh;
            if (eatFood) {
                evt.setCancelled(true);
                //player.getInventory().remove(Food);
                //player.updateInventory();
            }
        }
    }
}
