/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.model.UIData;
import com.vero.ui.util.CommonUtil;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tai Hu
 */
public final class DragAndDropDataManager {
    private static DragAndDropDataManager INSTANCE = null;
     
    private Map<String, UIData> uiDataMap = null;
    
    private DragAndDropDataManager() {
        uiDataMap = new HashMap<>();
    }
    
    public static DragAndDropDataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DragAndDropDataManager();
        }
        
        return INSTANCE;
    }
    
    public String putData(UIData data) {
        String id = CommonUtil.generateId();
        uiDataMap.put(id, data);
        return id;
    }
    
    public UIData getData(String id) {
        return uiDataMap.get(id);
    }
    
    public void removeData(String id) {
        uiDataMap.remove(id);
    }
}
