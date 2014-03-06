/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.TABLE;

import com.vero.model.entities.SchemaColumn;
import com.vero.model.entities.SchemaTable;
import com.vero.ui.common.UIDataManager;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.constants.TableType;
import com.vero.ui.service.ServiceException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * 
 * @author Tai Hu
 */
public class TableObjectData extends UIData {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TableObjectData.class.getName());

    private SchemaTable schemaTable = null;

    private StringProperty name = new SimpleStringProperty();
    private StringProperty physicalName = new SimpleStringProperty();
    private StringProperty alias = new SimpleStringProperty();
    private IntegerProperty rowCount = new SimpleIntegerProperty();
    private TableType tableType = TableType.UNKNOWN;
    private List<ColumnObjectData> columnObjectDataList = null;
    private DatasourceObjectData datasourceObjectData = null;
    private List<ColumnObjectData> unusedColumnObjectDataList = null;

    public TableObjectData() {
        this(new SchemaTable());
    }

    public TableObjectData(SchemaTable schemaTable) {
        super(schemaTable);
        this.schemaTable = schemaTable;

        // init data
        name.set(schemaTable.getName());
        name.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                TableObjectData.this.schemaTable.setName(newValue);
            }

        });

        physicalName.set(schemaTable.getPhysicalName());
        physicalName.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                TableObjectData.this.schemaTable.setPhysicalName(newValue);
            }

        });

        rowCount.set(schemaTable.getRowCount());
        rowCount.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                TableObjectData.this.schemaTable.setRowCount(newValue.intValue());
            }

        });

        tableType = TableType.values()[schemaTable.getTableType()];
        datasourceObjectData = new DatasourceObjectData(schemaTable.getSchemaDatasource());
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

    public String getAlias() {
        return alias.get();
    }

    public void setAlias(String alias) {
        this.alias.set(alias);
    }

    public StringProperty aliasProperty() {
        return alias;
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
        schemaTable.setTableType(tableType.ordinal());
    }

    public List<ColumnObjectData> getColumnObjectDataList() {
        if (columnObjectDataList == null) initColumnObjectDataList();
        return columnObjectDataList;
    }

    // public void setColumnObjectDataList(List<ColumnObjectData>
    // columnObjectDataList) {
    // this.columnObjectDataList = columnObjectDataList;
    // }

    public void addColumnObjectData(ColumnObjectData data) {
        if (columnObjectDataList == null) initColumnObjectDataList();
        data.setTableObjectData(this);
        columnObjectDataList.add(data);
        schemaTable.addSchemaColumn(data.getSchemaColumn());
    }

    public boolean removeColumnObjectData(ColumnObjectData data) {
        if (columnObjectDataList == null) initColumnObjectDataList();
        data.setTableObjectData(null);
        schemaTable.removeSchemaColumn(data.getSchemaColumn());
        return columnObjectDataList.remove(data);
    }
    
    public void removeAllColumnObjectData() {
        if (columnObjectDataList == null) initColumnObjectDataList();
        // FIXME TH 02/20/2014, this potentially could cause memory leak.
        // Need to reinvestigate in the next version.
        columnObjectDataList.clear();
        schemaTable.getSchemaColumns().clear();
    }

    public DatasourceObjectData getDatasourceObjectData() {
        return datasourceObjectData;
    }

    public void setDatasourceObjectData(DatasourceObjectData datasourceObjectData) {
        this.datasourceObjectData = datasourceObjectData;

        if (datasourceObjectData == null) {
            schemaTable.setSchemaDatasource(null);
        }
        else if (schemaTable.getSchemaDatasource() != datasourceObjectData.getSchemaDatasource()) {
            schemaTable.setSchemaDatasource(datasourceObjectData.getSchemaDatasource());
        }
    }

    private void initColumnObjectDataList() {
        columnObjectDataList = new ArrayList<ColumnObjectData>();

        if (schemaTable.getSchemaColumns() == null) {
            schemaTable.setSchemaColumns(new ArrayList<SchemaColumn>());
        }
        
        for (SchemaColumn schemaColumn : schemaTable.getSchemaColumns()) {
            ColumnObjectData columnObjectData = new ColumnObjectData(schemaColumn);
            columnObjectData.setTableObjectData(this);
            columnObjectDataList.add(columnObjectData);
        }
    }

    public List<AttributeObjectData> getRelatedAttributeObjectDataList() {
        List<AttributeObjectData> attributeObjectDataList = null;

        try {
            attributeObjectDataList = UIDataManager.getInstance().getRelatedAttributeObjectDataList(getId());
        }
        catch (ServiceException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            if (attributeObjectDataList == null) {
                attributeObjectDataList = new ArrayList<AttributeObjectData>();
            }
        }

        return attributeObjectDataList;
    }

    public List<MetricObjectData> getRelatedMetricObjectDataList() {
        List<MetricObjectData> metricObjectDataList = null;

        try {
            metricObjectDataList = UIDataManager.getInstance().getRelatedMetricObjectDataList(getId());
        }
        catch (ServiceException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            if (metricObjectDataList == null) {
                metricObjectDataList = new ArrayList<MetricObjectData>();
            }
        }

        return metricObjectDataList;
    }

    public List<ColumnObjectData> getUnusedColumnObjectDataList() {
        if (columnObjectDataList == null) initColumnObjectDataList();
        if (unusedColumnObjectDataList == null) {
            unusedColumnObjectDataList = new ArrayList<ColumnObjectData>();

            for (ColumnObjectData columnObjectData : columnObjectDataList) {
                if (!columnObjectData.isUsed()) {
                    unusedColumnObjectDataList.add(columnObjectData);
                }
            }
        }

        return unusedColumnObjectDataList;
    }

    public boolean containsColumn(String columnName) {
        for (ColumnObjectData columnObjectData : columnObjectDataList) {
            if (columnName.equals(columnObjectData.getName())) {
                return true;
            }
        }

        return false;
    }

    public boolean containsColumns(Collection<String> columnNames) {
        if (columnObjectDataList == null) initColumnObjectDataList();
        List<String> allColumnNames = new ArrayList<String>();
        for (ColumnObjectData columnObjectData : columnObjectDataList) {
            allColumnNames.add(columnObjectData.getName());
        }

        return allColumnNames.containsAll(columnNames);
    }

    public List<ColumnObjectData> getColumnsByNames(Collection<String> columnNames) {
        List<ColumnObjectData> columns = new ArrayList<ColumnObjectData>();

        for (ColumnObjectData columnObjectData : columnObjectDataList) {
            if (columnNames.contains(columnObjectData.getName())) {
                columns.add(columnObjectData);
            }
        }

        return columns;
    }

    public SchemaTable getSchemaTable() {
        return schemaTable;
    }
}
