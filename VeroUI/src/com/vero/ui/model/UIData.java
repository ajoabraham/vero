/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import com.vero.ui.constants.ObjectType;
import java.io.Serializable;

/**
 *
 * @author Tai Hu
 */
public abstract class UIData implements Serializable {
    private long Id = 0L;
    
    public UIData() {
        
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }
    
    public abstract ObjectType getType();
}
