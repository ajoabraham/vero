package com.vero.metadata;

import com.vero.admin.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Table {
    private String objectName = "";
    private String physicalName = "";
    private Map<String,Column> columns = new HashMap();
    private int rowCount = -1;
    private Date lastStatCollectionDate;
    private DataSource dataSource;
    
    public Table(){}
    
    public Table(String physicalName, DataSource ds) {
        objectName = physicalName;
        this.physicalName = physicalName;
        columns = new HashMap();
        rowCount = -1;
        dataSource = ds;
    }
    
    public String getObjectName() {
        return objectName;
    }

    public String getPhysicalName() {
        return physicalName;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public int getRowCount() {
        return rowCount;
    }
    
    public Date getLastScan() {
        return lastStatCollectionDate;
    }
    
    public void addColumn(Column col){
        getColumns().put(col.getObjectName(),col);
    }
    
    public void removeColumn(Column col){
        getColumns().remove(col.getObjectName());
    }
    
    public Map<String, Column> getColumns(){
        return columns;
    }

    public Column getColumn(String columnName) {       
        return getColumns().get(columnName);
    }
    
    public ArrayList<Column> getPrimaryKeyColumns(){
        ArrayList<Column> pkcol = new ArrayList();
        Iterator<String> it = getColumns().keySet().iterator();
        while(it.hasNext()){
            Column c = getColumn(it.next());
            if(c.isPrimaryKey()){
                pkcol.add(c);
            }
        }
        return pkcol;
    }
    
    public ArrayList<Column> getForeignKeyColumns(){
        ArrayList<Column> fkcol = new ArrayList();
        Iterator<String> it = getColumns().keySet().iterator();
        while(it.hasNext()){
            Column c = getColumn(it.next());
            if(c.isForeignKey()){
                fkcol.add(c);
            }
        }
        return fkcol;
    }
}
