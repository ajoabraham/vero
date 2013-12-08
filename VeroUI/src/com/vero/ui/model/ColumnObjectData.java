/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.COLUMN;

/**
 *
 * @author Tai Hu
 */
public class ColumnObjectData extends UIData {
    private String name = null;
    
    public ColumnObjectData() {
        
    }
    
    @Override
    public ObjectType getType() {
        return COLUMN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
