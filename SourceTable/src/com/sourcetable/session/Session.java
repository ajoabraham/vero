package com.sourcetable.session;

import com.sourcetable.datasource.*;
import com.sourcetable.metadata.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Session {
    HashMap<String, DataSource> dataSources;
    // ArrayList<> 
    HashMap<String, AttributeMeta> attributes;
    HashMap<String, MetricMeta> metrics;
    HashMap<String, JoinMeta> joins;
    // ArrayList<JoinDefMera> joindefs;
    
    public Session() {
        dataSources = new HashMap();
        attributes = new HashMap();
        metrics = new HashMap();
        joins = new HashMap();
    }
    
    public void addDataSource(String inType, String inName, String inDescription) {
        if (inType.compareTo("teradata") == 0) {
            DataSource ds = new Teradata(DsType.TERADATA, inName, inDescription);            
            dataSources.put(inName, ds);
        } else {
            System.out.println("DS not supported...");
        }
    }

    public void addAttributeMeta(AttributeMeta inAttr) {
        attributes.put(inAttr.getName(), inAttr);
    }

    public void addMetricMeta(MetricMeta inMetric) {
        metrics.put(inMetric.getName(), inMetric);
    }
    
    public void addJoinMeta(JoinMeta inJoin) {
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
}
