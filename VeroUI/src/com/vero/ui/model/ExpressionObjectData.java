/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vero.model.entities.SchemaColumn;
import com.vero.model.entities.SchemaExpression;
import com.vero.ui.constants.ObjectType;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * @author Tai Hu
 * 
 */
public class ExpressionObjectData extends UIData {

    private SchemaExpression schemaExpression = null;

    private StringProperty formula = new SimpleStringProperty();
    private List<ColumnObjectData> columnObjectDataList = null;
    private AttributeObjectData attributeObjectData = null;
    private MetricObjectData metricObjectData = null;
    private Map<TableObjectData, List<ColumnObjectData>> tableToColumnsMap = new HashMap<TableObjectData, List<ColumnObjectData>>();
    private TableObjectData selectedTableObjectData = null;

    public ExpressionObjectData() {
	this(new SchemaExpression());
    }

    public ExpressionObjectData(SchemaExpression schemaExpression) {
	super(schemaExpression);
	this.schemaExpression = schemaExpression;

	// init data
	formula.set(schemaExpression.getExpression());
	formula.addListener(new ChangeListener<String>() {

	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		ExpressionObjectData.this.schemaExpression.setExpression(newValue);
	    }

	});
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
	if (columnObjectDataList == null)
	    initColumnObjectDataList();
	return columnObjectDataList;
    }

    // public void setColumnObjectDataList(List<ColumnObjectData>
    // columnObjectDataList) {
    // this.columnObjectDataList = columnObjectDataList;
    // tableToColumnsMap = new HashMap<TableObjectData,
    // List<ColumnObjectData>>();
    //
    // for (ColumnObjectData columnObjectData : columnObjectDataList) {
    // addColumnIntoMap(columnObjectData);
    // }
    // }

    private void addColumnIntoMap(ColumnObjectData columnObjectData) {
	TableObjectData tableObjectData = columnObjectData.getTableObjectData();
	if (tableObjectData == null) {
	    tableObjectData = new TableObjectData(columnObjectData.getSchemaColumn().getSchemaTable());
	    columnObjectData.setTableObjectData(tableObjectData);
	}

	List<ColumnObjectData> tableColumns = tableToColumnsMap.get(tableObjectData);
	if (tableColumns == null) {
	    tableColumns = new ArrayList<ColumnObjectData>();
	    tableToColumnsMap.put(tableObjectData, tableColumns);
	}

	tableColumns.add(columnObjectData);
    }

    public void addColumnObjectData(ColumnObjectData columnObjectData) {
	if (columnObjectDataList == null)
	    initColumnObjectDataList();
	columnObjectDataList.add(columnObjectData);
	addColumnIntoMap(columnObjectData);
    }

    public void addAllColumnObjectData(List<ColumnObjectData> columns) {
	for (ColumnObjectData columnObjectData : columns) {
	    addColumnObjectData(columnObjectData);
	}
    }

    public boolean removeColumnObjectData(ColumnObjectData columnObjectData) {
	if (columnObjectDataList == null)
	    initColumnObjectDataList();
	return columnObjectDataList.remove(columnObjectData);
    }

    public AttributeObjectData getAttributeObjectData() {
	return attributeObjectData;
    }

    public void setAttributeObjectData(AttributeObjectData attributeObjectData) {
	this.attributeObjectData = attributeObjectData;

	if (attributeObjectData == null) {
	    schemaExpression.setSchemaAttribute(null);
	}
	else if (schemaExpression.getSchemaAttribute() != attributeObjectData.getSchemaAttribute()) {
	    schemaExpression.setSchemaAttribute(attributeObjectData.getSchemaAttribute());
	}
    }

    public MetricObjectData getMetricObjectData() {
	return metricObjectData;
    }

    public void setMetricObjectData(MetricObjectData metricObjectData) {
	this.metricObjectData = metricObjectData;

	if (metricObjectData == null) {
	    schemaExpression.setSchemaMetric(null);
	}
	else if (schemaExpression.getSchemaMetric() != metricObjectData.getSchemaMetric()) {
	    schemaExpression.setSchemaMetric(metricObjectData.getSchemaMetric());
	}
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

    private void initColumnObjectDataList() {
	columnObjectDataList = new ArrayList<ColumnObjectData>();
	tableToColumnsMap = new HashMap<TableObjectData, List<ColumnObjectData>>();

	if (schemaExpression.getSchemaColumns() != null) {
	    for (SchemaColumn schemaColumn : schemaExpression.getSchemaColumns()) {
		ColumnObjectData columnObjectData = new ColumnObjectData(schemaColumn);
		columnObjectDataList.add(columnObjectData);
		addColumnIntoMap(columnObjectData);
	    }
	}
    }
}
