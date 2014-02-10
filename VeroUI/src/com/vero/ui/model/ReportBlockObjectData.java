/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.REPORT_BLOCK;

import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class ReportBlockObjectData extends QueryBlockObjectData {
    
    public ReportBlockObjectData() {
    }
    
    @Override
    public ObjectType getType() {
	return REPORT_BLOCK;
    }
}
