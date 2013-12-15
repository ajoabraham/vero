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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajoabraham
 */
public class TeradataDB extends AbstractDB{
    private static final String TEST_CONNECT_QUERY = "SELECT 2+2";
    private final HashMap<String,Table> _catalog = new HashMap<>();
    
    public TeradataDB(){
        VENDOR_NAME = "Teradata";
        DRIVER = "com.teradata.jdbc.TeraDriver";
        ABSOLUTE_TABLE_NAME_PATTERN="DATABASE.TABLE";
        SUPPORTED_VERSIONS= new String[]{"12.00", "13.00", "13.10", "14.00","14.10"};
        this.setPort(1025);
        
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
        
        StringBuilder sb = new StringBuilder("jdbc:teradata://");
        
        sb.append(getHostName())
                .append("/").append("DBS_PORT=")
                .append(getPort())
                .append(",")
                .append("USER=")
                .append(getUsername())
                .append(",")
                .append("PASSWORD=")
                .append(getPassword())
                .append(",")
                .append("DATABASE=")
                .append(getDatabaseName());
        
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
            Logger.getLogger(TeradataDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }          
    }

    @Override
    public ArrayList<String> getSchemas(){
        throw new UnsupportedOperationException("Teradata does not support schemas."); 
    }

    @Override
    public List<String> getDatabases() throws SQLException {
       if(_databases == null){
            _databases = new ArrayList();
            ResultSet rs = connect().getMetaData().getSchemas();
            
            while(rs.next()){
                _databases.add(rs.getString(1));
            }
        }
        return _databases;
    }

    @Override
    public Map<String, String> getOrderedArgs() {
      if(_orderedArgs == null){ 
          _orderedArgs = new HashMap();
          _orderedArgs.put("schema", getDatabaseName());  
      }
      return _orderedArgs;
    }
    
    
    
    
}
