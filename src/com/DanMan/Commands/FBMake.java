/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Commands;

import com.DanMan.main.FalseBlood;
import com.DanMan.main.Vampire;
import com.DanMan.utils.SNLMetaData;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author DAY
 */
public class FBMake {

    CommandSender sender;
    String[] args;
    FalseBlood plugin;

    public FBMake(CommandSender sender, String[] args, FalseBlood plugin) {
        this.sender = sender;
        this.args = args;
        this.plugin = plugin;
    }

    public boolean make() {
        Player player;
        String pname;
        Vampire vamp;
        if (sender.hasPermission("falseblood.make")) {
            if (args.length == 1) {
                if ((sender instanceof Player)) {
                    pname = sender.getName();
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "You can only make a player a Vampire!");
                    return false;
                }
            } else if (args.length == 2) {
                pname = args[1];
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Incorrect number of arguments!");
                return false;
            }
            Pattern pat = Pattern.compile("[^A-Za-z0-9_]+");
            Matcher m = pat.matcher(pname);
            if (!m.find()) {
                player = Bukkit.getServer().getPlayer(pname);
                if (player != null) {
                    if (!Vampire.isVampire(pname, plugin)) {
                        vamp = new Vampire(player, plugin);
                        sender.sendMessage(ChatColor.RED + vamp.getName() + " is now a Vampire.");
                    } else {
                        vamp = SNLMetaData.getMetadata(player, plugin);
                        vamp.setVampire(false);
                        sender.sendMessage(ChatColor.RED + vamp.getName() + " is no longer a Vampire.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Sorry, that player is not online.");
                }
                return true;
            } else {
                sender.sendMessage(ChatColor.YELLOW + "A player's username can only contain letters, numbers and _");
                return false;
            }
        } else {
            sender.sendMessage(ChatColor.YELLOW + "You don't have the falseblood.make permission");
            return true;
        }
    }
}
