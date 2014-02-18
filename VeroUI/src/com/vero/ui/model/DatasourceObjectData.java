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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import org.hibernate.validator.constraints.NotBlank;

import com.vero.model.entities.SchemaDatabase;
import com.vero.model.entities.SchemaDatasource;
import com.vero.model.entities.SchemaTable;
import com.vero.ui.constants.DatasourceStatus;
import com.vero.ui.constants.ObjectType;

/**
 * 
 * @author Tai Hu
 */
public class DatasourceObjectData extends UIData {
    private static final long serialVersionUID = 1L;

    private SchemaDatasource schemaDatasource = null;

    private StringProperty name = new SimpleStringProperty();

    private DatabaseObjectData databaseObjectData = null;
    private List<TableObjectData> tableObjectDataList = null;
    private DatasourceStatus status = INACTIVE;

    private ProjectObjectData projectObjectData = null;

    public DatasourceObjectData() {
        this(new SchemaDatasource());
    }

    public DatasourceObjectData(SchemaDatasource schemaDatasource) {
        super(schemaDatasource);
        this.schemaDatasource = schemaDatasource;

        // init data
        name.set(schemaDatasource.getName());
        name.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                DatasourceObjectData.this.schemaDatasource.setName(newValue);
            }

        });

        SchemaDatabase schemaDatabase = schemaDatasource.getSchemaDatabase();
        if (schemaDatabase == null) {
            schemaDatabase = new SchemaDatabase();
            schemaDatasource.setSchemaDatabase(schemaDatabase);
        }

        databaseObjectData = new DatabaseObjectData(schemaDatabase);
        // databaseObjectData.setDatasourceObjectData(this);
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
        if (tableObjectDataList == null) initTableObjectDataList();
        return tableObjectDataList;
    }

    // public void setTableObjectDataList(List<TableObjectData>
    // tableObjectDataList) {
    // this.tableObjectDataList = tableObjectDataList;
    // }

    public void addTableObjectData(TableObjectData tableObjectData) {
        if (tableObjectDataList == null) initTableObjectDataList();
        tableObjectDataList.add(tableObjectData);
        tableObjectData.setDatasourceObjectData(this);
        schemaDatasource.addSchemaTable(tableObjectData.getSchemaTable());
    }

    public boolean removeTableObjectData(TableObjectData tableObjectData) {
        if (tableObjectDataList == null) initTableObjectDataList();
        tableObjectData.setDatasourceObjectData(null);
        schemaDatasource.removeSchemaTable(tableObjectData.getSchemaTable());
        return tableObjectDataList.remove(tableObjectData);
    }

    public DatasourceStatus getStatus() {
        return status;
    }

    public void setStatus(DatasourceStatus status) {
        this.status = status;
    }

    public DatabaseObjectData getDatabaseObjectData() {
        // if (databaseObjectData == null) {
        // setDatabaseObjectData(new DatabaseObjectData());
        // }
        return databaseObjectData;
    }

    // public void setDatabaseObjectData(DatabaseObjectData databaseObjectData)
    // {
    // databaseObjectData.setDatasourceObjectData(this);
    // this.databaseObjectData = databaseObjectData;
    // }

    public ProjectObjectData getProjectObjectData() {
        return projectObjectData;
    }

    public void setProjectObjectData(ProjectObjectData projectObjectData) {
        this.projectObjectData = projectObjectData;

        if (projectObjectData == null) {
            schemaDatasource.setSchemaProject(null);
        }
        else if (projectObjectData.getSchemaProject() != schemaDatasource.getSchemaProject()) {
            schemaDatasource.setSchemaProject(projectObjectData.getSchemaProject());
        }
    }

    private void initTableObjectDataList() {
        tableObjectDataList = new ArrayList<TableObjectData>();

        if (schemaDatasource.getSchemaTables() == null) {
            schemaDatasource.setSchemaTables(new ArrayList<SchemaTable>());
        }
        
        for (SchemaTable schemaTable : schemaDatasource.getSchemaTables()) {
            TableObjectData tableObjectData = new TableObjectData(schemaTable);
            tableObjectData.setDatasourceObjectData(this);
            tableObjectDataList.add(tableObjectData);
        }
    }

    public SchemaDatasource getSchemaDatasource() {
        return schemaDatasource;
    }
}
