/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Listeners;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import com.DanMan.utils.SNLMetaData;
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

    Vampire vamp;
    FalseBlood plugin;

    public VDeathListener(FalseBlood plug) {
        plugin = plug;
    }

    @EventHandler
    public void onVDeath(PlayerDeathEvent evt) {
        Player player = evt.getEntity();
        if (Vampire.isVampire(player.getName(), plugin)) {
            player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.INSTANT_HEAL.getDamageValue());
            player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.INSTANT_DAMAGE.getDamageValue());
            player.playEffect(player.getLocation(), Effect.POTION_BREAK, PotionType.STRENGTH.getDamageValue());
            vamp = SNLMetaData.getMetadata(player, plugin);
            int vage = vamp.getAge();
            if (vage > 0) {
                vamp.setAge(vage - 1);
            }
        }
    }
}
