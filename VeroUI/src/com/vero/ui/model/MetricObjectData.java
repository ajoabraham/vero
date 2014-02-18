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

import com.vero.model.entities.SchemaExpression;
import com.vero.model.entities.SchemaMetric;
import com.vero.ui.constants.ObjectType;

import static com.vero.ui.constants.ObjectType.METRIC;

/**
 *
 * @author Tai Hu
 */
public class MetricObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private SchemaMetric schemaMetric = null;
    private StringProperty name = new SimpleStringProperty();
    private List<ExpressionObjectData> expressionObjectDataList = new ArrayList<ExpressionObjectData>();
    private ExpressionObjectData selectedExpressionObjectData = null;
//    private List<TableObjectData> tableObjectDataList = new ArrayList<TableObjectData>();
//    private List<TableJoinObjectData> tableJoinObjectDataList = new ArrayList<TableJoinObjectData>();
    
    public MetricObjectData() {
        this(new SchemaMetric());
    }
    
    public MetricObjectData(SchemaMetric schemaMetric) {
	super(schemaMetric);
	this.schemaMetric = schemaMetric;
	
	// init data
	name.set(schemaMetric.getName());
	name.addListener(new ChangeListener<String>() {

	    @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		MetricObjectData.this.schemaMetric.setName(newValue);
            }
	    
	});
    }
    
    @Override
    public ObjectType getType() {
        return METRIC;
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

    public List<ExpressionObjectData> getExpressionObjectDataList() {
	if (expressionObjectDataList == null) initExpressionObjectDataList();
        return expressionObjectDataList;
    }

//    public void setExpressionObjectDataList(List<ExpressionObjectData> expressionObjectDataList) {
//        this.expressionObjectDataList = expressionObjectDataList;
//    }
    
    public void addExpressionObjectData(ExpressionObjectData expressionObjectData) {
	if (expressionObjectDataList == null) initExpressionObjectDataList();
        expressionObjectDataList.add(expressionObjectData);
        expressionObjectData.setMetricObjectData(this);
        schemaMetric.addSchemaExpression(expressionObjectData.getSchemaExpression());
    }
    
    public boolean removeExpressionObjectData(ExpressionObjectData expressionObjectData) {
	if (expressionObjectDataList == null) initExpressionObjectDataList();
        expressionObjectData.setMetricObjectData(null);
        schemaMetric.removeSchemaExpression(expressionObjectData.getSchemaExpression());
        return expressionObjectDataList.remove(expressionObjectData);
    }

    public ExpressionObjectData getSelectedExpressionObjectData() {
        return selectedExpressionObjectData;
    }

    public void setSelectedExpressionObjectData(ExpressionObjectData selectedExpressionObjectData) {
        this.selectedExpressionObjectData = selectedExpressionObjectData;
    }
    
    public SchemaMetric getSchemaMetric() {
	return schemaMetric;
    }
    
    private void initExpressionObjectDataList() {
	expressionObjectDataList = new ArrayList<ExpressionObjectData>();
	
	if (schemaMetric.getSchemaExpressions() == null) {
	    schemaMetric.setSchemaExpressions(new ArrayList<SchemaExpression>());
	}
	
	for (SchemaExpression schemaExpression : schemaMetric.getSchemaExpressions()) {
	    ExpressionObjectData expressionObjectData = new ExpressionObjectData(schemaExpression);
	    expressionObjectData.setMetricObjectData(this);
	    expressionObjectDataList.add(expressionObjectData);
	}
    }
    
//    public List<TableObjectData> getTableObjectDataList() {
//        return tableObjectDataList;
//    }
//
//    public void setTableObjectDataList(List<TableObjectData> tableObjectDataList) {
//        this.tableObjectDataList = tableObjectDataList;
//    }
//    
//    public boolean contains(TableObjectData tableObjectData) {
//	return tableObjectDataList.contains(tableObjectData);
//    }
//    
//    public void addTableObjectData(TableObjectData tableObjectData) {
//	tableObjectDataList.add(tableObjectData);
//    }
//    
//    public boolean removeTableObjectData(TableObjectData tableObjectData) {
//	return tableObjectDataList.remove(tableObjectData);
//    }
//
//    public List<TableJoinObjectData> getTableJoinObjectDataList() {
//        return tableJoinObjectDataList;
//    }
//
//    public void setTableJoinObjectDataList(List<TableJoinObjectData> tableJoinObjectDataList) {
//        this.tableJoinObjectDataList = tableJoinObjectDataList;
//    }
//    
//    public void addTableJoinObjectData(TableJoinObjectData tableJoinObjectData) {
//	tableJoinObjectDataList.add(tableJoinObjectData);
//    }
//    
//    public boolean removeTableJoinObjectData(TableJoinObjectData tableJoinObjectData) {
//	return tableJoinObjectDataList.remove(tableJoinObjectData);
//    }
}
