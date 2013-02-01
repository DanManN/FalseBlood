/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.utils;

import com.DanMan.main.Vampire;
import java.io.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author DAY
 */
public class Stats {

    public static void logMDtoFile(String pname, Plugin plugin) {
        Vampire vamp;
        pname = pname.toLowerCase();
        File sFile = new File("plugins/FalseBlood/users/" + pname + ".dat");
        try {
            sFile.createNewFile();
        } catch (IOException e) {
            System.err.println("Error: Could not create file due to illegal characters." + e);
        }
        try {
            Player player = Bukkit.getServer().getPlayer(pname);
            vamp = SNLMetaData.getMetadata(player, plugin);
            FileOutputStream fos = new FileOutputStream(sFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(vamp);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            System.err.println("Error: Could not serialize to file: " + e);
        }
    }

    public static void loadMDfromFile(String pname, Plugin plugin) {
        Vampire vamp = null;
        pname = pname.toLowerCase();
        File sFile = new File("plugins/FalseBlood/users/" + pname + ".dat");
        if (sFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(sFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                try {
                    vamp = (Vampire) ois.readObject();
                } catch (ClassNotFoundException e) {
                    System.err.println("Error: Could not deserialize from file: " + e);
                }
                ois.close();
//                System.out.println("Vampire instance: " + vamp);
                Player player = Bukkit.getServer().getPlayer(pname);
                SNLMetaData.setMetadata(player, vamp, plugin);
            } catch (IOException e) {
                System.err.println("Error: Could not deserialize from file: " + e);
            }
        }
    }

    public static int getSavedAge(String pname, Plugin plugin) {
        Vampire vamp = null;
        pname = pname.toLowerCase();
        File sFile = new File("plugins/FalseBlood/users/" + pname + ".dat");
        if (sFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(sFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                try {
                    vamp = (Vampire) ois.readObject();
                } catch (ClassNotFoundException e) {
                    System.err.println("Error: Could not deserialize from file: " + e);
                }
                ois.close();
            } catch (IOException e) {
                System.err.println("Error: Could not deserialize from file: " + e);
            }
            return vamp.getAge();
        } else {
            return -1;
        }
    }
}
