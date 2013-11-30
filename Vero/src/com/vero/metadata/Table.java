package com.vero.metadata;

import com.vero.admin.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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

    public Column getColumn(String columnName) {
        Iterator<Column> it = columns.iterator();
        while(it.hasNext()){
            Column c = it.next();
            if(c.getName().equals(columnName)){
                return c;
            }
        }
        return null;
    }
}
