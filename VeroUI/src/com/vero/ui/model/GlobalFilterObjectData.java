/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.GLOBAL_FILTER;

import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class GlobalFilterObjectData extends UIData {
    private ReportObjectData reportObjectData = null;

    /**
     * 
     */
    public GlobalFilterObjectData() {
    }

    public ReportObjectData getReportObjectData() {
        return reportObjectData;
    }

    public void setReportObjectData(ReportObjectData reportObjectData) {
        this.reportObjectData = reportObjectData;
    }


    @Override
    public ObjectType getType() {
	return GLOBAL_FILTER;
    }
}
