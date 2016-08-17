
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.main;

import com.DanMan.FalseBlood.Events.VAgeEvent;
import com.DanMan.FalseBlood.utils.SNLMetaData;
import com.DanMan.FalseBlood.utils.Stats;
import java.io.File;
import java.io.Serializable;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author DAY
 */
public final class Vampire implements Serializable {

    private transient FalseBlood plugin;
    //vampire variables
    private UUID pId;
    private boolean isBloodSucking;
    private boolean afk;
    private int sId = -1;
    private int tick = 0;
    private int age;

    public Vampire(Player player, FalseBlood plug) {
        pId = player.getUniqueId();
        plugin = plug;
        setVampire(true);
    }

    public static boolean isVampire(UUID name, Plugin plug) {
//        Stats stats = new Stats();
//        if (stats.getStats(player.getName(), 0).contains("true")) {
//            return true;
//        }
        File sFile = new File("plugins/FalseBlood/users/" + name + ".dat");
        if (sFile.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void setVampire(boolean bool) {
        if (bool) {
//            Method[] ms = SNLMetaData.class.getMethods();
//            for (Method m:ms) {
//                System.out.println("METHOD: "+m.getName() + ", args: "+m.getParameterTypes());
//            }
//            System.out.println("PLAYER: "+getPlayer());
//            System.out.println("VAMPIRE: "+this);
//            System.out.println("PLUGIN: "+plugin);
            
            SNLMetaData.setMetadata(getPlayer(), this, plugin);
//            System.out.println("Vampire Instance: " + SNLMetaData.getMetadata(getPlayer(), plugin));
            Stats.logMDtoFile(pId, plugin);
            VampTracker.startVampTracker(this);
            addClock(getPlayer());
            getPlayer().sendMessage(ChatColor.RED + "You are now a vampire.");
        } else {
            getPlayer().updateInventory();
            getPlayer().setAllowFlight(false);
            VampTracker.stopVampTracker(this);
            SNLMetaData.delMetaData(getPlayer(), plugin);
            File sFile = new File("plugins/FalseBlood/users/" + pId + ".dat");
            sFile.delete();
            getPlayer().sendMessage(ChatColor.RED + "You have fallen a victim to True-Death!");
        }
    }
    //add essential clock to vampire

    public static void addClock(Player player) {
        Inventory inv = player.getInventory();
        System.out.println(inv);
        if (inv.contains(Material.WATCH)) {
            inv.remove(Material.WATCH);
        }
        ItemStack firstSlot = inv.getItem(0);
        if (firstSlot != null) {
            inv.remove(firstSlot);
            inv.addItem(new ItemStack(Material.WATCH, 1));
            inv.addItem(firstSlot);
        } else {
            inv.addItem(new ItemStack(Material.WATCH, 1));
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int inAge) {
        //stat.logStats(name, true, inAge);
        //chane age
        age = inAge;
        if (getPlayer() != null) {
            //create and call agechange event
            VAgeEvent evt = new VAgeEvent(this);
            Bukkit.getServer().getPluginManager().callEvent(evt);
            getPlayer().sendMessage(ChatColor.RED + "You are now " + inAge + " years old.");
        }
    }

    public boolean isBloodSucking() {
        return isBloodSucking;
    }

    public void setBloodSucking(boolean isBloodSucking) {
        this.isBloodSucking = isBloodSucking;
    }

    public boolean isAfk() {
        return afk;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    //convenience methods
    public void setPlugin(FalseBlood plug) {
        plugin = plug;
    }

    public FalseBlood getPlugin() {
        return plugin;
    }

    public UUID getPId() {
        return pId;
    }

    public void addAge(int i) {
        setAge(getAge() + i);
    }

    public int getBloodLevel() {
        return getPlayer().getFoodLevel();
    }

    public void setBloodLevel(int inBloodLevel) {
        getPlayer().setFoodLevel(inBloodLevel);
    }

    public Player getPlayer() {
        Player player = Bukkit.getServer().getPlayer(pId);
        return player;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }
}
