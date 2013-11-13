package com.sourcetable.datasource;

import java.util.HashMap;

public class DataSource {
	DsType type;
    String description;
    HashMap<String, Table> tables;
    
    public DataSource(DsType inType, String inDescription) {
        type = inType;
        description = inDescription;
        tables = new HashMap();
    }
    
    public void addTable(Table inTable) {
        tables.put(inTable.getName(), inTable);
    }
    
	public DsType getType() {
		return type;
	}
    
	public String getDescription() {
		return description;
	}
    
    public HashMap getTables() {
        return tables;
    }
}
