package com.vero.admin;

import com.vero.metadata.Table;
import java.util.HashMap;
import java.util.UUID;

public class DataSource {
    UUID uuid;
    DsType type;
    String name;
    String description;
    HashMap<String, Table> tables;
    
    public enum DsType {
        NONE,
        MYSQL,
        TERADATA
    }
    
    public DataSource(DsType inType, String inName, String inDescription) {
        uuid = UUID.randomUUID();
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
