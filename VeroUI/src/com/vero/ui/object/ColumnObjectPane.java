/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.COLUMN;
import com.vero.ui.model.ColumnObjectData;

/**
 *
 * @author Tai Hu
 */
public class ColumnObjectPane extends ObjectPane {
    private ColumnObjectData data = null;
    
    public ColumnObjectPane(ColumnObjectData data) {
        this.data = data;
    }
    
    @Override
    public ObjectType getType() {
        return COLUMN;
    }
    
}
