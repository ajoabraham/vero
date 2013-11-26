/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.ATTRIBUTE;
import com.vero.ui.model.AttributeObjectData;

/**
 *
 * @author Tai Hu
 */
public class AttributeObjectPane extends ObjectPane {
    private AttributeObjectData data = null;
    
    public AttributeObjectPane(AttributeObjectData data) {
        this.data = data;
    }
    
    @Override
    public ObjectType getType() {
        return ATTRIBUTE;
    }
    
}
