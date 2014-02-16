/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<TableObjectData, List<ColumnObjectData>> tableToColumnsMap = new HashMap<TableObjectData, List<ColumnObjectData>>();
    private TableObjectData selectedTableObjectData = null;
    
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
        tableToColumnsMap = new HashMap<TableObjectData, List<ColumnObjectData>>();
        
        for (ColumnObjectData columnObjectData : columnObjectDataList) {
            addColumnIntoMap(columnObjectData);
        }
    }

    private void addColumnIntoMap(ColumnObjectData columnObjectData) {
        TableObjectData tableObjectData = columnObjectData.getTableObjectData();
        
        List<ColumnObjectData> tableColumns = tableToColumnsMap.get(tableObjectData);
        if (tableColumns == null) {
        	tableColumns = new ArrayList<ColumnObjectData>();
        	tableToColumnsMap.put(tableObjectData, tableColumns);
        }
        
        tableColumns.add(columnObjectData);	
    }
    
    public void addColumnObjectData(ColumnObjectData columnObjectData) {
        columnObjectDataList.add(columnObjectData);
        addColumnIntoMap(columnObjectData);
    }
    
    public void AddAllColumnObjectData(List<ColumnObjectData> columns) {
	for (ColumnObjectData columnObjectData : columns) {
	    addColumnObjectData(columnObjectData);
	}
    }
    
    public boolean removeColumnObjectData(ColumnObjectData columnObjectData) {
        return columnObjectDataList.remove(columnObjectData);
    }

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

    public TableObjectData getSelectedTableObjectData() {
        if (selectedTableObjectData == null && tableToColumnsMap.size() > 0) {
            selectedTableObjectData = tableToColumnsMap.keySet().iterator().next();
        }
        
        return selectedTableObjectData;
    }

    public void setSelectedTableObjectData(TableObjectData selectedTableObjectData) {
        this.selectedTableObjectData = selectedTableObjectData;
    }
    
    public boolean containsTableObjectData(TableObjectData tableObjectData) {
	return tableToColumnsMap.containsKey(tableObjectData);
    }

    @Override
    public ObjectType getType() {
        return EXPRESSION;
    }
}
