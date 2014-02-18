/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.REPORT_BLOCK;

import com.vero.model.entities.SchemaReportBlock;
import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class ReportBlockObjectData extends QueryBlockObjectData {
    
    public ReportBlockObjectData() {
	this(new SchemaReportBlock());
    }
    
    public ReportBlockObjectData(SchemaReportBlock schemaReportBlock) {
	super(schemaReportBlock);
    }
    
    @Override
    public ObjectType getType() {
	return REPORT_BLOCK;
    }
}
