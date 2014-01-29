/**
 * 
 */
package com.vero.ui.util;

import java.util.ArrayList;

import com.vero.metadata.Column;
import com.vero.metadata.Table;
import com.vero.model.entities.SchemaColumn;
import com.vero.model.entities.SchemaDatabase;
import com.vero.model.entities.SchemaDatasource;
import com.vero.model.entities.SchemaProject;
import com.vero.model.entities.SchemaTable;
import com.vero.ui.constants.DBKeyType;
import com.vero.ui.constants.TableType;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.DatabaseObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.ProjectObjectData;
import com.vero.ui.model.TableObjectData;

/**
 * @author Tai Hu
 *
 */
public final class DataUtils {
    private DataUtils() {	
    }
    
    public static void copy(ProjectObjectData source, SchemaProject target) {
        target.setId(source.getId());
        target.setName(source.getName());
        
        target.setSchemaDatasources(new ArrayList<SchemaDatasource>());
        
        for (DatasourceObjectData datasourceObjectData : source.getDatasourceObjectDataList()) {
            SchemaDatasource schemaDatasource = new SchemaDatasource();
            copy(datasourceObjectData, schemaDatasource);
            target.addSchemaDatasource(schemaDatasource);
        }
    }
    
    public static void copy(SchemaProject source, ProjectObjectData target) {
        target.setId(source.getId());
        target.setName(source.getName());
                
        for (SchemaDatasource schemaDatasource : source.getSchemaDatasources()) {
            DatasourceObjectData datasourceObjectData = new DatasourceObjectData();
            copy(schemaDatasource, datasourceObjectData);
            target.addDatasourceObjectData(datasourceObjectData);
        }
    }
    
    public static void copy(Table source, TableObjectData target) {
	target.setName(source.getObjectName());
	target.setPhysicalName(source.getPhysicalName());
	target.setRowCount(source.getRowCount());
	target.setTableType(TableType.valueOf(source.getTableLogicalType().toString()));	
	
	for (Column column : source.getColumns().values()) {
	    ColumnObjectData columnObjectData = new ColumnObjectData();
	    copy(column, columnObjectData);
	    target.getColumnObjectDataList().add(columnObjectData);
	}
    }
    
    public static void copy(Column source, ColumnObjectData target) {
	target.setName(source.getObjectName());
	target.setKeyType(DBKeyType.valueOf(source.getKeyType().toString()));
	target.setDataType(source.getDataType());
	target.setDataTypeSize(source.getDataTypeSize());
    }
    
    public static void copy(TableObjectData source, Table target) {
	target.setObjectName(source.getName());
	target.setPhysicalName(source.getPhysicalName());
	target.setRowCount(source.getRowCount());
	
	for (ColumnObjectData columnObjectData : source.getColumnObjectDataList()) {
	    Column column = new Column();
	    copy(columnObjectData, column);
	    target.addColumn(column);
	}
    }
    
    public static void copy(ColumnObjectData source, Column target) {
	target.setObjectName(source.getName());
	target.setKeyType(Column.KeyTypes.valueOf(source.getKeyType().toString()));
	target.setDataType(source.getDataType());
	target.setDataTypeSize(source.getDataTypeSize());
    }
     
    public static void copy(DatasourceObjectData source, SchemaDatasource target) {
        target.setId(source.getId());
        target.setName(source.getName());
        SchemaDatabase schemaDatabase = new SchemaDatabase();
        copy(source.getDatabaseObjectData(), schemaDatabase);
        target.setSchemaDatabase(schemaDatabase);

        target.setSchemaTables(new ArrayList<SchemaTable>());
        
        for (TableObjectData tableObjectData : source.getTableObjectDataList()) {
            SchemaTable schemaTable = new SchemaTable();
            copy(tableObjectData, schemaTable);
            target.addSchemaTable(schemaTable);
        }
    }
    
    public static void copy(DatabaseObjectData source, SchemaDatabase target) {
        target.setId(source.getId());
        target.setDatabaseName(source.getDatabaseName());
        target.setHostAddress(source.getHostname());
        target.setPort(source.getPort());
        target.setUserName(source.getUserName());
        target.setPassword(source.getPassword());
    }
    
    public static void copy(TableObjectData source, SchemaTable target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setPhysicalName(source.getPhysicalName());
        target.setRowCount(source.getRowCount());
        target.setTableType(source.getTableType().ordinal());
        
        target.setSchemaColumns(new ArrayList<SchemaColumn>());
        
        for (ColumnObjectData columnObjectData : source.getColumnObjectDataList()) {
            SchemaColumn schemaColumn = new SchemaColumn();
            copy(columnObjectData, schemaColumn);
            target.addSchemaColumn(schemaColumn);
        }
    }
    
    public static void copy(ColumnObjectData source, SchemaColumn target) {
        target.setId(source.getId());
        target.setDataType(source.getDataType());
        target.setName(source.getName());
        target.setKeyType(source.getKeyType().ordinal());
    }
    
    public static void copy(SchemaDatasource source, DatasourceObjectData target) {
        target.setId(source.getId());
        target.setName(source.getName());
        copy(source.getSchemaDatabase(), target.getDatabaseObjectData());
        
        for (SchemaTable schemaTable : source.getSchemaTables()) {
            TableObjectData tableObjectData = new TableObjectData();
            copy(schemaTable, tableObjectData);
            target.addTableObjectData(tableObjectData);
        }
    }
    
    public static void copy(SchemaDatabase source, DatabaseObjectData target) {
        target.setId(source.getId());
	target.setDatabaseName(source.getDatabaseName());
        target.setHostname(source.getHostAddress());
        target.setPort(source.getPort());
        target.setUserName(source.getUserName());
        target.setPassword(source.getPassword());
    }
    
    public static void copy(SchemaTable source, TableObjectData target) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setPhysicalName(source.getPhysicalName());
        target.setRowCount(source.getRowCount());
        target.setTableType(TableType.values()[source.getTableType()]);
        
        for (SchemaColumn schemaColumn : source.getSchemaColumns()) {
            ColumnObjectData columnObjectData = new ColumnObjectData();
            copy(schemaColumn, columnObjectData);
            target.addColumnObjectData(columnObjectData);
        }
    }
    
    public static void copy(SchemaColumn source, ColumnObjectData target) {
        target.setId(source.getId());
        target.setDataType(source.getDataType());
        target.setName(source.getName());
        target.setKeyType(DBKeyType.values()[source.getKeyType()]);
    }
}
