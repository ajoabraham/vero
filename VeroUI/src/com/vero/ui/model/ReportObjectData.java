/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import java.util.List;

import com.vero.ui.constants.ObjectType;

import static com.vero.ui.constants.ObjectType.REPORT;

/**
 *
 * @author Tai Hu
 */
public class ReportObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private String name = null;
    private GlobalFilterObjectData globalFilterObjectData = null;
    private ReportBlockObjectData reportBlockObjectData = null;
    private List<BlockObjectData> blockObjectDataList = null;
    
    public ReportObjectData() {
        
    }
    
    @Override
    public ObjectType getType() {
        return REPORT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GlobalFilterObjectData getGlobalFilterObjectData() {
        return globalFilterObjectData;
    }

    public void setGlobalFilterObjectData(GlobalFilterObjectData globalFilterObjectData) {
        this.globalFilterObjectData = globalFilterObjectData;
        this.globalFilterObjectData.setReportObjectData(this);
    }

    public ReportBlockObjectData getReportBlockObjectData() {
        return reportBlockObjectData;
    }

    public void setReportBlockObjectData(ReportBlockObjectData reportBlockObjectData) {
        this.reportBlockObjectData = reportBlockObjectData;
        this.reportBlockObjectData.setReportObjectData(this);
    }

    public List<BlockObjectData> getBlockObjectDataList() {
        return blockObjectDataList;
    }

    public void setBlockObjectDataList(List<BlockObjectData> blockObjectDataList) {
        this.blockObjectDataList = blockObjectDataList;
    }
}
