/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import com.DanMan.utils.SNLMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 *
 * @author DAY
 */
public class VMakeListener implements Listener {

    FalseBlood plugin;
    Vampire vamp;
    Player player;

    public VMakeListener(FalseBlood plug) {
        plugin = plug;
    }

    @EventHandler
    public void onVampGiveBlood(PlayerInteractEntityEvent evt) {
        player = evt.getPlayer();
        Entity clicked = evt.getRightClicked();
        if (clicked instanceof Player && Vampire.isVampire(player.getName(), plugin)) {
            Player victim = (Player) clicked;
            vamp = SNLMetaData.getMetadata(player, plugin);
            if (!Vampire.isVampire(victim.getName(), plugin)) {
                if (victim.getFoodLevel() > 0 && vamp.getBloodLevel() > 0) {
                    vamp.setBloodLevel(vamp.getBloodLevel() - 1);
                    if (victim.getHealth() < 20) {
                        if (victim.getHealth() == 19) {
                            victim.setHealth(victim.getHealth() + 1);
                        } else {
                            victim.setHealth(victim.getHealth() + 2);
                        }
                    } else {
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 0));
                    }
                } else if (victim.getFoodLevel() == 0 && vamp.getBloodLevel() > 10) {
                    player.setFoodLevel(player.getFoodLevel() - 10);
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
        player = evt.getPlayer();

//        if (evt.hasItem()){
//        player.sendMessage("Item: " + evt.getItem() + "DV: " + evt.getItem().getDurability());
//        }

        //click watch boolean
        Boolean clicksWatch = player.getItemInHand().getType() == Material.WATCH;
        //in End Realm boolean
        Boolean inWorldEnd = player.getWorld() == Bukkit.getServer().getWorld("world_the_end");

        if (inWorldEnd && clicksWatch) {
            makeVampire(player);
        }

    }
    //potions
    ItemStack night = new ItemStack(Material.POTION, 1, (short) 16310);
    ItemStack invisability = new ItemStack(Material.POTION, 1, (short) 16318);
    ItemStack nightLong = new ItemStack(Material.POTION, 1, (short) 16374);
    ItemStack invisabilityLong = new ItemStack(Material.POTION, 1, (short) 16382);

    public void makeVampire(Player player) {
        //has necessary potions
        Boolean hasBasicPotions = hasPotion(player, night) && hasPotion(player, invisability);
        Boolean hasLongPotions = hasPotion(player, nightLong) && hasPotion(player, invisabilityLong);
        //give age boost depending on tier of potions
        if (!Vampire.isVampire(player.getName(), plugin)) {
            if (hasLongPotions) {
                player.getInventory().remove(nightLong);
                player.getInventory().remove(invisabilityLong);
                vampEffects(player);
                delayVMake(400L, 5, player);
            } else if (hasBasicPotions) {
                player.getInventory().remove(night);
                player.getInventory().remove(invisability);
                vampEffects(player);
                delayVMake(400L, 0, player);
            }
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
        delayMessage(100L, player, "You feel cursed and sick.");
        delayMessage(200L, player, "Your heat beats slower every second until...");
        delayMessage(300L, player, "You heart finally stops. You think your are dead, but you feel so ALIVE!");
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            @Override
            public void run() {
                player.getWorld().strikeLightningEffect(player.getLocation());
                player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.INSTANT_HEAL.getDamageValue());
                player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.INSTANT_DAMAGE.getDamageValue());
                player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.STRENGTH.getDamageValue());
            }
        }, 400L);
    }

    public boolean hasPotion(Player player, ItemStack potion) {
        Boolean hasXPotion = player.getInventory().contains(potion);
        //System.out.println(hasXPotion);
        return hasXPotion;
    }

    public void delayVMake(Long delay, final int age, final Player player) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            @Override
            public void run() {
                vamp = new Vampire(player, plugin);
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
