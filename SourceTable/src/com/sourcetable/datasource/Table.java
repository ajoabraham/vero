package com.sourcetable.datasource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Table {
    String name;
    ArrayList<Column> columns;
    int rowCount;
    Date lastScan;
    Column primaryKey;
    DataSource dataSource;
    
	public String getName() {
		return name;
	}
    
    public int getRowCount() {
        return rowCount;
    }
    
    public Date getLastScan() {
        return lastScan;
    }
}
