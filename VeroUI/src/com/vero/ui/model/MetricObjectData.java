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

import com.vero.ui.constants.ObjectType;

import static com.vero.ui.constants.ObjectType.METRIC;

/**
 *
 * @author Tai Hu
 */
public class MetricObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private StringProperty name = new SimpleStringProperty();
    private List<ExpressionObjectData> expressionObjectDataList = new ArrayList<ExpressionObjectData>();
    
    public MetricObjectData() {
        
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
        return expressionObjectDataList;
    }

    public void setExpressionObjectDataList(List<ExpressionObjectData> expressionObjectDataList) {
        this.expressionObjectDataList = expressionObjectDataList;
    }
    
    public void addExpressionObjectData(ExpressionObjectData expressionObjectData) {
        expressionObjectDataList.add(expressionObjectData);
        expressionObjectData.setMetricObjectData(this);
    }
    
    public boolean removeExpressionObjectData(ExpressionObjectData expressionObjectData) {
        expressionObjectData.setMetricObjectData(null);
        return expressionObjectDataList.remove(expressionObjectData);
    }
}
