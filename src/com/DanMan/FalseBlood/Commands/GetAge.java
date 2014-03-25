/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.Commands;

import com.DanMan.FalseBlood.main.FalseBlood;
import com.DanMan.FalseBlood.main.Vampire;
import com.DanMan.FalseBlood.utils.SNLMetaData;
import com.DanMan.FalseBlood.utils.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        Player player;
        String pname;
        Vampire vamp;
        if (sender.hasPermission("falseblood.getage")) {
            int age;
            if (args.length == 1) {
                if ((sender instanceof Player)) {
                    pname = sender.getName();
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "You can only get a Vampire's age!");
                    return true;
                }
            } else if (args.length == 2) {
                pname = args[1];
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Incorrect number of arguments!");
                return false;
            }
            player = Bukkit.getServer().getPlayer(pname);
            if (Vampire.isVampire(pname, plugin)) {
                if (player != null) {
                    vamp = SNLMetaData.getMetadata(player, plugin);
                    age = vamp.getAge();
                } else {
                    age = Stats.getSavedAge(pname, plugin);
                }
                sender.sendMessage(ChatColor.RED + pname + " is " + age + " years old!");
            } else {
                sender.sendMessage(ChatColor.YELLOW + "You can only get a Vampire's age!");
            }

        } else {
            sender.sendMessage(ChatColor.YELLOW + "You don't have the falseblood.getage permission");
        }
        return true;
    }
}
