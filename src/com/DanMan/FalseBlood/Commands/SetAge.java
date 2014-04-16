/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.Commands;

import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.Vampire;
import com.DanMan.FalseBlood.utils.SNLMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author DAY
 */
public class SetAge {

    CommandSender sender;
    String[] args;
    FalseBlood plugin;

    public SetAge(CommandSender sender, String[] args, FalseBlood plugin) {
        this.sender = sender;
        this.args = args;
        this.plugin = plugin;
    }

    public boolean setage() {
        Player player;
        Vampire vamp;
        if (sender.hasPermission("falseblood.setage")) {
            int age = 0;
            if (args.length == 2) {
                if ((sender instanceof Player)) {
                    player = (Player) sender;
                    try {
                        age = Integer.parseInt(args[1]);
                    } catch (Exception ex) {
                        sender.sendMessage(ChatColor.YELLOW + "Error: age didn't parse.");
                        return false;
                    }
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "You can only change a player's age!");
                    return true;
                }
            } else if (args.length == 3) {
                player = Bukkit.getServer().getPlayer(args[1]);
                try {
                    age = Integer.parseInt(args[2]);
                } catch (Exception ex) {
                    sender.sendMessage(ChatColor.YELLOW + "Error: age didn't parse.");
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Incorrect number of arguments!");
                return false;
            }
            if (player != null) {
                if (Vampire.isVampire(player.getUniqueId(), plugin)) {
                    vamp = SNLMetaData.getMetadata(player, plugin);
                    vamp.setAge(age);
                    sender.sendMessage(ChatColor.RED + player.getName() + " is now " + age + " years old!");
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "You can only change a vampire's age!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Sorry, that player is not online.");
            }

        } else {
            sender.sendMessage(ChatColor.YELLOW + "You don't have the falseblood.setage permission");
        }
        return true;
    }
}
