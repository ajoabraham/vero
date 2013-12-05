/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.db;

import com.vero.metadata.Table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajoabraham
 */
public class MSSQLServerDB extends AbstractDB{
    private static final String TEST_CONNECT_QUERY = "SELECT 2+2";
    private final HashMap<String,Table> _catalog = new HashMap<>();
    
    public MSSQLServerDB(){
        VENDOR_NAME = "Microsoft SQL Server";
        DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        ABSOLUTE_TABLE_NAME_PATTERN="DATABASE.SCHEMA.TABLE";
        SUPPORTED_VERSIONS= new String[]{"2005", "2008", "2008 R2", "2012", "Azure"};
        DEFAULT_SCHEMA="dbo";
        this.setPort(1433);
        
        try {
            Class.forName(DRIVER).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Connection connect() {
        StringBuilder sb = new StringBuilder("jdbc:sqlserver://");
        
        sb.append(getHostName())
            .append(":")
            .append(getPort())
            .append(";databaseName=")
            .append(getDatabaseName())
            .append(";")
            .append("user=")
            .append(getUsername())
            .append(";")
            .append("password=")
            .append(getPassword());
        
        try {
            _conn = DriverManager.getConnection(sb.toString());
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, sb.toString(), ex);
        }
        return _conn;
    }

    
    @Override
    public boolean testConnection() { 
        try {
            connect().createStatement().execute(TEST_CONNECT_QUERY);
            return true; 
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }          
    }

    @Override
    public boolean supportsSchema() {
        return true; 
    }
    
    
}
