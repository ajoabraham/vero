package com.sourcetable.session;

import com.sourcetable.datasource.*;
import com.sourcetable.metadata.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Session {
    HashMap<String, DataSource> dataSources;
    // ArrayList<> 
    ArrayList<AttributeMeta> attributes;
    ArrayList<MetricMeta> metrics;
    // ArrayList<JoinDefMera> joindefs;
    
    public Session() {
        dataSources = new HashMap();
        attributes = new ArrayList();
        metrics = new ArrayList();
    }
    
    public void addDataSource(String inType, String inName, String inDescription) {
        if (inType.compareTo("teradata") == 0) {
            DataSource ds = new Teradata(DsType.TERADATA, inName, inDescription);            
            dataSources.put(inName, ds);
        } else {
            System.out.println("DS not supported...");
        }
    }
    
    public HashMap getAllDataSources() {
        return dataSources;
    }
    
    public DataSource getDataSource(String inName) {        
        return dataSources.get(inName);        
    }
    
    public ArrayList<AttributeMeta> getAttributes() {
        return attributes;        
    }
    
    public ArrayList<MetricMeta> getMetrics() {
        return metrics;
    }
}
