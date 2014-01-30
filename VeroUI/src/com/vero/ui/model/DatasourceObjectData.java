/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import static com.vero.ui.constants.DatasourceStatus.INACTIVE;
import static com.vero.ui.constants.ObjectType.DATASOURCE;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.hibernate.validator.constraints.NotBlank;

import com.vero.ui.constants.DatasourceStatus;
import com.vero.ui.constants.ObjectType;

/**
 *
 * @author Tai Hu
 */
public class DatasourceObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private StringProperty name = new SimpleStringProperty();
    
    private DatabaseObjectData databaseObjectData = null;
    private List<TableObjectData> tableObjectDataList = new ArrayList<TableObjectData>();
    private DatasourceStatus status = INACTIVE;
    
    private ProjectObjectData projectObjectData = null;
    
    public DatasourceObjectData() {        
    }
    
    @Override
    public ObjectType getType() {
        return DATASOURCE;
    }

    @NotBlank(message = "Name cannot be blank.")
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty() {
        return name;
    }

    public List<TableObjectData> getTableObjectDataList() {
        return tableObjectDataList;
    }

    public void setTableObjectDataList(List<TableObjectData> tableObjectDataList) {
        this.tableObjectDataList = tableObjectDataList;
    }
    
    public void addTableObjectData(TableObjectData tableObjectData) {
	tableObjectDataList.add(tableObjectData);
	tableObjectData.setDatasourceObjectData(this);
    }
    
    public boolean removeTableObjectData(TableObjectData tableObjectData) {
	tableObjectData.setDatasourceObjectData(null);
	return tableObjectDataList.remove(tableObjectData);
    }

    public DatasourceStatus getStatus() {
        return status;
    }

    public void setStatus(DatasourceStatus status) {
        this.status = status;
    }

    public DatabaseObjectData getDatabaseObjectData() {
        if (databaseObjectData == null) {
            setDatabaseObjectData(new DatabaseObjectData());
        }
	return databaseObjectData;
    }

    public void setDatabaseObjectData(DatabaseObjectData databaseObjectData) {
	databaseObjectData.setDatasourceObjectData(this);
        this.databaseObjectData = databaseObjectData;
    }

    public ProjectObjectData getProjectObjectData() {
        return projectObjectData;
    }

    public void setProjectObjectData(ProjectObjectData projectObjectData) {
        this.projectObjectData = projectObjectData;
    }
}
