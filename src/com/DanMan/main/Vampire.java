
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.main;

import com.DanMan.Events.VAgeEvent;
import com.DanMan.utils.SNLMetaData;
import com.DanMan.utils.Stats;
import java.io.File;
import java.io.Serializable;
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
    private String pname;
    private boolean isBloodSucking;
    private boolean afk;
    private int sId = -1;
    private int tick = 0;
    private int age;

    public Vampire(Player player, FalseBlood plug) {
        pname = player.getName();
        plugin = plug;
        setVampire(true);
    }

    public static boolean isVampire(String name, Plugin plug) {
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
        //stat.logStats(name, bool, 0);
        if (bool) {
            SNLMetaData.setMetadata(getPlayer(), this, plugin);
//            System.out.println("Vampire Instance: " + SNLMetaData.getMetadata(getPlayer(), plugin));
            Stats.logMDtoFile(pname, plugin);
            VampTracker.startVampTracker(this);
            addClock(getPlayer());
            getPlayer().sendMessage(ChatColor.RED + "You are now a vampire.");
        } else {
            getPlayer().updateInventory();
            getPlayer().setAllowFlight(false);
            VampTracker.stopVampTracker(this);
            SNLMetaData.delMetaData(getPlayer(), plugin);
            File sFile = new File("plugins/FalseBlood/users/" + pname + ".dat");
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

    public String getName() {
        return pname;
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
        Player player = Bukkit.getServer().getPlayer(pname);
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