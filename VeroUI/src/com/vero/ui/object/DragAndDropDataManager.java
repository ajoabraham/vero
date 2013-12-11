/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.model.UIData;

/**
 *
 * @author Tai Hu
 */
public final class DragAndDropDataManager {
    private static DragAndDropDataManager INSTANCE = null;
    
    private UIData data = null;
    
    private DragAndDropDataManager() {
        
    }
    
    public static DragAndDropDataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DragAndDropDataManager();
        }
        
        return INSTANCE;
    }
    
    public void setData(UIData data) {
        this.data = data;
    }
    
    public UIData getData() {
        return data;
    }
}
