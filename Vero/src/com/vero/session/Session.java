package com.vero.session;

import com.vero.metadata.Attribute;
import com.vero.metadata.Metric;
import com.vero.metadata.JoinDefinition;
import com.vero.admin.DataSource;
import com.vero.admin.DeleteTeradata;
import com.vero.metadata.Table;
import static com.vero.utility.Utility.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Session {    
    private HashMap<String, DataSource> dataSources;
    // ArrayList<> 
    private HashMap<String, Table> tables; // FIXME: restrcted to single DS
    private HashMap<String, Attribute> attributes;
    private HashMap<String, Metric> metrics;
    private HashMap<String, JoinDefinition> joins;
    private ArrayList<String> hardhints;
    // ArrayList<JoinDefMera> joindefs;
    
    public Session() {
        dataSources = new HashMap();
        tables = new HashMap();
        attributes = new HashMap();
        metrics = new HashMap();
        joins = new HashMap();
        hardhints = new ArrayList();
    }
    
    public void addDataSource(String inType, String inName, String inDescription) {
        if (inType.compareTo("Teradata") == 0) {
            DataSource ds = new DeleteTeradata(DataSource.DsType.TERADATA, inName, inDescription);            
            dataSources.put(inName, ds);
        } else {
            System.out.println("DS not supported...");
        }
    }

    public void addTable(Table inTable) {
        tables.put(inTable.getPhysicalName(), inTable);
    }
    
    public void addAttributeMeta(Attribute inAttr) {
        attributes.put(inAttr.getName(), inAttr);
    }

    public void addMetricMeta(Metric inMetric) {
        metrics.put(inMetric.getName(), inMetric);
    }
    
    public void addJoinMeta(JoinDefinition inJoin) {
        joins.put(inJoin.getName(), inJoin);
    }

    public void addHardhintMeta(String inHardhint) {
        hardhints.add(inHardhint);
    }
    
    public HashMap getAllDataSources() {
        return dataSources;
    }
    
    public DataSource getDataSource(String inName) {        
        return dataSources.get(inName);        
    }
    
    public Table getTable(String inName) {
        return tables.get(inName);
    }
    
    public HashMap getAttributes() {
        return attributes;        
    }
    
    public HashMap getMetrics() {
        return metrics;
    }
    
    public HashMap getJoins() {
        return joins;
    }
    
    public ArrayList getHardhints() {
        return hardhints;
    }
    
    public void dump() {
        System.out.println("Dumping session...");
        HashMap ds = getAllDataSources();
        printMap(ds);
        
        DataSource specificDS = getDataSource("Teradata - Prod");
        if (specificDS != null) {
            System.out.println("Got a Teradata - Prod - " + specificDS.toString());
        } else {
            System.out.println("Not found...");
        }
        System.out.println("------------------------------");
    }
}
