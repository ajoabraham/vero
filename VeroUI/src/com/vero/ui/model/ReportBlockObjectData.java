/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.*;

import java.util.ArrayList;
import java.util.List;

import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class ReportBlockObjectData extends BlockObjectData {
    private List<AttributeObjectData> attributeObjectDataList = new ArrayList<AttributeObjectData>();
    private List<MetricObjectData> metricObjectDataList = new ArrayList<MetricObjectData>();
    private List<TableObjectData> tableObjectDataList = new ArrayList<TableObjectData>();
    private List<TableJoinObjectData> tableJoinObjectDataList = new ArrayList<TableJoinObjectData>();
    
    public ReportBlockObjectData() {
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
	return REPORT_BLOCK;
    }

}
