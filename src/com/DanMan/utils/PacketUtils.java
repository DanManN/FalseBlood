/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.utils;

import net.minecraft.server.v1_4_R1.MathHelper;
import net.minecraft.server.v1_4_R1.Packet20NamedEntitySpawn;
import net.minecraft.server.v1_4_R1.Packet24MobSpawn;
import net.minecraft.server.v1_4_R1.Packet29DestroyEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_4_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author DAY
 */
public class PacketUtils {

    public void unImmitate(Player p1) {
        CraftPlayer p22 = (CraftPlayer) p1;
        Packet29DestroyEntity p29 = new Packet29DestroyEntity(p22.getEntityId());
        Packet20NamedEntitySpawn p20 = new Packet20NamedEntitySpawn(p22.getHandle());
        for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
            if (p1.getWorld().equals(p2.getWorld())) {
                if (p2 != p1) {
                    ((CraftPlayer) p2).getHandle().playerConnection.sendPacket(p29);
                    ((CraftPlayer) p2).getHandle().playerConnection.sendPacket(p20);
                }
            }
        }
    }

    public void immitate(Player p1, int id) {
        Packet24MobSpawn p24 = makePacket(p1, (byte) id);
        for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
            if (p1.getWorld().equals(p2.getWorld())) {
                if (p2 != p1) {
                    ((CraftPlayer) p2).getHandle().playerConnection.sendPacket(p24);
                }
            }
        }
    }

    public Packet24MobSpawn makePacket(Player p1, Byte id) {
        Location loc = p1.getLocation();
        Packet24MobSpawn packet = new Packet24MobSpawn();
        packet.a = ((CraftPlayer) p1).getEntityId();
        packet.b = id.byteValue();
        packet.c = MathHelper.floor(loc.getX() * 32.0D);
        packet.d = MathHelper.floor(loc.getY() * 32.0D);
        packet.e = MathHelper.floor(loc.getZ() * 32.0D);
        packet.f = (byte) (int) ((int) loc.getYaw() * 256.0F / 360.0F);
        packet.g = (byte) (int) (loc.getPitch() * 256.0F / 360.0F);
        return packet;
    }
}
