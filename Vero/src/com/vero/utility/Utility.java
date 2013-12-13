/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.utility;

import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author yulinwen
 */
public class Utility {
    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            // it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
