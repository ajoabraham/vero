/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.vero.ui.constants.DBKeyType;
import com.vero.ui.constants.ObjectType;

import static com.vero.ui.constants.ObjectType.COLUMN;

/**
 *
 * @author Tai Hu
 */
public class ColumnObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private StringProperty name = new SimpleStringProperty();
    private StringProperty dataType = new SimpleStringProperty();
    private IntegerProperty dataTypeSize = new SimpleIntegerProperty();
    private DBKeyType keyType = DBKeyType.NO_KEY_TYPE;
    private List<ExpressionObjectData> expressionObjectDataList = new ArrayList<ExpressionObjectData>();
    private TableObjectData tableObjectData = null;
    
    public ColumnObjectData() {        
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

    public int getDataTypeSize() {
        return dataTypeSize.get();
    }

    public void setDataTypeSize(int dataTypeSize) {
        this.dataTypeSize.set(dataTypeSize);
    }
    
    public IntegerProperty dataTypeSizeProperty() {
	return dataTypeSize;
    }

    public DBKeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(DBKeyType keyType) {
        this.keyType = keyType;
    }

    public List<ExpressionObjectData> getExpressionObjectDataList() {
        return expressionObjectDataList;
    }

    public void setExpressionObjectDataList(List<ExpressionObjectData> expressionObjectDataList) {
        this.expressionObjectDataList = expressionObjectDataList;
    }
    
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
    }
    
    public boolean isUsed() {
	return expressionObjectDataList != null && expressionObjectDataList.size() > 0;
    }
}
