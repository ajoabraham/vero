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
    private static final long serialVersionUID = 1L;
    
    private String Id = null;
    
    public UIData() {
        
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
    
    public abstract ObjectType getType();
}
