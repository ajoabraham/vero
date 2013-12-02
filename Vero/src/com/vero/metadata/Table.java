package com.vero.metadata;

import com.vero.admin.DataSource;
import java.sql.Timestamp;
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
    private Timestamp lastStatCollectionDate;
    private DataSource dataSource;
    private LogicalTableTypes tableLogicalType = LogicalTableTypes.UNKNOWN;
    
    public enum LogicalTableTypes{
        DIMENSION,
        BRIDGE,
        FACT,
        AGGREGATE,
        UNKNOWN
    }
    
    public Table(){}
    
    public Table(String physicalName, DataSource ds) {
        objectName = physicalName;
        this.physicalName = physicalName;
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

    public LogicalTableTypes getTableLogicalType() {
        if(tableLogicalType==LogicalTableTypes.UNKNOWN){
            ArrayList<Column> pkcols = getPrimaryKeyColumns();
            ArrayList<Column> fkcols = getForeignKeyColumns();
            ArrayList<Column> nonkeycols = getNonKeyColumns();
            
            if(pkcols.size()>0 && fkcols.isEmpty()){
                return LogicalTableTypes.DIMENSION;
            }else if(fkcols.size()==getColumns().size() || 
                    (fkcols.size()>0 && nonkeycols.isEmpty())){
                return LogicalTableTypes.BRIDGE;
            }else if(fkcols.size()>0 && nonkeycols.size()>0){
                return LogicalTableTypes.FACT;
            }
            
        }
        return tableLogicalType;
    }

    public void setLogicalType(LogicalTableTypes tableLogicalType) {
        this.tableLogicalType = tableLogicalType;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public int getRowCount() {
        return rowCount;
    }
    
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        this.lastStatCollectionDate = new Timestamp((new Date()).getTime());
    }
    
    public Date getLastStatDate() {
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
    
    public ArrayList<Column> getNonKeyColumns(){
        ArrayList<Column> col = new ArrayList();
        Iterator<String> it = getColumns().keySet().iterator();
        while(it.hasNext()){
            Column c = getColumn(it.next());
            if(!c.isForeignKey() && !c.isPrimaryKey()){
                col.add(c);
            }
        }
        return col;
    }
}
