/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.DATASOURCE;
import com.vero.ui.model.DataSourceObjectData;

/**
 *
 * @author Tai Hu
 */
public class DataSourceObjectPane extends ObjectPane {
    private DataSourceObjectData data = null;
    
    public DataSourceObjectPane(DataSourceObjectData data) {
        this.data = data;
        buildUI();
    }
    
    private void buildUI() {
        
    }
    
    @Override
    public ObjectType getType() {
        return DATASOURCE;
    }
    
}
