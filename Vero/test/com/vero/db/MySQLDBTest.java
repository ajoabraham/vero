/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.db;

import com.vero.metadata.Column;
import com.vero.metadata.Table;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                                    "OrderData","RequiredDate","ShippedDate",
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
        Table t = d.get("CustomerDemographics");
        
        fail("Test that table was built with primary key identifier.");
    }
    
     @Test
    public void testIdentifyForeignKeys() throws SQLException{
        System.out.println(db.getDatabaseName());
        ResultSet rs = db.connect().getMetaData().getPrimaryKeys(db.getDatabaseName(), null, "Orders");
        AbstractDB.printResultSet("Foreign Keys", rs);
        fail("Test that table foreign keys were detected.");
    }
    
}
