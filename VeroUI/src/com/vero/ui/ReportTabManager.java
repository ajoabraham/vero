/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui;

/**
 * This class handles all report tab panes in the application.
 * 
 * @author Tai Hu
 */
public final class ReportTabManager {
    private static ReportTabManager INSTANCE = null;
    
    private ReportTabManager() {
        
    }
    
    public static ReportTabManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReportTabManager();
        }
        
        return INSTANCE;
    }
}
