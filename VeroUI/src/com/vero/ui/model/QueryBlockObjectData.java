/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.QUERY_BLOCK;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class QueryBlockObjectData extends BlockObjectData {
    private IntegerProperty position = new SimpleIntegerProperty();
    
    private DatasourceObjectData datasourceObjectData = null;
    private List<AttributeObjectData> attributeObjectDataList = new ArrayList<AttributeObjectData>();
    private List<MetricObjectData> metricObjectDataList = new ArrayList<MetricObjectData>();
    private List<TableObjectData> tableObjectDataList = new ArrayList<TableObjectData>();
    private List<TableJoinObjectData> tableJoinObjectDataList = new ArrayList<TableJoinObjectData>();
    
    public QueryBlockObjectData() {
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

    public List<AttributeObjectData> getAttributeObjectDataList() {
        return attributeObjectDataList;
    }

    public void setAttributeObjectDataList(List<AttributeObjectData> attributeObjectDataList) {
        this.attributeObjectDataList = attributeObjectDataList;
    }

    public void addAttributeObjectData(AttributeObjectData attributeObjectData) {
        attributeObjectDataList.add(attributeObjectData);
    }
    
    public boolean removeAttributeObjectData(AttributeObjectData attributeObjectData) {
        return attributeObjectDataList.remove(attributeObjectData);
    }
    
    public List<MetricObjectData> getMetricObjectDataList() {
        return metricObjectDataList;
    }

    public void setMetricObjectDataList(List<MetricObjectData> metricObjectDataList) {
        this.metricObjectDataList = metricObjectDataList;
    }

    public void addMetricObjectData(MetricObjectData metricObjectData) {
        metricObjectDataList.add(metricObjectData);
    }
    
    public boolean removeMetricObjectData(MetricObjectData metricObjectData) {
        return metricObjectDataList.remove(metricObjectData);
    }
    
    public List<TableObjectData> getTableObjectDataList() {
        return tableObjectDataList;
    }

    public void setTableObjectDataList(List<TableObjectData> tableObjectDataList) {
        this.tableObjectDataList = tableObjectDataList;
    }
    
    public boolean containsTableObjectData(TableObjectData tableObjectData) {
	return tableObjectDataList.contains(tableObjectData);
    }
    
    public void addTableObjectData(TableObjectData tableObjectData) {
        tableObjectDataList.add(tableObjectData);
    }

    public boolean removeTableObjectData(TableObjectData tableObjectData) {
        return tableObjectDataList.remove(tableObjectData);
    }
    
    public List<TableJoinObjectData> getTableJoinObjectDataList() {
        return tableJoinObjectDataList;
    }

    public void setTableJoinObjectDataList(List<TableJoinObjectData> tableJoinObjectDataList) {
        this.tableJoinObjectDataList = tableJoinObjectDataList;
    }
    
    public void addTableJoinObjectData(TableJoinObjectData tableJoinObjectData) {
        tableJoinObjectDataList.add(tableJoinObjectData);
    }
    
    public boolean removeTableJoinObjectData(TableJoinObjectData tableJoinObjectData) {
        return tableJoinObjectDataList.remove(tableJoinObjectData);
    }

    @Override
    public ObjectType getType() {
	return QUERY_BLOCK;
    }
}
