/**
 * 
 */
package com.vero.ui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.vero.model.entities.SchemaData;

/**
 * @author Tai Hu
 *
 */
public abstract class PositionableObjectData extends UIData implements Comparable<PositionableObjectData> {
    private IntegerProperty position = new SimpleIntegerProperty();
    
    public PositionableObjectData(SchemaData schemaData) {
        super(schemaData);
    }

    public int getPosition() {
        return position.get();
    }
    
    public void setPosition(int position) {
        this.position.set(position);
    }
    
    public IntegerProperty positionProperty() {
        return position;
    }
    
    @Override
    public int compareTo(PositionableObjectData o) {
        if (this.getPosition() < o.getPosition()) {
            return -1;
        }
        else if (this.getPosition() > o.getPosition()) {
            return 1;
        }
        else {
            return 0;
        }
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        
        sb.append(super.toString());
        sb.append("Position = " + getPosition() + "\n");
        
        return sb.toString();
    }
}
