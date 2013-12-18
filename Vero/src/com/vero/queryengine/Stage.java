/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.Attribute;
import com.vero.metadata.Expression;
import com.vero.metadata.JoinDefinition;
import com.vero.metadata.Metric;
import com.vero.metadata.Table;
import com.vero.session.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author yulinwen
 */
public class Stage {
    private class ReferenceUnit {
        private int rowCount;
        private HashMap<String, Attribute> attrHT;
        private HashMap<String, Metric> metricHT;
        private HashMap<String, JoinDefinition> joindefHT;
                        
        public ReferenceUnit() {
            rowCount = -1;
            attrHT = new HashMap();
            metricHT = new HashMap();
            joindefHT = new HashMap();
        }
    }
    
    private HashMap<String, ReferenceUnit> table2ReferenceUnitHT;
    
    public Stage() {
        table2ReferenceUnitHT = new HashMap();
    }
    
    public void preprocess(Session inSession) {        
        // associate table with joindefs
        HashMap inJoindef = new HashMap(inSession.getJoins());
        Map<String, JoinDefinition> jdMap = inJoindef;
        for (Map.Entry<String, JoinDefinition> entry : jdMap.entrySet()) {
            System.out.println("JD Key = " + entry.getKey() + ", Value = " + entry.getValue());
            JoinDefinition joinDef = entry.getValue();

            String[] elements = {joinDef.getTLeft(), joinDef.getTRight()};
            
            for (String tableName: elements) {
                if (!table2ReferenceUnitHT.containsKey(tableName)) {
                    ReferenceUnit rU = new ReferenceUnit(); 
                    rU.rowCount = inSession.getTable(tableName).getRowCount();
                    table2ReferenceUnitHT.put(tableName, rU);
                    rU.joindefHT.put(joinDef.getName(), joinDef);
                } else {
                    ReferenceUnit rU = table2ReferenceUnitHT.get(tableName);
                    if (!rU.joindefHT.containsKey(joinDef.getName())) {
                        rU.joindefHT.put(joinDef.getName(), joinDef);
                    }
                }
            }
        }
        
        // associate table with attribute
        HashMap inAttr = new HashMap(inSession.getAttributes());
        Map<String, Attribute> attrMap = inAttr;
        for (Map.Entry<String, Attribute> entry : attrMap.entrySet()) {
            Attribute attr = entry.getValue();
             
            ArrayList<Table> listTables = attr.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    setAttributeByTable(iterTable.next().getPhysicalName(), attr);
                }
            }
        }
        
        // associate table with metric
        HashMap inMetric = new HashMap(inSession.getMetrics());
        Map<String, Metric> metricMap = inMetric;
        for (Map.Entry<String, Metric> entry : metricMap.entrySet()) {
            Metric met = entry.getValue();
            
            ArrayList<Table> listTables = met.retrieveTables();            
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    setMetricByTable(iterTable.next().getPhysicalName(), met);
                }
            }            
        }
        
        // dump table2ReferenceUnitHT
        System.out.println("dumping table2ReferenceUnitHT");
        System.out.println("------------------------------");
        Map<String, ReferenceUnit> dumpTMap = table2ReferenceUnitHT;
        for (Map.Entry<String, ReferenceUnit> entry1 : dumpTMap.entrySet()) {
            System.out.println("dumpT Key = " + entry1.getKey() + ", Value = " + entry1.getValue());
            ReferenceUnit rU = entry1.getValue();
            System.out.println("rowCount = " + rU.rowCount);
            Map<String, JoinDefinition> dumpJMap = rU.joindefHT;
            for (Map.Entry<String, JoinDefinition> entry2 : dumpJMap.entrySet()) {
                System.out.println("dumpJ Key = " + entry2.getKey() + ", Value = " + entry2.getValue());
            }
        }
    }
    
    private void setAttributeByTable(String inTable, Attribute inAttr) {
        if (table2ReferenceUnitHT.containsKey(inTable)) {
            table2ReferenceUnitHT.get(inTable).attrHT.put(inAttr.getName(), inAttr);
        }
    }
    
    private void setMetricByTable(String inTable, Metric inMetric) {        
        if (table2ReferenceUnitHT.containsKey(inTable)) {
            table2ReferenceUnitHT.get(inTable).metricHT.put(inMetric.getName(), inMetric);
        }
    }
    
    public HashMap<String, JoinDefinition> getJoindefHTByTable(String inTable) {
        if (table2ReferenceUnitHT.containsKey(inTable)) {
            return table2ReferenceUnitHT.get(inTable).joindefHT;
        } else {
            return null;
        }
    }
    
    public int getRowCountByTable(String inTable) {
        if (table2ReferenceUnitHT.containsKey(inTable)) {
            return table2ReferenceUnitHT.get(inTable).rowCount;
        } else {
            return -1;
        }
    }
}
