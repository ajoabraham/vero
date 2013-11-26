/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.db;

import com.vero.datasource.ColDataType;
import com.vero.datasource.Column;
import com.vero.datasource.Table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajoabraham
 */
public class MySQLDB extends AbstractDB{
    private static final String TEST_CONNECT_QUERY = "SELECT 2+2";
    private HashMap<String,Table> _catalog = new HashMap<>();
    
    public MySQLDB(){
        VENDOR_NAME = "MySQL";
        DRIVER = "com.mysql.jdbc.Driver";
        ABSOLUTE_TABLE_NAME_PATTERN="DATABASE.TABLE";
        SUPPORTED_VERSIONS= new String[]{"5.1","5.5","5.6","5.7"};
        this.setPort(3306);
        
        try {
            Class.forName(DRIVER).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    Connection connect() {
        StringBuilder sb = new StringBuilder("jdbc:mysql://");
        
        sb.append(getHostName())
            .append(":")
            .append(getPort())
            .append("/")
            .append(getDatabaseName())
            .append("?")
            .append("user=")
            .append(getUsername())
            .append("&")
            .append("password=")
            .append(getPassword());
        
        try {
            conn = DriverManager.getConnection(sb.toString());
            testConnection();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, sb.toString(), ex);
        }
        return conn;
    }

    @Override
    void testConnection() {
        
        try {
            conn.createStatement().execute(TEST_CONNECT_QUERY);
            
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    HashMap<String, Table> getDBTables() {
        if (_catalog == null){
            try {
                loadCatalog();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return _catalog;
    }

    @Override
    void getDatabases() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void getSchemas() {
        throw new UnsupportedOperationException("MySQL does not support Schemas specifically (databases and schemas are the same thing)."); 
    }
    
    public boolean supportsSchema(){
        return false;
    }
    
    public void loadCatalog() throws SQLException{
        _catalog.clear();
        ResultSet rs = conn.getMetaData().getColumns(this.getDatabaseName(), null, null, null);
        String tableName = "";
        String prevTableName="";
        Table t = null;
        
        while(rs.next()){
            tableName = rs.getString("TABLE_NAME");
            
            if(!tableName.equals(prevTableName)){
                t = new Table(tableName,null);
                if(!prevTableName.isEmpty()){
                    _catalog.put(prevTableName, t);
                }
            }
            
            Column c = new Column(
                    rs.getString("TABLE_NAME"),
                    ColDataType.NONE,
                    false,
                    t   );
            t.addColumn(c);
            
            prevTableName = tableName;
        }
    }
}
