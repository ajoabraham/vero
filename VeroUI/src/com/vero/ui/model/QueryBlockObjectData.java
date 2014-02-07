/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.QUERY_BLOCK;

import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class QueryBlockObjectData extends BlockObjectData {

    /**
     * 
     */
    public QueryBlockObjectData() {
    }

    @Override
    public ObjectType getType() {
	return QUERY_BLOCK;
    }

}
