/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui;

/**
 * All UI components are registered with UIManager once created.
 * Singleton pattern allow access to UI component anyway in the
 * application.
 * 
 * @author Tai Hu
 */
public final class UIManager {
    private static UIManager INSTANCE = null;
    
    private UIManager() {
        
    }
    
    public static UIManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UIManager();
        }
        
        return INSTANCE;
    }
}
