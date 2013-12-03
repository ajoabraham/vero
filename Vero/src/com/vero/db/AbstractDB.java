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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
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
   
    
    public AbstractDB(){
    }
    
    // ---- ABSTRACT METHODS ---- //
    abstract Connection connect();
    abstract boolean testConnection();
   
    // ---- CORE METHODS ---- //
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
    
    public void getSchemas() {
        throw new UnsupportedOperationException("MySQL does not support Schemas specifically (databases and schemas are the same thing)."); 
    }
    
    public boolean supportsSchema(){
        return false;
    }
    
    public void loadCatalog() throws SQLException{
        if(_catalog == null){
            _catalog = new HashMap();
        }else{
          _catalog.clear();  
        }
        
        ResultSet rs = connect().getMetaData().getColumns(getDatabaseName(), getSchemaName(), null, null);
        String tableName = "";
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
    
    public void close() throws SQLException{
        _conn.close();
        _conn = null;
        if( _catalog != null) {_catalog.clear();}
        _catalog = null;
        if( _databases != null) {_databases.clear();}
        _databases = null;
    }
    
    // ---- SETTER & GETTER METHODS ---- //
    public String getVersion() {
        return version;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public String getAuthMode() {
        return authMode;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AbstractDB setVersion(String version) {
        this.version = version;
        return this;
    }

    public AbstractDB setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    protected AbstractDB setPort(int port) {
        this.port = port;
        return this;
    }

    public AbstractDB setAuthMode(String authMode) {
        this.authMode = authMode;
        return this;
    }

    public AbstractDB setUsername(String username) {
        this.username = username;
        return this;
    }

    public AbstractDB setPassword(String password) {
        this.password = password;
        return this;
    }
    
    public String getDatabaseName() {
        return databaseName;
    }

    public AbstractDB setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public AbstractDB setSchemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }
    
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
}