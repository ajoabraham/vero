/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.db;

import com.vero.metadata.Table;
import com.vero.metadata.Column;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the base class of all subsequent Database objects.  However, 
 * this class implements most of the core functionality required by our app.
 * There are currently on 2 abstract methods because their functionality
 * varies quite a bit from database to database.
 *
 * @author ajoabraham
 */
abstract class AbstractDB implements Serializable {
    // ---- RUNTIME MACROS FOR DB QUERIES ---- //
    public static final String REPORT_NAME = "REPORT_NAME";
    public static final String USERNAME = "USERNAME";
    public static final String APP_NAME = "APP_NAME";
    
    public static String DEFAULT_SCHEMA = "public";
    public static String ABSOLUTE_TABLE_NAME_PATTERN = "DATABASE.SCHEMA.TABLE";
    public static String VENDOR_NAME="Database";
    public static String DRIVER = "driver";
    public static String[] SUPPORTED_VERSIONS;
    public static String[] SUPPORTED_AUTH_MODES;

    private String version="";
    private String hostName="";
    private int port=0;
    private String authMode="";
    private String username="";
    private String password="";
    private String databaseName="";
    private String schemaName="";
    
    protected Connection _conn = null;
    protected Map<String,Table> _catalog;
    protected List<String> _databases;
   
    public AbstractDB(){}
    
    // ---- ABSTRACT METHODS ---- //
    abstract Connection connect();
    abstract boolean testConnection();
   
    // ---- CORE METHODS ---- //
    
    /**
     * Fetches all tables available on the current database.  It will 
     * call {@link #loadCatalog()} if the catalog hasn't been loaded yet else
     * you will get a cached catalog.  To reload the catalog just call 
     * {@link #loadCatalog()}.
     * 
     * @return  A Map where the key is a Table Name and the value 
     *          is a {@link Table}
     */
    public Map<String, Table> getDBTables() {
        if (_catalog == null){
            try {
                loadCatalog();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return _catalog;
    }
    
    /**
     * List of databases on host machine and port
     * @return  List of databases on host machine and port
     * @throws SQLException 
     */
    public List<String> getDatabases() throws SQLException {
        if(_databases == null){
            _databases = new ArrayList();
            ResultSet rs = connect().getMetaData().getCatalogs();
            
            while(rs.next()){
                _databases.add(rs.getString(1));
            }
        }
        return _databases;
    }
    
    /**
     * Returns a list of schemas under a specific database.  MySQL
     * will return null while Postgres will return at least Public.
     */
    public void getSchemas() {
        throw new UnsupportedOperationException("MySQL does not support Schemas specifically (databases and schemas are the same thing)."); 
    }
    
    /**
     * Not all databases supports a concept of Schema as understood by Vero.
     * This is a simple way to check if the db supports it or not.
     * 
     * @return 
     */
    public boolean supportsSchema(){
        return false;
    }
    
    /**
     * This method is primarily used in the UI when users want to see
     * all the tables available in their target database.  They will then 
     * choose the tables they want to use and save those tables to our
     * metadata.  Tables returned are limited to the current Database 
     * and Schema.
     * 
     * This method will not collect stats (update row count) or identify which
     * columns are primary keys and foreign keys.  Those operations are more 
     * expensive and should be called only on chosen tables or if users 
     * manually requests it.
     * 
     * @throws SQLException 
     */
    public void loadCatalog() throws SQLException{
        if(_catalog == null){
            _catalog = new HashMap();
        }else{
          _catalog.clear();  
        }
        
        ResultSet rs = connect().getMetaData().getColumns(getDatabaseName(), getSchemaName(), null, null);
        String tableName;
        String prevTableName="";
        Table t = null;
        
        while(rs.next()){
            tableName = rs.getString("TABLE_NAME");
            
            if(!tableName.equals(prevTableName)){
                if(!prevTableName.isEmpty()){
                    _catalog.put(prevTableName, t);
                }
                t = new Table(tableName,null);
            }
            
            Column c = new Column(
                    rs.getString("COLUMN_NAME"),
                    rs.getString("TYPE_NAME"),
                    rs.getInt("COLUMN_SIZE"),
                    t   );
            c.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
            c.setSqlType(rs.getInt("DATA_TYPE"));
            t.addColumn(c);
            
            prevTableName = tableName;
        }
    }
    
    /**
     * Clears the current catalog and closes all connections 
     * to the database. This is the preferred way to close a database
     * connection.  Do not call close directly on the Connection object.
     * 
     * @throws SQLException 
     */
    public void close() throws SQLException{
        _conn.close();
        _conn = null;
        if( _catalog != null) {_catalog.clear();}
        _catalog = null;
        if( _databases != null) {_databases.clear();}
        _databases = null;
    }
    
    // ---- SETTER & GETTER METHODS ---- //
    
    /**
     * @see #setVersion(java.lang.String) 
     * @return 
     */
    public String getVersion() {
        return version;
    }
    
    /**
     * @see #setHostName(java.lang.String) 
     * @return 
     */
    public String getHostName() {
        return hostName;
    }
    
    /**
     * @see #setPort(int) 
     * @return 
     */
    public int getPort() {
        return port;
    }
    
    /**
     * @see #setAuthMode(java.lang.String) 
     * @return 
     */
    public String getAuthMode() {
        return authMode;
    }
    
    /**
     * @see #setUsername(java.lang.String) 
     * @return 
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @see #setPassword(java.lang.String) 
     * @return 
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * The version of the target database. We should be able to 
     * get this from the JDBC api.  This needs to be standardized format
     * like MAJOR.MINOR maybe.
     * 
     * @param version   MARJOR.MINOR
     * @return 
     */
    public AbstractDB setVersion(String version) {
        this.version = version;
        return this;
    }
    
    /**
     * REQUIRED - The machine name of the target datasource. This could be
     * an IP address or DNS name.
     * 
     * @param hostName  The name or IP address of the database machine.
     * @return 
     */
    public AbstractDB setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }
    
    /**
     * OPTIONAL - The port of the database we are connecting to.
     * 
     * @param port database port
     * @return 
     */
    protected AbstractDB setPort(int port) {
        this.port = port;
        return this;
    }
    
    /**
     * Sets the authentication mode used to log user into the
     * database. The modes available depends on the DB.
     * 
     * @param authMode This should be one of SUPPORED_AUTH_MODES
     * @return 
     */
    public AbstractDB setAuthMode(String authMode) {
        this.authMode = authMode;
        return this;
    }
    
    /**
     * REQUIRED - Will have to be refactored with DBAuth object.
     * @param username
     * @return 
     */
    public AbstractDB setUsername(String username) {
        this.username = username;
        return this;
    }
    
    /**
     * REQUIRED - Will have to be refactored with DBAuth object.
     * @param password
     * @return 
     */
    public AbstractDB setPassword(String password) {
        this.password = password;
        return this;
    }
    
    /**
     * @see #setDatabaseName(java.lang.String) 
     * @return 
     */
    public String getDatabaseName() {
        return databaseName;
    }
    
    /**
     * REQUIRED - this is the root name of the database we are connecting to.
     * In Oracle this is the same as the Schema name.
     * 
     * @param databaseName
     * @return 
     */
    public AbstractDB setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }
    
