/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Commands;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import com.DanMan.utils.SNLMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
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
        String pname;
        Vampire vamp;
        if (sender.hasPermission("falseblood.setage")) {
            int age = 0;
            if (args.length == 2) {
                if ((sender instanceof Player)) {
                    pname = sender.getName();
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
                pname = args[1];
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
            player = Bukkit.getServer().getPlayer(pname);
            if (Vampire.isVampire(pname, plugin)) {
                if (player != null) {
                    vamp = SNLMetaData.getMetadata(player, plugin);
                    vamp.setAge(age);
                    sender.sendMessage(ChatColor.RED + vamp.getName() + " is now " + age + " years old!");
                } else {
                    sender.sendMessage(ChatColor.RED + "Sorry, that player is not online.");
                }
            } else {
                sender.sendMessage(ChatColor.YELLOW + "You can only change a vampire's age!");
            }
        } else {
            sender.sendMessage(ChatColor.YELLOW + "You don't have the falseblood.setage permission");
        }
        return true;
    }
}
