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
    private class ReferenceUnit1 {
        private int rowCount = -1;
        private final HashMap<String, Attribute> attrHT = new HashMap();
        private final HashMap<String, Metric> metricHT = new HashMap();
        private final HashMap<String, JoinDefinition> joindefHT = new HashMap();
        private final ArrayList<Table> hardhintsAL = new ArrayList();
                        
        public ReferenceUnit1() {}
    }
        
    private final HashMap<String, ReferenceUnit1> table2ReferenceUnitHT;
    //private HashMap<String, Attribute> attributes;
    //private HashMap<String, Metric> metrics;
    private ArrayList<Attribute> attributes;
    private ArrayList<Metric> metrics;
    private ArrayList<String> hardhints;
    private final HashMap<UUID, ProcessingUnit> processingUnits;
    
    public Stage() {
        table2ReferenceUnitHT = new HashMap();
        attributes = null;
        metrics = null;
        hardhints = null;
        processingUnits = new HashMap();
    }
    
    public void preprocess(Session inSession) {                
        // associate table with hardhint
        hardhints = new ArrayList(inSession.getHardhints());
        List<String> hardhintaAL = hardhints;
        for (int i = 0; i < hardhintaAL.size(); i++) {
            String tableName = hardhintaAL.get(i);
            Table curTable = inSession.getTable(tableName);
            
            ProcessingUnit aPU = new ProcessingUnit();
            aPU.setType(ProcessingUnit.PUType.PUTYPE_HARDHINT);
            aPU.setContent(curTable);
            processingUnits.put(aPU.getUUID(), aPU);
            
            if (!table2ReferenceUnitHT.containsKey(tableName)) {
                ReferenceUnit1 rU = new ReferenceUnit1(); 
                rU.rowCount = inSession.getTable(tableName).getRowCount();
                table2ReferenceUnitHT.put(tableName, rU);
                rU.hardhintsAL.add(curTable);
            } else {
                ReferenceUnit1 rU = table2ReferenceUnitHT.get(tableName);
                // FIXME: may intriduce duplicate
                rU.hardhintsAL.add(curTable);
            }
        }
        
        // associate table with joindefs
        HashMap inJoindef = new HashMap(inSession.getJoins());
        Map<String, JoinDefinition> jdMap = inJoindef;
        for (Map.Entry<String, JoinDefinition> entry : jdMap.entrySet()) {
            System.out.println("JD Key = " + entry.getKey() + ", Value = " + entry.getValue());
            JoinDefinition joinDef = entry.getValue();

            String[] elements = {joinDef.getTLeft(), joinDef.getTRight()};
            
            for (String tableName: elements) {
                if (!table2ReferenceUnitHT.containsKey(tableName)) {
                    ReferenceUnit1 rU = new ReferenceUnit1(); 
                    rU.rowCount = inSession.getTable(tableName).getRowCount();
                    table2ReferenceUnitHT.put(tableName, rU);
                    rU.joindefHT.put(joinDef.getName(), joinDef);
                } else {
                    ReferenceUnit1 rU = table2ReferenceUnitHT.get(tableName);
                    if (!rU.joindefHT.containsKey(joinDef.getName())) {
                        rU.joindefHT.put(joinDef.getName(), joinDef);
                    }
                }
            }
        }
        
        // associate table with attribute
        /*
        attributes = new HashMap(inSession.getAttributes());
        Map<String, Attribute> attrMap = attributes;
        for (Map.Entry<String, Attribute> entry : attrMap.entrySet()) {
            Attribute attr = entry.getValue();
            
            ProcessingUnit aPU = new ProcessingUnit();
            aPU.setType(ProcessingUnit.PUType.PUTYPE_ATTRIBUTE);
            aPU.setContent(attr);
            processingUnits.put(aPU.getUUID(), aPU);
             
            ArrayList<Table> listTables = attr.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    setAttributeByTable(iterTable.next().getPhysicalName(), attr);
                }
            }
        }
        */
        
        attributes = new ArrayList(inSession.getAttributes());
        for (Attribute curAttr : attributes) {
            ProcessingUnit aPU = new ProcessingUnit();
            aPU.setType(ProcessingUnit.PUType.PUTYPE_ATTRIBUTE);
            aPU.setContent(curAttr);
            processingUnits.put(aPU.getUUID(), aPU);
             
            ArrayList<Table> listTables = curAttr.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    setAttributeByTable(iterTable.next().getPhysicalName(), curAttr);
                }
            }
        }
        
        // associate table with metric
        /*
        metrics = new HashMap(inSession.getMetrics());
        Map<String, Metric> metricMap = metrics;
        for (Map.Entry<String, Metric> entry : metricMap.entrySet()) {
            Metric met = entry.getValue();
            ProcessingUnit aPU = new ProcessingUnit();
            aPU.setType(ProcessingUnit.PUType.PUTYPE_METRIC);
            aPU.setContent(met);
            processingUnits.put(aPU.getUUID(), aPU); 
            
            ArrayList<Table> listTables = met.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    setMetricByTable(iterTable.next().getPhysicalName(), met);
                }
            }
        }
        */
        metrics = new ArrayList(inSession.getMetrics());
        for (Metric curMet : metrics) {
            ProcessingUnit aPU = new ProcessingUnit();
            aPU.setType(ProcessingUnit.PUType.PUTYPE_METRIC);
            aPU.setContent(curMet);
            processingUnits.put(aPU.getUUID(), aPU); 
            
            ArrayList<Table> listTables = curMet.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    setMetricByTable(iterTable.next().getPhysicalName(), curMet);
                }
            }
        }
        
        // create pu2ReferenceUnitHT
        
        // dump table2ReferenceUnitHT
        System.out.println("dumping table2ReferenceUnitHT");
        System.out.println("------------------------------");
        Map<String, ReferenceUnit1> dumpTMap = table2ReferenceUnitHT;
        for (Map.Entry<String, ReferenceUnit1> entry1 : dumpTMap.entrySet()) {
            System.out.println("dumpT Key = " + entry1.getKey() + ", Value = " + entry1.getValue());
            ReferenceUnit1 rU = entry1.getValue();
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

    public ArrayList<Table> getHardhintALByTable(String inTable) {
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
    
    /*
    public HashMap getAttributes() {
        return attributes;
    }

    public HashMap getMetrics() {
        return metrics;
    }
    */
    
    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }
    
    public ArrayList<Metric> getMetrics() {
        return metrics;
    }
    
    public HashMap getPUs() {
        return processingUnits;
    }
}
