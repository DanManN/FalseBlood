/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.Commands;

import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.Vampire;
import com.DanMan.FalseBlood.utils.SNLMetaData;
import com.DanMan.FalseBlood.utils.Stats;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author DAY
 */
public class GetAge {

	CommandSender sender;
	String[] args;
	FalseBlood plugin;

	public GetAge(CommandSender sender, String[] args, FalseBlood plugin) {
		this.sender = sender;
		this.args = args;
		this.plugin = plugin;
	}

	public boolean getage() {
		OfflinePlayer player = null;
		Vampire vamp;
		UUID pId;
		boolean online = false;
		if (sender.hasPermission("falseblood.getage")) {
			int age;
			if (args.length == 1) {
				if ((sender instanceof Player)) {
					player = (Player)sender;
					online = true;
				} else {
					sender.sendMessage(ChatColor.YELLOW +
									   "You can only get a Vampire's age!");
					return true;
				}
			} else if (args.length == 2) {
				for (OfflinePlayer op : Bukkit.getServer().getOfflinePlayers()) {
					if (op.getName().equals(args[1])) {
						player = op;
						if (op.isOnline()) {
							online = true;
						}
						break;
					}
				}
			} else {
				sender.sendMessage(ChatColor.YELLOW + "Incorrect number of arguments!");
				return false;
			}
			if (player != null) {
				pId = player.getUniqueId();
				if (Vampire.isVampire(pId)) {
					if (online) {
						vamp = SNLMetaData.getMetadata((Player)player, plugin);
						age = vamp.getAge();
					} else {
						age = Stats.getSavedAge(pId, plugin);
					}
					sender.sendMessage(ChatColor.RED + player.getName() + " is " + age +
									   " years old!");
				} else {
					sender.sendMessage(ChatColor.YELLOW +
									   "You can only get a Vampire's age!");
				}
			} else {
				sender.sendMessage(ChatColor.YELLOW +
								   "The player has never been on the server.");
			}
		} else {
			sender.sendMessage(ChatColor.YELLOW +
							   "You don't have the falseblood.getage permission");
		}
		return true;
	}
}
