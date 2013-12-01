/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.db;

import com.vero.metadata.Column;
import com.vero.metadata.Table;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ajoabraham
 */
public class MySQLDBTest {
    
    public static MySQLDB db;
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Testing MySQL DB");
        db = new MySQLDB();
        db.setUsername("stuser")
                .setPassword("sourcetable")
                .setDatabaseName("northwind")
                .setHostName("mysql5.cokqqhqwkadj.us-west-2.rds.amazonaws.com");
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        db.close();
    }
    
    @Test
    public void testConnect() throws SQLException {
        Connection result = db.connect();
        assertTrue("Should connect to DB in 30 Seconds", result.isValid(30));
    }

    @Test
    public void testTestConnection() {
        boolean result = db.testConnection();
        assertTrue("Valid connection should be true.", result);
    }
    
    @Test
    public void testGetDatabases() throws SQLException{
        List<String> d = db.getDatabases();
        assertTrue("Northwind db should be in this collection",d.contains("northwind"));
    }
    
    @Test
    public void testGetDBTables() throws SQLException{
        Map<String, Table> d = db.getDBTables();
        assertTrue("CustomerDemographics table should be in the collection",d.containsKey("CustomerDemographics"));
    }
    
    @Test
    public void testExpectedColumnsArePresent() throws SQLException{
        Map<String, Table> d = db.getDBTables();
        String[] expectedColumns = {"ShipVia","ShipCity","ShipRegion","Freight",
                                    "OrderDate","RequiredDate","ShippedDate",
                                    "OrderID","CustomerID","EmployeeID","ShipName",
                                     "ShipAddress","ShipPostalCode","ShipCountry"};
        
        Table t = d.get("Orders");
        Column c;
        for(String col : expectedColumns){
            c = t.getColumn(col);
            assertTrue(col+" column should be in Orders table",c != null);
        }
        
    }
    
    @Test
    public void testIdentifyPrimaryKeys() throws SQLException{
        Map<String, Table> d = db.getDBTables();
        Table t = d.get("Employees");
        db.identifyKeys(t);
        Column c = d.get("Employees").getPrimaryKeyColumns().get(0);
        
        assertTrue("EmployeeID should be the first and only primary key: ",
                c.getObjectName().equals("EmployeeID"));
        
        assertTrue("There should only be 1 primary key in this table: ",
                d.get("Employees").getPrimaryKeyColumns().size()==1);
        
    }
    
    @Test
    public void testIdentifyForeignKeys() throws SQLException{
        Map<String, Table> d = db.getDBTables();
        Table t = d.get("Orders");
        db.identifyKeys(t);
        ArrayList<Column> c = t.getForeignKeyColumns();
        
        String[] expectedColumns = {"CustomerID","ShipVia","EmployeeID"};
        
        assertTrue("There should be 3 foreign key columns: ",
                c.size()==3);
        
        for (String eCol : expectedColumns) {
           boolean found = false;
           for(Iterator<Column> it= c.iterator(); it.hasNext();){
               if (it.next().getObjectName().equals(eCol)){
                   found = true;
               }
           }
           assertTrue(eCol + " should be a foreign key ",found);
        }
    }
    
    @Test
    public void testBridgeTableKeysAreForeignKeys() throws SQLException{
        Map<String, Table> d = db.getDBTables();
        Table t = d.get("EmployeeTerritories");
        db.identifyKeys(t);
        ArrayList<Column> c = t.getForeignKeyColumns();
        
        String[] expectedColumns = {"TerritoryID","EmployeeID"};
        
        assertTrue("There should be 2 foreign key columns: ",
                c.size()==2);
        
        for (String eCol : expectedColumns) {
           boolean found = false;
           for(Iterator<Column> it= c.iterator(); it.hasNext();){
               if (it.next().getObjectName().equals(eCol)){
                   found = true;
               }
           }
           assertTrue(eCol + " should be a foreign key ",found);
        }
    }
}
