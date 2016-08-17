package com.DanMan.FalseBlood.main;

import com.DanMan.FalseBlood.Listeners.VMakeListener;
import com.DanMan.FalseBlood.Listeners.VDeathListener;
import com.DanMan.FalseBlood.Listeners.VDrinkTrueBloodListener;
import com.DanMan.FalseBlood.Listeners.VSuckListener;
import com.DanMan.FalseBlood.Listeners.VDamageListener;
import com.DanMan.FalseBlood.Listeners.VRestrictionsListener;
import com.DanMan.FalseBlood.Listeners.VLoginListener;
import com.DanMan.FalseBlood.Listeners.VStakeListener;
import com.DanMan.FalseBlood.Listeners.VClockListener;
import com.DanMan.FalseBlood.Listeners.VMobNeutralityListener;
import com.DanMan.FalseBlood.Commands.FB;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FalseBlood extends JavaPlugin {

    private FB myEx;
    private PluginManager evtBus;

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
    	evtBus = getServer().getPluginManager();
    	evtBus.registerEvents(new VLoginListener(this), this);
    	evtBus.registerEvents(new VRestrictionsListener(this), this);
    	evtBus.registerEvents(new VSuckListener(this), this);
        evtBus.registerEvents(new VDamageListener(this), this);
        evtBus.registerEvents(new VStakeListener(this), this);
        evtBus.registerEvents(new VClockListener(this), this);
        evtBus.registerEvents(new VDeathListener(this), this);
        evtBus.registerEvents(new VDrinkTrueBloodListener(this), this);
 	evtBus.registerEvents(new VMakeListener(this), this);
	evtBus.registerEvents(new VMobNeutralityListener(this), this);
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
