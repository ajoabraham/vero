/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import com.vero.model.entities.SchemaData;
import com.vero.ui.constants.ObjectType;

import java.io.Serializable;

/**
 *
 * @author Tai Hu
 */
public abstract class UIData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private SchemaData schemaData = null;
    
    public UIData(SchemaData schemaData) {
        this.schemaData = schemaData;
    }

    public String getId() {
        return schemaData.getId();
    }

    public void setId(String id) {
        schemaData.setId(id);
    }
    
    public abstract ObjectType getType();
}