    /**
     * @see #setSchemaName(java.lang.String) 
     * @return 
     */
    public String getSchemaName() {
        return schemaName;
    }
    
    /**
     * OPTIONAL - Many databases support a schema concept.  Most of the time a 
     * schema is a subspace within a database.  This is true for MS Sql Server and 
     * Postgres.  One big difference is Oracle where the term schema means the
     * same thing as a Database and User.  I dont know if Oracle have a subspace
     * beneath a Database/Schema.
     * 
     * @param schemaName    The name of the schema to be used in this datasource.
     * @return 
     */
    public AbstractDB setSchemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }
    
    /**
     * Simple means to print the results of a ResultSet object to 
     * the console.
     * 
     * @param name  The name to be printed before printing the results.
     *              Helpful when printing many resultset objects.
     * @param rs    The ResultSet object
     * @throws SQLException 
     */
    public static void printResultSet(String name,ResultSet rs) throws SQLException {
        System.out.println(name+":\n");
        StringBuffer col = new StringBuffer();
        for (int j=1;j<rs.getMetaData().getColumnCount()+1;j++){
            if (j>0){
                    col.append(" ");
                }
                col.append(rs.getMetaData().getColumnName(j));
        }
        System.out.println(col);
        while(rs.next()){
            StringBuffer s = new StringBuffer();
            
            for(int i=1;i<rs.getMetaData().getColumnCount()+1;i++){
                if (i>0){
                    s.append(" ");
                }
                s.append(rs.getString(i));
            }
            System.out.println(s);
        }
    }
    /**
     * Identifies the correct keyType of the columns in the
     * supplied tables current collection.  This operation requires 
     * multiple calls to the database and can only work on a per table basis.
     * The current JDBC api doesn't support loading key column information
     * for all tables at once.  
     * 
     * It is recommended that this call is delayed in the UI until the user 
     * manually requests it or the user selects this table for import.
     * 
     * @param t The {@link Table} that needs its keys identified.
     * @throws SQLException 
     */
    public void identifyKeys(Table t) throws SQLException {
        ResultSet rs = connect().getMetaData().getPrimaryKeys(getDatabaseName(), getSchemaName(), t.getObjectName());
        while (rs.next()) {
            String fkcol = rs.getString("COLUMN_NAME");
            t.getColumn(fkcol).setKeyType(Column.KeyTypes.PRIMARY_KEY);
        }
        rs.close();
        
        // Foreign key column identification must always come second.
        // In mysql a bridge table key columns are both primary key
        // and foreign key columns.  For our purposes we just need to know the
        // singular role.
        rs = connect().getMetaData().getImportedKeys(getDatabaseName(), getSchemaName(), t.getObjectName());
        while (rs.next()) {
            String fkcol = rs.getString("FKCOLUMN_NAME");
            t.getColumn(fkcol).setKeyType(Column.KeyTypes.FOREIGN_KEY);
        }
        rs.close();
    }
    
    /**
     * Updates the row count of the supplied table.
     * 
     * @param t Table to update
     * @throws SQLException 
     */
    public void collectStats(Table t) throws SQLException{
        ResultSet rs = connect().createStatement()
                         .executeQuery("SELECT count(*) FROM "+t.getPhysicalName()+";");
        rs.first();
        t.setRowCount(rs.getInt(1));
        rs.close();
    }
    
    /**
     * Update a specific table to reflect changes in the underlying database.
     * During the update process we may add,remove, or change column properties.
     * After the main update body we also call {@link #identifyKeys(com.vero.metadata.Table)} 
     * to make sure that any key changes are absorbed as well.
     * 
     * There is a flaw in the current method where if a key used to be primary
     * or foreign and it no longer is, we don't capture that in this method yet.
     * 
     * @param t Table that needs to be updated
     * @throws SQLException 
     */
    public void updateTableStructure(Table t) throws SQLException{
        ResultSet rs = connect().getMetaData()
                .getColumns(getDatabaseName(), getSchemaName(), t.getPhysicalName(), null);
        
        // Build array of current columns in the table (in the database)
        // We need this in order to find out if any columns have been removed.
        // There might be a better way to do this.
        Map<String,Column> dbTableColumns = new HashMap();
        
        while(rs.next()){
            String colName = rs.getString("COLUMN_NAME");
            // Retrieve existing column if it already exists
            Column currentMetadataCol =t.getColumn(colName);
            
            Column inDBCol = new Column();
            inDBCol.setObjectName(rs.getString("COLUMN_NAME"));
            inDBCol.setDataType(rs.getString("TYPE_NAME"));
            inDBCol.setDataTypeSize(rs.getInt("COLUMN_SIZE"));       
            inDBCol.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
            inDBCol.setSqlType(rs.getInt("DATA_TYPE"));
            dbTableColumns.put(inDBCol.getObjectName(), inDBCol);
            
            if (currentMetadataCol==null){
                t.addColumn(inDBCol);
            }else{
                if(!currentMetadataCol.getDataType().equals(inDBCol.getDataType())){
                    currentMetadataCol.setDataType(inDBCol.getDataType());
                }
                if(currentMetadataCol.getDataTypeSize() != inDBCol.getDataTypeSize()){
                    currentMetadataCol.setDataTypeSize(inDBCol.getDataTypeSize());
                }
                if(currentMetadataCol.getDecimalDigits()!= inDBCol.getDecimalDigits()){
                    currentMetadataCol.setDecimalDigits(inDBCol.getDecimalDigits());
                }
                if(currentMetadataCol.getSqlType()!= inDBCol.getSqlType()){
                    currentMetadataCol.setSqlType(inDBCol.getSqlType());
                }
            }    
        }
        for (Iterator<Column> it = t.getColumns().values().iterator(); it.hasNext();) {           
            if(dbTableColumns.get(it.next().getObjectName()) == null){
                it.remove();
            }
        }
        
        // Last step is to identify column key types.
        // TODO - need to probably reset current key types incase
        // something that was a keyType is no longer.
        identifyKeys(t);
    }
}
