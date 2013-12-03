package com.vero.metadata;

import com.vero.admin.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A table in Vero closely reflects the table and its structure as it exists
 * in the target database.  Overtime we may add some methods on the table
 * to assist in query generation.
 * 
 * @author ajoabraham
 */
public class Table {
    private String objectName = "";
    private String physicalName = "";
    private Map<String,Column> columns = new HashMap();
    private int rowCount = -1;
    private Timestamp lastStatCollectionDate;
    private DataSource dataSource;
    private LogicalTableTypes tableLogicalType = LogicalTableTypes.UNKNOWN;
    
    /**
     * Enumeration of different LogicalTableTypes.  This will be essential
     * in generating the correct query.
     */
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
    
    /**
     * This is the name of the the table as it is displayed in Vero.  It maybe
     * different from the name of the table as it exists in the db.
     * 
     * @return 
     */
    public String getObjectName() {
        return objectName;
    }
    
    /**
     * This is the exact name of the table as it exists in the database.
     * 
     * @return 
     */
    public String getPhysicalName() {
        return physicalName;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
    
    /**
     * We will try to guess the logical type based on the number of primary
     * keys and foreign keys if the user hasn't manually set the table type.
     * @return 
     */
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
    
    /**
     * Manually assign a table logical type.  This will be informative in
     * generating efficient SQL queries.
     * 
     * @param tableLogicalType 
     */
    public void setLogicalType(LogicalTableTypes tableLogicalType) {
        this.tableLogicalType = tableLogicalType;
    }
    
    /**
     * This is the datasource this table belongs to.
     * @param dataSource 
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public int getRowCount() {
        return rowCount;
    }
    
    /**
     * Updating row counts will automatically update the last stat collection
     * timestamp.
     * 
     * @param rowCount 
     */
    public void setRowCount(int rowCount) {
        this.lastStatCollectionDate = new Timestamp((new Date()).getTime());
        this.rowCount = rowCount;       
    }
    
    /**
     * This is the last date and time the number of rows was updated for
     * this table.
     * 
     * @return 
     */
    public Timestamp getLastStatDate() {
        return lastStatCollectionDate;
    }
    
    public void addColumn(Column col){
        getColumns().put(col.getObjectName(),col);
    }
    
    /**
     * Remove a column by its name.  It has to be the exact name exactly as
     * its stored in the database. 
     * 
     * @param col Case sensitive name of the column.
     */
    public void removeColumn(Column col){
        getColumns().remove(col.getObjectName());
    }
    
    public Map<String, Column> getColumns(){
        return columns;
    }
    
    /**
     * Retrieve a column by its name.  It has to be the exact name exactly as
     * its stored in the database.
     * 
     * @param columnName Case sensitive name of the column.
     * @return 
     */
    public Column getColumn(String columnName) {       
        return getColumns().get(columnName);
    }
    
    /**
     * Primary key columns represent unique row identifiers of a table.
     * Generally primary key columns are also used in joins.
     * 
     * @return ArrayList of all the primary key columns.
     */
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
    
    /**
     * Foreign key columns are columns who have a parent table in which they
     * are the primary key.  This is a join clue as well as a table type clue.
     * 
     * @return ArrayList of all the foreign key columns.
     */
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
    /**
     * Non key columns are those which are nether a primary key nor a 
     * foreign key.
     * 
     * @return ArrayList of all the Non Key columns.
     */
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
