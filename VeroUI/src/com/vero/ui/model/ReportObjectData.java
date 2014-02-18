/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.vero.model.entities.SchemaReport;
import com.vero.ui.constants.ObjectType;

import static com.vero.ui.constants.ObjectType.REPORT;

/**
 *
 * @author Tai Hu
 */
public class ReportObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private SchemaReport schemaReport = null;
    
    private StringProperty name = new SimpleStringProperty();
    private ProjectObjectData projectObjectData = null;
    private GlobalFilterObjectData globalFilterObjectData = null;
    private ReportBlockObjectData reportBlockObjectData = null;
    private List<BlockObjectData> blockObjectDataList = null;
    
    public ReportObjectData() {
        this(new SchemaReport());
    }
    
    public ReportObjectData(SchemaReport schemaReport) {
	super(schemaReport);
	this.schemaReport = schemaReport;
	
	// init data
	name.set(schemaReport.getName());
	name.addListener(new ChangeListener<String>() {

	    @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	        ReportObjectData.this.schemaReport.setName(newValue);
            }
	    
	});
    }
    
    @Override
    public ObjectType getType() {
        return REPORT;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty name() {
	return name;
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
	if (blockObjectDataList == null) initBlockObjectDataList();
        return blockObjectDataList;
    }

//    public void setBlockObjectDataList(List<BlockObjectData> blockObjectDataList) {
//        this.blockObjectDataList = blockObjectDataList;
//    }
    
    public void addBlockObjectData(BlockObjectData blockObjectData) {
	if (blockObjectDataList == null) initBlockObjectDataList();
        blockObjectDataList.add(blockObjectData);
        blockObjectData.setReportObjectData(this);
    }
    
    public boolean removeBlockObjectData(BlockObjectData blockObjectData) {
	if (blockObjectDataList == null) initBlockObjectDataList();
        blockObjectData.setReportObjectData(null);
        return blockObjectDataList.remove(blockObjectData);
    }

    public ProjectObjectData getProjectObjectData() {
        return projectObjectData;
    }

    public void setProjectObjectData(ProjectObjectData projectObjectData) {
        this.projectObjectData = projectObjectData;
        
        if (projectObjectData == null) {
            schemaReport.setSchemaProject(null);
        }
        else if (projectObjectData.getSchemaProject() != projectObjectData.getSchemaProject()) {
            schemaReport.setSchemaProject(projectObjectData.getSchemaProject());
        }
    }
    
    private void initBlockObjectDataList() {
	blockObjectDataList = new ArrayList<BlockObjectData>();

    }
    
    public SchemaReport getSchemaReport() {
	return schemaReport;
    }
}
