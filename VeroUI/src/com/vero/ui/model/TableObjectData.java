/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.TABLE;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.constants.TableType;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Tai Hu
 */
public class TableObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private StringProperty name = new SimpleStringProperty();
    private StringProperty physicalName = new SimpleStringProperty();
    private IntegerProperty rowCount = new SimpleIntegerProperty();
    private TableType tableType = TableType.UNKNOWN;
    private List<ColumnObjectData> columnObjectDataList = new ArrayList<ColumnObjectData>();
    private List<AttributeObjectData> attributeObjectDataList = new ArrayList<AttributeObjectData>();
    private List<MetricObjectData> metricObjectDataList = new ArrayList<MetricObjectData>();
    
    public TableObjectData() {
        
    }

    @Override
    public ObjectType getType() {
        return TABLE;
    }
    
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty() {
	return name;
    }

    public String getPhysicalName() {
        return physicalName.get();
    }

    public void setPhysicalName(String physicalName) {
        this.physicalName.set(physicalName);
    }
    
    public StringProperty physicalNameProperty() {
	return physicalName;
    }

    public int getRowCount() {
        return rowCount.get();
    }

    public void setRowCount(int rowCount) {
        this.rowCount.set(rowCount);
    }
    
    public IntegerProperty rowCountProperty() {
	return rowCount;
    }

    public TableType getTableType() {
        return tableType;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public List<ColumnObjectData> getColumnObjectDataList() {
        return columnObjectDataList;
    }

    public void setColumnObjectDataList(List<ColumnObjectData> columnObjectDataList) {
        this.columnObjectDataList = columnObjectDataList;
    }

    public List<AttributeObjectData> getAttributeObjectDataList() {
        return attributeObjectDataList;
    }

    public void setAttributeObjectDataList(List<AttributeObjectData> attributeObjectDataList) {
        this.attributeObjectDataList = attributeObjectDataList;
    }

    public List<MetricObjectData> getMetricObjectDataList() {
        return metricObjectDataList;
    }

    public void setMetricObjectDataList(List<MetricObjectData> metricObjectDataList) {
        this.metricObjectDataList = metricObjectDataList;
    }
}
