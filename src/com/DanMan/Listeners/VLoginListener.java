/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.VampTracker;
import com.DanMan.main.Vampire;
import com.DanMan.utils.SNLMetaData;
import com.DanMan.utils.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author DAY
 */
public class VLoginListener implements Listener {

    FalseBlood plugin;
    Vampire vamp;
    Player player;
    String pname;
    static final String key = "VampPlayer";

    public VLoginListener(FalseBlood plug) {
        plugin = plug;
    }

    @EventHandler
    public void onVampLogon(PlayerJoinEvent evt) {
        player = evt.getPlayer();
        pname = player.getName();
//        SNLMetaData.showMetadata(player, plugin);
        if (Vampire.isVampire(pname, plugin)) {
            Stats.loadMDfromFile(pname, plugin);
            vamp = SNLMetaData.getMetadata(player, plugin);
            vamp.setPlugin(plugin);
            VampTracker.startVampTracker(vamp);
            //allow flight:
            if (vamp.getAge() >= 50 && !player.getAllowFlight()) {
                player.setAllowFlight(true);
            }
        }
    }
        @EventHandler
    public void onVampLogoff(PlayerQuitEvent evt) {
        player = evt.getPlayer();
        pname = player.getName();
//        SNLMetaData.showMetadata(player, plugin);
        if (Vampire.isVampire(pname, plugin)) {
            vamp = SNLMetaData.getMetadata(player, plugin);
            VampTracker.stopVampTracker(vamp);
            Stats.logMDtoFile(pname, plugin);
        }
    }
}
