/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.Listeners;

import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.Vampire;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 *
 * @author DAY
 */
public class VMobNeutralityListener implements Listener {
	// private static FalseBlood plugin;

	public VMobNeutralityListener(FalseBlood plug)
	{
		//    plugin = plug;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onVampSleep(EntityTargetEvent evt)
	{
		// System.out.println("Target Event triggered.");
		if (evt.getTarget() instanceof Player && evt.getEntity() instanceof Monster) {
			Player player = (Player)evt.getTarget();
			Monster mon = (Monster)evt.getEntity();
			if (Vampire.isVampire(player.getUniqueId())) {
				if (mon instanceof Blaze || mon instanceof PigZombie ||
				    mon instanceof Silverfish || mon instanceof Witch ||
				    mon instanceof Wither || mon instanceof EnderDragon) {
					// System.out.println("Target Event NotCanceled: Acceptable Mob.");
					return;
				}
				if (mon.getLastDamageCause() instanceof
				    EntityDamageByEntityEvent) {
					EntityDamageByEntityEvent evt2 =
						(EntityDamageByEntityEvent)
							mon.getLastDamageCause();
					if (evt2.getDamager() instanceof Player) {
						Player player2 = (Player)evt2.getDamager();
						if (player2 == player) {
							// System.out.println("Target Event NotCanceled: Aggravated
							// by player.");
							return;
						}
					}
				}
				// System.out.println("Target Event Canceled.");
				evt.setCancelled(true);
			}
		}
	}
}
