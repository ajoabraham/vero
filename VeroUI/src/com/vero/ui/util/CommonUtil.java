/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.util;

import java.util.UUID;

/**
 *
 * @author Tai Hu
 */
public final class CommonUtil {
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
