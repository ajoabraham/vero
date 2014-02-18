/**
 * 
 */
package com.vero.ui.util;

import com.vero.metadata.Column;
import com.vero.metadata.Table;
import com.vero.ui.constants.DBKeyType;
import com.vero.ui.constants.TableType;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.TableObjectData;

/**
 * @author Tai Hu
 *
 */
public final class DataUtils {
    private DataUtils() {	
    }
    
    // Copy between UIData and Metadata
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
//	target.setDataTypeSize(source.getDataTypeSize());
    }
    
    public static void copy(Table source, TableObjectData target) {
	target.setName(source.getObjectName());
	target.setPhysicalName(source.getPhysicalName());
	target.setRowCount(source.getRowCount());
	target.setTableType(TableType.valueOf(source.getTableLogicalType().toString()));	
	
	for (Column column : source.getColumns().values()) {
	    ColumnObjectData columnObjectData = new ColumnObjectData();
	    copy(column, columnObjectData);
	    target.addColumnObjectData(columnObjectData);
	}
    }
    
    public static void copy(Column source, ColumnObjectData target) {
	target.setName(source.getObjectName());
	target.setKeyType(DBKeyType.valueOf(source.getKeyType().toString()));
	target.setDataType(source.getDataType());
//	target.setDataTypeSize(source.getDataTypeSize());
    }
    
    // Copy between UIData and persistent data
//    public static void copy(DatasourceObjectData source, SchemaDatasource target) {
//        target.setId(source.getId());
//        target.setName(source.getName());
//        
//        if (source.getProjectObjectData() != null) {
//            SchemaProject schemaProject = new SchemaProject();
//            schemaProject.setId(source.getProjectObjectData().getId());
//            target.setSchemaProject(schemaProject);
//        }
//        
//        SchemaDatabase schemaDatabase = new SchemaDatabase();
//        copy(source.getDatabaseObjectData(), schemaDatabase);
//        target.setSchemaDatabase(schemaDatabase);
//
//        target.setSchemaTables(new ArrayList<SchemaTable>());
//        
//        for (TableObjectData tableObjectData : source.getTableObjectDataList()) {
//            SchemaTable schemaTable = new SchemaTable();
//            copy(tableObjectData, schemaTable);
//            target.addSchemaTable(schemaTable);
//        }
//    }
//    
//    private static void copy(DatabaseObjectData source, SchemaDatabase target) {
//        target.setId(source.getId());
//        target.setDatabaseName(source.getDatabaseName());
//        target.setHostAddress(source.getHostname());
//        target.setPort(source.getPort());
//        target.setUserName(source.getUserName());
//        target.setPassword(source.getPassword());
//    }
//    
//    private static void copy(TableObjectData source, SchemaTable target) {
//        target.setId(source.getId());
//        target.setName(source.getName());
//        target.setPhysicalName(source.getPhysicalName());
//        target.setRowCount(source.getRowCount());
//        target.setTableType(source.getTableType().ordinal());
//        
//        target.setSchemaColumns(new ArrayList<SchemaColumn>());
//        
//        for (ColumnObjectData columnObjectData : source.getColumnObjectDataList()) {
//            SchemaColumn schemaColumn = new SchemaColumn();
//            copy(columnObjectData, schemaColumn);
//            target.addSchemaColumn(schemaColumn);
//        }
//    }
//    
//    private static void copy(ColumnObjectData source, SchemaColumn target) {
//        target.setId(source.getId());
//        target.setDataType(source.getDataType());
//        target.setName(source.getName());
//        target.setKeyType(source.getKeyType().ordinal());
//        
//        List<SchemaExpression> schemaExpressions = new ArrayList<SchemaExpression>();
//        for (ExpressionObjectData expressionObjectData : source.getExpressionObjectDataList()) {
//            SchemaExpression schemaExpression = new SchemaExpression();
//            schemaExpression.setId(expressionObjectData.getId());
//            schemaExpressions.add(schemaExpression);
//        }
//        
//        target.setSchemaExpressions(schemaExpressions);
//    }
//    
//    public static void copy(SchemaProject source, ProjectObjectData target) {
//        target.setId(source.getId());
//        target.setName(source.getName());
//                
//        for (SchemaDatasource schemaDatasource : source.getSchemaDatasources()) {
//            DatasourceObjectData datasourceObjectData = new DatasourceObjectData();
//            copy(schemaDatasource, datasourceObjectData);
//            target.addDatasourceObjectData(datasourceObjectData);
//        }
//    }
//    
//    private static void copy(SchemaDatasource source, DatasourceObjectData target) {
//        target.setId(source.getId());
//        target.setName(source.getName());
//        
//        DatabaseObjectData databaseObjectData = new DatabaseObjectData();
//        copy(source.getSchemaDatabase(), databaseObjectData);
//        target.setDatabaseObjectData(databaseObjectData);
//        
//        for (SchemaTable schemaTable : source.getSchemaTables()) {
//            TableObjectData tableObjectData = new TableObjectData();
//            copy(schemaTable, tableObjectData);
//            target.addTableObjectData(tableObjectData);
//        }
//    }
//    
//    private static void copy(SchemaDatabase source, DatabaseObjectData target) {
//        target.setId(source.getId());
//	target.setDatabaseName(source.getDatabaseName());
//        target.setHostname(source.getHostAddress());
//        target.setPort(source.getPort());
//        target.setUserName(source.getUserName());
//        target.setPassword(source.getPassword());
//    }
//    
//    public static void copy(SchemaTable source, TableObjectData target) {
//        target.setId(source.getId());
//        target.setName(source.getName());
//        target.setPhysicalName(source.getPhysicalName());
//        target.setRowCount(source.getRowCount());
//        target.setTableType(TableType.values()[source.getTableType()]);
//        
//        for (SchemaColumn schemaColumn : source.getSchemaColumns()) {
//            ColumnObjectData columnObjectData = new ColumnObjectData();
//            copy(schemaColumn, columnObjectData);
//            target.addColumnObjectData(columnObjectData);
//        }
//    }
//    
//    private static void copy(SchemaColumn source, ColumnObjectData target) {
//        target.setId(source.getId());
//        target.setDataType(source.getDataType());
//        target.setName(source.getName());
//        target.setKeyType(DBKeyType.values()[source.getKeyType()]);
//        
//        for (SchemaExpression schemaExpression : source.getSchemaExpressions()) {
//            ExpressionObjectData expressionObjectData = new ExpressionObjectData();
//            expressionObjectData.setId(schemaExpression.getId());
//            target.getExpressionObjectDataList().add(expressionObjectData);
//        }
//    }
//    
//    public static void copy(SchemaAttribute source, AttributeObjectData target) {
//        target.setId(source.getId());
//        target.setName(source.getName());
//        
////        for (SchemaExpression schemaExpression : source.getSchemaExpressions()) {
////            ExpressionObjectData expressionObjectData = new ExpressionObjectData();
////            DataUtils.copy(schemaExpression, expressionObjectData);
////            target.addExpressionObjectData(expressionObjectData);
////        }
//    }
//       
//    public static void copy(SchemaMetric source, MetricObjectData target) {
//        target.setId(source.getId());
//        target.setName(source.getName());
//    }
}
