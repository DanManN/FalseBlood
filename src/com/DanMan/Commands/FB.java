/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.Commands;

import com.DanMan.main.FalseBlood;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author DAY
 */
public class FB implements CommandExecutor {

    FalseBlood plugin;
    boolean bool;

    public FB(FalseBlood plug) {
        plugin = plug;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("fb")) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("make")) {
                    bool = new FBMake(sender, args, plugin).make();
                } else if (args[0].equalsIgnoreCase("setage")) {
                    bool = new SetAge(sender, args, plugin).setage();
                } else if (args[0].equalsIgnoreCase("getage")) {
                    bool = new GetAge(sender, args, plugin).getage();
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "No such command /fb " + args[0]);
                    return false;
                }
                return bool;
            } else {
                return false;
            }
            
        }
        return false;
    }
}
