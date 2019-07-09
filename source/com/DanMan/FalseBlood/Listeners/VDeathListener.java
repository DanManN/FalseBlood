/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.Listeners;

import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.Vampire;
import com.DanMan.FalseBlood.utils.SNLMetaData;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionType;

/**
 *
 * @author DAY
 */
public class VDeathListener implements Listener {

	private static FalseBlood plugin;

	public VDeathListener(FalseBlood plug) { plugin = plug; }

	@EventHandler
	public void onVDeath(PlayerDeathEvent evt) {
		Player player = evt.getEntity();
		if (Vampire.isVampire(player.getUniqueId())) {
			player.playEffect(player.getLocation(), Effect.POTION_BREAK,
							  PotionType.INSTANT_HEAL.getDamageValue());
			player.playEffect(player.getLocation(), Effect.POTION_BREAK,
							  PotionType.INSTANT_DAMAGE.getDamageValue());
			player.playEffect(player.getLocation(), Effect.POTION_BREAK,
							  PotionType.STRENGTH.getDamageValue());
			Vampire vamp = SNLMetaData.getMetadata(player, plugin);
			if (vamp == null)
				return;
			int vage = vamp.getAge();
			if (vage > 0) {
				vamp.setAge(vage - 1);
			}
			// player.getInventory().remove(Material.WATCH);
		}
	}
}
