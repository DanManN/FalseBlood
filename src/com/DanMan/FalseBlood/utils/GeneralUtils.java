/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DanMan.FalseBlood.utils;

/**
 *
 * @author DAY
 */
public class GeneralUtils {
    //percent chance
    public static boolean random(double percent) {
        double r = Math.random();
        if (r < percent) {
            return true;
        } else {
            return false;
        }
    }
}
