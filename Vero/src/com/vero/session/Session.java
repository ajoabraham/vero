package com.vero.session;

import com.vero.metadata.Attribute;
import com.vero.metadata.Metric;
import com.vero.metadata.JoinDefinition;
import com.vero.admin.DataSource;
import com.vero.admin.DeleteTeradata;
import static com.vero.utility.Utility.*;
import java.util.HashMap;

public class Session {    
    HashMap<String, DataSource> dataSources;
    // ArrayList<> 
    HashMap<String, Attribute> attributes;
    HashMap<String, Metric> metrics;
    HashMap<String, JoinDefinition> joins;
    // ArrayList<JoinDefMera> joindefs;
    
    public Session() {
        dataSources = new HashMap();
        attributes = new HashMap();
        metrics = new HashMap();
        joins = new HashMap();
    }
    
    public void addDataSource(String inType, String inName, String inDescription) {
        if (inType.compareTo("teradata") == 0) {
            DataSource ds = new DeleteTeradata(DataSource.DsType.TERADATA, inName, inDescription);            
            dataSources.put(inName, ds);
        } else {
            System.out.println("DS not supported...");
        }
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
    
    public HashMap getAllDataSources() {
        return dataSources;
    }
    
    public DataSource getDataSource(String inName) {        
        return dataSources.get(inName);        
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
