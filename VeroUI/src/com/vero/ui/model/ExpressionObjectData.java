/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.*;

import java.util.ArrayList;
import java.util.List;

import com.vero.ui.constants.ObjectType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Tai Hu
 *
 */
public class ExpressionObjectData extends UIData {
    
    private StringProperty formula = new SimpleStringProperty();
    private List<ColumnObjectData> columnObjectDataList = new ArrayList<ColumnObjectData>();
    private AttributeObjectData attributeObjectData = null;
    private MetricObjectData metricObjectData = null;
    
    public ExpressionObjectData() {

    }

    public String getFormula() {
        return formula.get();
    }


    public void setFormula(String formula) {
        this.formula.set(formula);
    }

    public StringProperty formula() {
        return formula;
    }
    
    public List<ColumnObjectData> getColumnObjectDataList() {
        return columnObjectDataList;
    }


    public void setColumnObjectDataList(List<ColumnObjectData> columnObjectDataList) {
        this.columnObjectDataList = columnObjectDataList;
    }

//    public void addColumnObjectData(ColumnObjectData columnObjectData) {
//        columnObjectDataList.add(columnObjectData);
//    }
//    
//    public boolean removeColumnObjectData(ColumnObjectData columnObjectData) {
//        return columnObjectDataList.remove(columnObjectData);
//    }

    public AttributeObjectData getAttributeObjectData() {
        return attributeObjectData;
    }


    public void setAttributeObjectData(AttributeObjectData attributeObjectData) {
        this.attributeObjectData = attributeObjectData;
    }


    public MetricObjectData getMetricObjectData() {
        return metricObjectData;
    }


    public void setMetricObjectData(MetricObjectData metricObjectData) {
        this.metricObjectData = metricObjectData;
    }


    @Override
    public ObjectType getType() {
        return EXPRESSION;
    }
}
