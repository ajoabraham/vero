package com.sourcetable.session;

import com.sourcetable.datasource.*;
import com.sourcetable.metadata.*;
import java.util.ArrayList;

public class Session {
    ArrayList<DataSource> dataSources;
    // ArrayList<> 
    ArrayList<AttributeMeta> attributes;
    ArrayList<MetricMeta> metrics;
    // ArrayList<JoinDefMera> joindefs;
    
    public Session() {
        dataSources = new ArrayList();
        attributes = new ArrayList();
        metrics = new ArrayList();
    }
    
    public ArrayList<DataSource> getDataSources() {
        return dataSources;
    }
    
    public ArrayList<AttributeMeta> getAttributes() {
        return attributes;        
    }
    
    public ArrayList<MetricMeta> getMetrics() {
        return metrics;
    }
}
