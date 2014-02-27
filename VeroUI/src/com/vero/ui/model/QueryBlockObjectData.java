/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.QUERY_BLOCK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.vero.model.entities.SchemaData;
import com.vero.model.entities.SchemaQueryBlock;
import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 * 
 */
public class QueryBlockObjectData extends BlockObjectData {
    private IntegerProperty position = new SimpleIntegerProperty();

    private DatasourceObjectData datasourceObjectData = null;
    private ObservableList<AttributeObjectData> attributeObjectDataList = FXCollections.observableArrayList();
    private ObservableList<MetricObjectData> metricObjectDataList = FXCollections.observableArrayList();
    private ObservableList<TableObjectData> tableObjectDataList = FXCollections.observableArrayList();
    private ObservableList<TableJoinObjectData> tableJoinObjectDataList = FXCollections.observableArrayList();
    private Map<String, List<String>> tableToAttributeMetricMap = new HashMap<String, List<String>>();

    public QueryBlockObjectData() {
	this(new SchemaQueryBlock());
    }

    public QueryBlockObjectData(SchemaData schemaData) {
	super(schemaData);

	// init data
	if (schemaData instanceof SchemaQueryBlock) {
	    position.set(((SchemaQueryBlock) schemaData).getPosition());
	    position.addListener(new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		    ((SchemaQueryBlock) QueryBlockObjectData.this.schemaData).setPosition(newValue.intValue());
		}

	    });
	}
    }

    public void setPosition(int position) {
	this.position.set(position);
    }

    public int getPosition() {
	return this.position.get();
    }

    public IntegerProperty position() {
	return position;
    }

    public DatasourceObjectData getDatasourceObjectData() {
	return datasourceObjectData;
    }

    public void setDatasourceObjectData(DatasourceObjectData datasourceObjectData) {
	this.datasourceObjectData = datasourceObjectData;
    }

    public ObservableList<AttributeObjectData> getAttributeObjectDataList() {
	return attributeObjectDataList;
    }

//    public void setAttributeObjectDataList(List<AttributeObjectData> attributeObjectDataList) {
//	this.attributeObjectDataList = attributeObjectDataList;
//    }

    public void addAttributeObjectData(AttributeObjectData attributeObjectData) {
	attributeObjectDataList.add(attributeObjectData);
    }

    public boolean removeAttributeObjectData(AttributeObjectData attributeObjectData) {
	return attributeObjectDataList.remove(attributeObjectData);
    }

    public ObservableList<MetricObjectData> getMetricObjectDataList() {
	return metricObjectDataList;
    }

//    public void setMetricObjectDataList(List<MetricObjectData> metricObjectDataList) {
//	this.metricObjectDataList = metricObjectDataList;
//    }

    public void addMetricObjectData(MetricObjectData metricObjectData) {
	metricObjectDataList.add(metricObjectData);
    }

    public boolean removeMetricObjectData(MetricObjectData metricObjectData) {
	return metricObjectDataList.remove(metricObjectData);
    }

    public ObservableList<TableObjectData> getTableObjectDataList() {
	return tableObjectDataList;
    }

//    public void setTableObjectDataList(List<TableObjectData> tableObjectDataList) {
//	this.tableObjectDataList = tableObjectDataList;
//    }

//    private boolean containsTableObjectData(TableObjectData tableObjectData) {
//	return tableObjectDataList.contains(tableObjectData);
//    }

    public boolean addTableObjectData(MetricObjectData metricObjectData, TableObjectData tableObjectData) {
	boolean isAdded = false;
	if (!tableObjectDataList.contains(tableObjectData)) {
	    tableObjectDataList.add(tableObjectData);
	    isAdded = true;
	}

	List<String> ids = tableToAttributeMetricMap.get(tableObjectData.getId());
	if (ids == null) {
	    ids = new ArrayList<String>();
	    tableToAttributeMetricMap.put(tableObjectData.getId(), ids);
	}
	
	if (!ids.contains(metricObjectData.getId())) {
	    ids.add(metricObjectData.getId());
	}
	
	return isAdded;
    }
    
    public boolean addTableObjectData(AttributeObjectData attributeObjectData, TableObjectData tableObjectData) {
	boolean isAdded = false;
	if (!tableObjectDataList.contains(tableObjectData)) {
	    tableObjectDataList.add(tableObjectData);
	    isAdded = true;
	}

	List<String> ids = tableToAttributeMetricMap.get(tableObjectData.getId());
	if (ids == null) {
	    ids = new ArrayList<String>();
	    tableToAttributeMetricMap.put(tableObjectData.getId(), ids);
	}
	
	if (!ids.contains(attributeObjectData.getId())) {
	    ids.add(attributeObjectData.getId());
	}
	
	return isAdded;
    }

    public boolean removeTableObjectData(AttributeObjectData attributeObjectData, TableObjectData tableObjectData) {
	// Remove the link first
	List<String> ids = tableToAttributeMetricMap.get(tableObjectData.getId());
	if (ids != null) ids.remove(attributeObjectData.getId());
	
	if (ids == null || ids.isEmpty()) {
	    tableToAttributeMetricMap.remove(tableObjectData.getId());
	    return tableObjectDataList.remove(tableObjectData);
	}
	
	return false;
    }
    
    public boolean removeTableObjectData(MetricObjectData metricObjectData, TableObjectData tableObjectData) {
	// Remove the link first
	List<String> ids = tableToAttributeMetricMap.get(tableObjectData.getId());
	if (ids != null) ids.remove(metricObjectData.getId());
	
	if (ids == null || ids.isEmpty()) {
	    tableToAttributeMetricMap.remove(tableObjectData.getId());
	    return tableObjectDataList.remove(tableObjectData);
	}
	
	return false;	
    }

    public ObservableList<TableJoinObjectData> getTableJoinObjectDataList() {
	return tableJoinObjectDataList;
    }

//    public void setTableJoinObjectDataList(List<TableJoinObjectData> tableJoinObjectDataList) {
//	this.tableJoinObjectDataList = tableJoinObjectDataList;
//    }

    public void addTableJoinObjectData(TableJoinObjectData tableJoinObjectData) {
	tableJoinObjectDataList.add(tableJoinObjectData);
    }

    public boolean removeTableJoinObjectData(TableJoinObjectData tableJoinObjectData) {
	return tableJoinObjectDataList.remove(tableJoinObjectData);
    }
    
    public AttributeObjectData getAttributeObjectDataById(String id) {
        for (AttributeObjectData attributeObjectData : attributeObjectDataList) {
            if (id.equalsIgnoreCase(attributeObjectData.getId())) {
                return attributeObjectData;
            }
        }
        
        return null;
    }
    
    public MetricObjectData getMetricObjectDataById(String id) {
        for (MetricObjectData metricObjectData : metricObjectDataList) {
            if (id.equalsIgnoreCase(metricObjectData.getId())) {
                return metricObjectData;
            }
        }
        
        return null;
    }
    
    public TableObjectData getTableObjectDataById(String id) {
	for (TableObjectData tableObjectData : tableObjectDataList) {
	    if (id.equalsIgnoreCase(tableObjectData.getId())) {
		return tableObjectData;
	    }
	}
	
	return null;
    }

    @Override
    public ObjectType getType() {
	return QUERY_BLOCK;
    }
}
