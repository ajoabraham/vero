/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.QUERY_BLOCK;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class QueryBlockObjectData extends BlockObjectData {
    private IntegerProperty position = new SimpleIntegerProperty();

    public QueryBlockObjectData() {
    }

    public void setPosition(int position) {
	this.position.set(position);
    }
    
    public int getPosition() {
	return this.position.get();
    }
    
    public IntegerProperty position() {
	return position;
    }
    
    @Override
    public ObjectType getType() {
	return QUERY_BLOCK;
    }

}
