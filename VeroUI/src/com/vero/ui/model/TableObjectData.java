/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import static com.vero.ui.common.ObjectType.TABLE;
import com.vero.ui.common.ObjectType;
import java.util.List;

/**
 *
 * @author Tai Hu
 */
public class TableObjectData extends UIData {
    private String name = null;
    private List<ColumnObjectData> columnObjectDataList = null;
    private List<AttributeObjectData> attributeObjectDataList = null;
    private List<MetricObjectData> metricObjectData = null;
    
    public TableObjectData() {
        
    }

    @Override
    public ObjectType getType() {
        return TABLE;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnObjectData> getColumnObjectDataList() {
        return columnObjectDataList;
    }

    public void setColumnObjectDataList(List<ColumnObjectData> columnObjectDataList) {
        this.columnObjectDataList = columnObjectDataList;
    }

    public List<AttributeObjectData> getAttributeObjectDataList() {
        return attributeObjectDataList;
    }

    public void setAttributeObjectDataList(List<AttributeObjectData> attributeObjectDataList) {
        this.attributeObjectDataList = attributeObjectDataList;
    }

    public List<MetricObjectData> getMetricObjectData() {
        return metricObjectData;
    }

    public void setMetricObjectData(List<MetricObjectData> metricObjectData) {
        this.metricObjectData = metricObjectData;
    }
}
