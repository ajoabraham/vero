package com.vero.session;

import com.vero.metadata.Attribute;
import com.vero.metadata.Metric;
import com.vero.metadata.JoinDefinition;
import com.vero.admin.DataSource;
import com.vero.admin.DeleteTeradata;
import static com.vero.queryengine.Vero.printMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Session {
    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            // it.remove(); // avoids a ConcurrentModificationException
        }
    }
    
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
