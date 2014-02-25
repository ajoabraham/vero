/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import com.vero.model.entities.SchemaData;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.util.DataUtils;

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
        
        // FIXME TH 02/24/2014 Since GlobalFilterBlock doesn't have
        // a schema data yet. Check null here for now 
        if (schemaData != null && schemaData.getId() == null) {
            schemaData.setId(DataUtils.generateId());
        }
    }

    public String getId() {
        return schemaData.getId();
    }

    public void setId(String id) {
        schemaData.setId(id);
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj == null) 
	    return false;
	
	if (!(obj instanceof UIData))
	    return false;
	
	if (this == obj) 
	    return true;
	
	UIData other = (UIData) obj;
	
	if (getId() != null && other.getId() != null) {
	    return getId().equals(other.getId());
	}
	
	return false;
    }
    
    @Override
    public int hashCode() {
	if (getId() == null) {
	    return super.hashCode();
	}
	
	return getId().hashCode();
    }
    
    public abstract ObjectType getType();
}
