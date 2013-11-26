package com.vero.datasource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Table {
    String objectName = "";
    String displayName = "";
    ArrayList<Column> columns;
    int rowCount;
    Date lastScan;
    Column primaryKey;
    DataSource dataSource;
    
    public Table(String inName, DataSource inDS) {
        objectName = inName;
        displayName = inName;
        columns = new ArrayList();
        rowCount = 0;
        lastScan = new Date();
        primaryKey = null;
        dataSource = inDS;
    }
    
    public String getObjectName() {
        return objectName;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    public int getRowCount() {
        return rowCount;
    }
    
    public Date getLastScan() {
        return lastScan;
    }
    
    public void addColumn(Column col){
        columns.add(col);
    }
}
