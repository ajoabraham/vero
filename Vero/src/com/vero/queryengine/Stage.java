/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.Attribute;
import com.vero.metadata.JoinDefinition;
import com.vero.metadata.Metric;
import com.vero.metadata.Table;
import com.vero.session.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author yulinwen
 */
public class Stage {
    private class ReferenceUnit {
        private int rowCount = -1;
        // FIXME: key should be modified to be UUID
        private final HashMap<String, Attribute> attrHT = new HashMap();
        private final HashMap<String, Metric> metricHT = new HashMap();
        private final HashMap<String, JoinDefinition> joindefHT = new HashMap();
        private final List<Table> hardhintsAL = new ArrayList();
                        
        public ReferenceUnit() {}
    }
        
    private final HashMap<String, ReferenceUnit> table2ReferenceUnitHT = new HashMap();
    private final List<Attribute> attributes = new ArrayList();
    private final List<Metric> metrics = new ArrayList();
    private final List<String> hardhints = new ArrayList();
    private final List<JoinDefinition> joindefs = new ArrayList();
    private final HashMap<UUID, ProcessingUnit> processingUnits = new HashMap();
    
    public Stage() {}
    
    public void preprocess(Session inSession) {
        // retrieve all balck hardhints
        ArrayList<String> blackHardhints = new ArrayList(inSession.getBlackHardhints());
        
        // associate table with white hardhints
        hardhints.addAll(inSession.getWhiteHardhints());
        for (int i = 0; i < hardhints.size(); i++) {
            String tableName = hardhints.get(i);
            Table curTable = inSession.getTable(tableName);
            
            ProcessingUnit aPU = new ProcessingUnit();
            aPU.setType(ProcessingUnit.PUType.PUTYPE_HARDHINT);
            aPU.setContent(curTable);
            processingUnits.put(aPU.getUUID(), aPU);
            
            if (!table2ReferenceUnitHT.containsKey(tableName)) {
                ReferenceUnit rU = new ReferenceUnit(); 
                rU.rowCount = inSession.getTable(tableName).getRowCount();
                table2ReferenceUnitHT.put(tableName, rU);
                rU.hardhintsAL.add(curTable);
            } else {
                ReferenceUnit rU = table2ReferenceUnitHT.get(tableName);
                // FIXME: may introduce duplicate
                rU.hardhintsAL.add(curTable);
            }
        }
        
        // associate table with joindefs
        joindefs.addAll(inSession.getJoins());
        for (JoinDefinition curJoindef : joindefs) {
            String[] elements = {curJoindef.getTLeft(), curJoindef.getTRight()};
            
            for (String tableName: elements) {
                if (!table2ReferenceUnitHT.containsKey(tableName)) {
                    ReferenceUnit rU = new ReferenceUnit(); 
                    rU.rowCount = inSession.getTable(tableName).getRowCount();
                    table2ReferenceUnitHT.put(tableName, rU);
                    rU.joindefHT.put(curJoindef.getName(), curJoindef);
                } else {
                    ReferenceUnit rU = table2ReferenceUnitHT.get(tableName);
                    if (!rU.joindefHT.containsKey(curJoindef.getName())) {
                        rU.joindefHT.put(curJoindef.getName(), curJoindef);
                    }
                }
            }
        }
        
        // associate table with attributes
        attributes.addAll(inSession.getAttributes());
        for (Attribute curAttr : attributes) {
            ProcessingUnit aPU = new ProcessingUnit();
            aPU.setType(ProcessingUnit.PUType.PUTYPE_ATTRIBUTE);
            aPU.setContent(curAttr);
            processingUnits.put(aPU.getUUID(), aPU);
            
            // filter out black hardhint tables
            for (String curTable : blackHardhints) {
                curAttr.removeTable(curTable);
            }
            
            List<Table> listTables = curAttr.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    setAttributeByTable(iterTable.next().getPhysicalName(), curAttr);
                }
            }
        }
        
        // associate table with metrics
        metrics.addAll(inSession.getMetrics());
        for (Metric curMet : metrics) {
            ProcessingUnit aPU = new ProcessingUnit();
            aPU.setType(ProcessingUnit.PUType.PUTYPE_METRIC);
            aPU.setContent(curMet);
            processingUnits.put(aPU.getUUID(), aPU); 
            
            // filter out black hardhint tables
            for (String curTable : blackHardhints) {
                curMet.removeTable(curTable);
            }
            
            List<Table> listTables = curMet.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    setMetricByTable(iterTable.next().getPhysicalName(), curMet);
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
                // System.out.println("L table = " + entry2.getValue().getTLeft() + ", R table = " + entry2.getValue().getTRight());
            }
            Map<String, Attribute> dumpAMap = rU.attrHT;
            for (Map.Entry<String, Attribute> entry3 : dumpAMap.entrySet()) {
                System.out.println("dumpA Key = " + entry3.getKey() + ", Value = " + entry3.getValue());
            }
            Map<String, Metric> dumpMMap = rU.metricHT;
            for (Map.Entry<String, Metric> entry4 : dumpMMap.entrySet()) {
                System.out.println("dumpM Key = " + entry4.getKey() + ", Value = " + entry4.getValue());
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
    
    public HashMap<String, Attribute> getAttributeHTByTable(String inTable) {
        if (table2ReferenceUnitHT.containsKey(inTable)) {
            return table2ReferenceUnitHT.get(inTable).attrHT;
        } else {
            return null;
        }
    }
    
    public HashMap<String, Metric> getMetricHTByTable(String inTable) {
        if (table2ReferenceUnitHT.containsKey(inTable)) {
            return table2ReferenceUnitHT.get(inTable).metricHT;
        } else {
            return null;
        }
    }
    
    public HashMap<String, JoinDefinition> getJoindefHTByTable(String inTable) {
        if (table2ReferenceUnitHT.containsKey(inTable)) {
            return table2ReferenceUnitHT.get(inTable).joindefHT;
        } else {
            return null;
        }
    }

    public List<Table> getHardhintALByTable(String inTable) {
        if (table2ReferenceUnitHT.containsKey(inTable)) {
            return table2ReferenceUnitHT.get(inTable).hardhintsAL;
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
    
    public List<Attribute> getAttributes() {
        return attributes;
    }
    
    public List<Metric> getMetrics() {
        return metrics;
    }
    
    public HashMap getPUs() {
        return processingUnits;
    }
}
