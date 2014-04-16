/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.Events;

import com.DanMan.FalseBlood.main.Vampire;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author DAY
 */
public class VAgeEvent extends Event {

    Vampire vamp;
    //age is the new age set by setage();
    private int age;
    private UUID pId;
    private Player player;
    private static final HandlerList handlers = new HandlerList();

    public VAgeEvent(Vampire v) {
        vamp = v;
        age = vamp.getAge();
        pId = vamp.getPId();
        player = vamp.getPlayer();
    }

    public int getAge() {
        return age;
    }

    public Player getPlayer() {
        return player;
    }

    public Vampire getVamp() {
        return vamp;
    }

    public UUID getName() {
        return pId;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
