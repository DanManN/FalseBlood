/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.main;

import com.DanMan.Commands.FB;
import com.DanMan.Listeners.*;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author DAY
 */
public class FalseBlood extends JavaPlugin {

    private FB myEx;

    @Override
    public void onEnable() {
        createDirs();
        myEx = new FB(this);
        getCommand("fb").setExecutor(myEx);
        registerListeners();
        getLogger().info("FalseBlood: Enabled");

    }

    @Override
    public void onDisable() {
        VampTracker.vampUnload(this);
        HandlerList.unregisterAll(this);
        getLogger().info("FalseBlood: Disabled");
    }

    public void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new VLoginListener(this), this);
        pm.registerEvents(new VRestrictionsListener(this), this);
        pm.registerEvents(new VSuckListener(this), this);
        pm.registerEvents(new VDamageListener(this), this);
        pm.registerEvents(new VStakeListener(this), this);
        pm.registerEvents(new VClockListener(this), this);
        pm.registerEvents(new VDeathListener(this), this);
        pm.registerEvents(new VDrinkTrueBloodListener(this), this);
        pm.registerEvents(new VMakeListener(this), this);
    }

    public void createDirs() {
        File dirs = new File("plugins/FalseBlood/users/");
        if (!dirs.exists()) {
            getLogger().warning("FalseBlood Directories not found! Creating them now.");
            boolean mkdirs = dirs.mkdirs();
            if (!mkdirs) {
                getLogger().severe("Unable to create necessary directories!");
            }
        }
    }
}