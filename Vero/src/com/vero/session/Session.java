package com.vero.session;

import com.vero.metadata.Attribute;
import com.vero.metadata.Metric;
import com.vero.metadata.JoinDefinition;
import com.vero.admin.DataSource;
import com.vero.admin.DeleteTeradata;
import com.vero.metadata.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Session {
    private final HashMap<String, DataSource> dataSources = new HashMap();
    private final HashMap<String, Table> tables = new HashMap(); // FIXME: restricted to single DS
    private final List<Attribute> attributes = new ArrayList();
    private final List<Metric> metrics = new ArrayList();
    private final List<JoinDefinition> joins = new ArrayList();
    private final List<String> whiteHardhints = new ArrayList();
    private final List<String> blackHardhints = new ArrayList();
    
    public Session() {} 
    
    public void addDataSource(UUID uuid, String inType, String inName, String inDescription) {
        if (inType.compareTo("Teradata") == 0) {
            DataSource ds = new DeleteTeradata(uuid, DataSource.DsType.TERADATA, inName, inDescription);            
            dataSources.put(inName, ds);
        } else {
            System.out.println("DS not supported...");
        }
    }

    public void addTable(Table inTable) {
        tables.put(inTable.getPhysicalName(), inTable);
    }
    
    public void addAttribute(Attribute inAttr) {
        attributes.add(inAttr);
    }

    public void addMetric(Metric inMetric) {
        metrics.add(inMetric);
    }
    
    public void addJoindef(JoinDefinition inJoin) {
        joins.add(inJoin);
    }

    public void addWhiteHardhint(String inHardhint) {
        whiteHardhints.add(inHardhint);
    }

    public void addBlackHardhint(String inHardhint) {
        blackHardhints.add(inHardhint);
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
        
    public List<Attribute> getAttributes() {
        return attributes;
    }
    
    public List<Metric> getMetrics() {
        return metrics;
    }
    
    public List<JoinDefinition> getJoins() {
        return joins;
    }
    
    public List getWhiteHardhints() {
        return whiteHardhints;
    }

    public List getBlackHardhints() {
        return blackHardhints;
    }
}
