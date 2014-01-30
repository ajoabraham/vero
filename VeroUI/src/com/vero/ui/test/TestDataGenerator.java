/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.test;

import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.TableObjectData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tai Hu
 */
public final class TestDataGenerator {
    private static long ID_SEQ = 0L;
    public static DatasourceObjectData generateDatasource(String name) {
        DatasourceObjectData datasourceObjectData = new DatasourceObjectData();
        
        datasourceObjectData.setId("" + ++ID_SEQ);
        datasourceObjectData.setName(name);
        
        List<TableObjectData> tableObjectDataList = new ArrayList<>();
        datasourceObjectData.setTableObjectDataList(tableObjectDataList);
        
        for (int i = 0; i < 5; i++) {
            TableObjectData tableObjectData = new TableObjectData();
            tableObjectData.setId("" + ++ID_SEQ);
            tableObjectData.setName("Table-" + i);
            
            List<ColumnObjectData> columnObjectDataList = new ArrayList<>();
            tableObjectData.setColumnObjectDataList(columnObjectDataList);
            
            for (int j = 0; j < 5; j++) {
                ColumnObjectData columnObjectData = new ColumnObjectData();
                columnObjectData.setId("" + ++ID_SEQ);
                columnObjectData.setName("Column-" + j);
                columnObjectDataList.add(columnObjectData);
            }
            
            List<AttributeObjectData> attributeObjectDataList = new ArrayList<>();
//            tableObjectData.setAttributeObjectDataList(attributeObjectDataList);
            for (int j = 0; j < 2; j++) {
                AttributeObjectData attributeObjectData = new AttributeObjectData();
                attributeObjectData.setId("" + ++ID_SEQ);
                attributeObjectData.setName("Attribute-" + j);
                attributeObjectDataList.add(attributeObjectData);             
            }
            
            List<MetricObjectData> metricObjectDataList = new ArrayList<>();
//            tableObjectData.setMetricObjectDataList(metricObjectDataList);
            for (int j = 0; j < 2; j++) {
                MetricObjectData metricObjectData = new MetricObjectData();
                metricObjectData.setId("" + ++ID_SEQ);
                metricObjectData.setName("Metric-" + j);
                metricObjectDataList.add(metricObjectData);
            }
            
            tableObjectDataList.add(tableObjectData);
        }
        
        return datasourceObjectData;
    }
    
    public static List<DatasourceObjectData> generateDatasourceList(String namePrefix) {
        List<DatasourceObjectData> datasourceObjectDataList = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            datasourceObjectDataList.add(generateDatasource(namePrefix + "-" + i));
        }
        
        return datasourceObjectDataList;
    }
}
