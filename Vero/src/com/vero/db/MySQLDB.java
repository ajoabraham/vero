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
public class MySQLDB extends AbstractDB{
    private static final String TEST_CONNECT_QUERY = "SELECT 2+2";
    private final HashMap<String,Table> _catalog = new HashMap<>();
    
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
    public Connection connect() {
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
}
