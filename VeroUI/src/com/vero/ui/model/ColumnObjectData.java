/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.COLUMN;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.vero.model.entities.SchemaColumn;
import com.vero.model.entities.SchemaExpression;
import com.vero.ui.constants.DBKeyType;
import com.vero.ui.constants.ObjectType;

/**
 *
 * @author Tai Hu
 */
public class ColumnObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private SchemaColumn schemaColumn = null;
    
    private StringProperty name = new SimpleStringProperty();
    private StringProperty dataType = new SimpleStringProperty();
//    private IntegerProperty dataTypeSize = new SimpleIntegerProperty();
    private DBKeyType keyType = DBKeyType.NO_KEY_TYPE;
    private List<ExpressionObjectData> expressionObjectDataList = null;
    private TableObjectData tableObjectData = null;
    
    public ColumnObjectData() {        
        this(new SchemaColumn());
    }
    
    public ColumnObjectData(SchemaColumn schemaColumn) {
        super(schemaColumn);
        this.schemaColumn = schemaColumn;
        
        // init data
        name.set(schemaColumn.getName());
        name.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ColumnObjectData.this.schemaColumn.setName(newValue);
            }
            
        });
        
        dataType.set(schemaColumn.getDataType());
        dataType.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ColumnObjectData.this.schemaColumn.setDataType(newValue);
            }
            
        });
        
        keyType = DBKeyType.values()[schemaColumn.getKeyType()];
    }
    
    @Override
    public ObjectType getType() {
        return COLUMN;
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

    public String getDataType() {
        return dataType.get();
    }

    public void setDataType(String dataType) {
        this.dataType.set(dataType);
    }
    
    public StringProperty dataTypeProperty() {
	return dataType;
    }

//    public int getDataTypeSize() {
//        return dataTypeSize.get();
//    }
//
//    public void setDataTypeSize(int dataTypeSize) {
//        this.dataTypeSize.set(dataTypeSize);
//    }
//    
//    public IntegerProperty dataTypeSizeProperty() {
//	return dataTypeSize;
//    }

    public DBKeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(DBKeyType keyType) {
        this.keyType = keyType;
        schemaColumn.setKeyType(keyType.ordinal());
    }

    public List<ExpressionObjectData> getExpressionObjectDataList() {
        if (expressionObjectDataList == null) initExpressionObjectDataList();
        return expressionObjectDataList;
    }

//    public void setExpressionObjectDataList(List<ExpressionObjectData> expressionObjectDataList) {
//        this.expressionObjectDataList = expressionObjectDataList;
//    }
    
//    public void addExpressionObjectData(ExpressionObjectData expressionObjectData) {
//        expressionObjectDataList.add(expressionObjectData);
//        expressionObjectData.addColumnObjectData(this);
//    }
//    
//    public boolean removeExpressionObjectData(ExpressionObjectData expressionObjectData) {
//        expressionObjectData.removeColumnObjectData(this);
//        return expressionObjectDataList.remove(expressionObjectData);
//    }

    public TableObjectData getTableObjectData() {
        return tableObjectData;
    }

    public void setTableObjectData(TableObjectData tableObjectData) {
        this.tableObjectData = tableObjectData;
        
        if (tableObjectData == null) {
            schemaColumn.setSchemaTable(null);
        }
        else if (schemaColumn.getSchemaTable() != tableObjectData.getSchemaTable()) {
            schemaColumn.setSchemaTable(tableObjectData.getSchemaTable());
        }
    }
    
    public boolean isUsed() {
	return expressionObjectDataList != null && expressionObjectDataList.size() > 0;
    }
    
    private void initExpressionObjectDataList() {
        expressionObjectDataList = new ArrayList<ExpressionObjectData>();
        
        for (SchemaExpression schemaExpression : schemaColumn.getSchemaExpressions()) {
            ExpressionObjectData expressionObjectData = new ExpressionObjectData(schemaExpression);
            expressionObjectDataList.add(expressionObjectData);
        }
    }
    
    public SchemaColumn getSchemaColumn() {
	return schemaColumn;
    }
}
