/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.Listeners;

import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.VampTracker;
import com.DanMan.FalseBlood.main.Vampire;
import com.DanMan.FalseBlood.utils.SNLMetaData;
import com.DanMan.FalseBlood.utils.Stats;
import java.util.UUID;
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
	private static FalseBlood plugin;
	// private static final String key = "VampPlayer";

	public VLoginListener(FalseBlood plug)
	{
		plugin = plug;
	}

	@EventHandler public void onVampLogon(PlayerJoinEvent evt)
	{
		Player player = evt.getPlayer();
		UUID pId = player.getUniqueId();
		//        SNLMetaData.showMetadata(player, plugin);
		if (Vampire.isVampire(pId)) {
			Stats.loadMDfromFile(pId, plugin);
			Vampire vamp = SNLMetaData.getMetadata(player, plugin);
			vamp.setPlugin(plugin);
			VampTracker.startVampTracker(vamp);
			// allow flight:
			if (vamp.getAge() >= 50 && !player.getAllowFlight()) {
				player.setAllowFlight(true);
			}
		}
	}
	@EventHandler public void onVampLogoff(PlayerQuitEvent evt)
	{
		Player player = evt.getPlayer();
		UUID pId = player.getUniqueId();
		//        SNLMetaData.showMetadata(player, plugin);
		if (Vampire.isVampire(pId)) {
			Vampire vamp = SNLMetaData.getMetadata(player, plugin);
			VampTracker.stopVampTracker(vamp);
			Stats.logMDtoFile(pId, plugin);
		}
	}
}
