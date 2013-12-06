/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.db;

import com.vero.metadata.Table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajoabraham
 */
public class PostgresDB extends AbstractDB{
    private static final String TEST_CONNECT_QUERY = "SELECT 2+2";
    private final HashMap<String,Table> _catalog = new HashMap<>();
    
    public PostgresDB(){
        VENDOR_NAME = "PostgreSQL";
        DRIVER = "org.postgresql.Driver";
        ABSOLUTE_TABLE_NAME_PATTERN="DATABASE.SCHEMA.TABLE";
        SUPPORTED_VERSIONS= new String[]{"9.3","9.2"};
        DEFAULT_SCHEMA="public";
        this.setPort(5432);
        
        try {
            Class.forName(DRIVER).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            Logger.getLogger(MySQLDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Connection connect() {
        if(_conn !=null ){
            return _conn;
        }
        
        StringBuilder sb = new StringBuilder("jdbc:postgresql://");
        
        sb.append(getHostName())
            .append(":")
            .append(getPort())
            .append("/")
            .append(getDatabaseName());
        
        Properties props = new Properties();
        props.setProperty("user",getUsername());
        props.setProperty("password",getPassword());
        props.setProperty("search_path",getSchemaName());
        
        try {
            _conn = DriverManager.getConnection(sb.toString(),props);
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

    @Override
    public ArrayList<String> getSchemas() throws SQLException {
        ArrayList<String> s = new ArrayList();
        ResultSet rs = connect().getMetaData().getSchemas(getDatabaseName(), null);
        while(rs.next()){
            s.add(rs.getString("TABLE_SCHEM"));
        }
        return s;
    }
    
    
}
