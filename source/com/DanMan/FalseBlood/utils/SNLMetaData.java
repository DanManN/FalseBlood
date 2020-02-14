/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.utils;

import com.DanMan.FalseBlood.main.Vampire;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author DAY
 */
public class SNLMetaData {
	private static final String key = "VampPlayer";

	public static void setMetadata(Player player, Vampire value, Plugin plug)
	{
		player.setMetadata(key, new FixedMetadataValue(plug, value));
	}

	public static Vampire getMetadata(Player player, Plugin plug)
	{
		List<MetadataValue> values = player.getMetadata(key);
		for (MetadataValue value : values) {
			if (value.getOwningPlugin().getDescription().getName().equals(
				    plug.getDescription().getName())) {
				return (Vampire)value.value();
			}
		}
		return null;
	}
	public static void showMetadata(Player player, Plugin plug)
	{
		List<MetadataValue> values = player.getMetadata(key);
		System.out.println("Values: " + values);
		for (MetadataValue value : values) {
			System.out.println("MetaData: " + value.value());
		}
	}
	public static void delMetaData(Player player, Plugin plug)
	{
		player.removeMetadata(key, plug);
	}
}
