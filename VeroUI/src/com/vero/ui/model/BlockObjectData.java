/**
 * 
 */
package com.vero.ui.model;

import com.vero.model.entities.SchemaCommentBlock;
import com.vero.model.entities.SchemaData;
import com.vero.model.entities.SchemaQueryBlock;
import com.vero.model.entities.SchemaReportBlock;


/**
 * @author Tai Hu
 *
 */
public abstract class BlockObjectData extends UIData {
    private ReportObjectData reportObjectData = null;
    private String metadata = null;
    SchemaData schemaData = null;
    
    public BlockObjectData(SchemaData schemaData) {
	super(schemaData);
	this.schemaData = schemaData;
    }

    public ReportObjectData getReportObjectData() {
        return reportObjectData;
    }

    public void setReportObjectData(ReportObjectData reportObjectData) {
        this.reportObjectData = reportObjectData;
        
        if (schemaData instanceof SchemaQueryBlock) {
            if (reportObjectData == null) {
        	((SchemaQueryBlock) schemaData).setSchemaReport(null);
            }
            else if (reportObjectData.getSchemaReport() != ((SchemaQueryBlock) schemaData).getSchemaReport()) {
        	((SchemaQueryBlock) schemaData).setSchemaReport(reportObjectData.getSchemaReport());
            }
        }
        else if (schemaData instanceof SchemaReportBlock){
            if (reportObjectData == null) {
        	((SchemaReportBlock) schemaData).setSchemaReport(null);
            }
            else if (reportObjectData.getSchemaReport() != ((SchemaReportBlock) schemaData).getSchemaReport()) {
        	((SchemaReportBlock) schemaData).setSchemaReport(reportObjectData.getSchemaReport());
            }
        }
        else {
            if (reportObjectData == null) {
        	((SchemaCommentBlock) schemaData).setSchemaReport(null);
            }
            else if (reportObjectData.getSchemaReport() != ((SchemaCommentBlock) schemaData).getSchemaReport()) {
        	((SchemaCommentBlock) schemaData).setSchemaReport(reportObjectData.getSchemaReport());
            }
        }
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
