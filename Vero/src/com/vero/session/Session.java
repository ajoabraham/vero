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
    public static int sessionObjID = 0;
    private final HashMap<String, DataSource> dataSources = new HashMap();
    private final HashMap<String, Table> tables = new HashMap(); // FIXME: restricted to single DS
    private final ArrayList<Attribute> attributes = new ArrayList();
    private final ArrayList<Metric> metrics = new ArrayList();
    private final ArrayList<JoinDefinition> joins = new ArrayList();
    private final ArrayList<String> whiteHardhints = new ArrayList();
    private final ArrayList<String> blackHardhints = new ArrayList();
    
    public Session() {}
    
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
        attributes.add(inAttr);
    }

    public void addMetricMeta(Metric inMetric) {
        metrics.add(inMetric);
    }
    
    public void addJoinMeta(JoinDefinition inJoin) {
        joins.add(inJoin);
    }

    public void addWhiteHardhintMeta(String inHardhint) {
        whiteHardhints.add(inHardhint);
    }

    public void addBlackHardhintMeta(String inHardhint) {
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
    
    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }
    
    public ArrayList<Metric> getMetrics() {
        return metrics;
    }
    
    public ArrayList<JoinDefinition> getJoins() {
        return joins;
    }
    
    public ArrayList getWhiteHardhints() {
        return whiteHardhints;
    }

    public ArrayList getBlackHardhints() {
        return blackHardhints;
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
