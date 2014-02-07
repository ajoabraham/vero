/**
 * 
 */
package com.vero.ui.model;


/**
 * @author Tai Hu
 *
 */
public abstract class BlockObjectData extends UIData {
    private ReportObjectData reportObjectData = null;
    
    /**
     * 
     */
    public BlockObjectData() {
    }

    public ReportObjectData getReportObjectData() {
        return reportObjectData;
    }

    public void setReportObjectData(ReportObjectData reportObjectData) {
        this.reportObjectData = reportObjectData;
    }
}
