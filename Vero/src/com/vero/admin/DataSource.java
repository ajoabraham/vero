package com.vero.admin;

import com.vero.metadata.Table;
import java.util.HashMap;
import java.util.UUID;

public class DataSource {
    private UUID uuid = null;
    private DsType type;
    private String name;
    private String description;
    private HashMap<String, Table> tables;
    
    public enum DsType {
        NONE,
        MYSQL,
        TERADATA
    }
    
    public DataSource() {        
    }
    
    public DataSource(UUID uuid, DsType inType, String inName, String inDescription) {
        this.uuid = uuid;
        type = inType;
        name = inName;
        description = inDescription;
        tables = new HashMap();
    }
    
    public void addTable(Table inTable) {
        tables.put(inTable.getObjectName(), inTable);
    }
    
    public UUID getUUID() {
        return uuid;
    }
    
    public DsType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    public Table getTable(String inName) {
        return tables.get(inName);
    }
    
    public HashMap getTables() {
        return tables;
    }
}
