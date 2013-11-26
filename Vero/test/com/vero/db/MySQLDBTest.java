/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.db;

import com.vero.datasource.Table;
import java.sql.Connection;
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
    public void testGetDBTablesBuildsColumn() throws SQLException{
        Map<String, Table> d = db.getDBTables();
        Table t = d.get("CustomerDemographics");
        
        fail("Test that table object contains expected columns.");
    }
    
    @Test
    public void testGetDBTablesIdentifiesPrimaryKey() throws SQLException{
        Map<String, Table> d = db.getDBTables();
        Table t = d.get("CustomerDemographics");
        
        fail("Test that table was built with primary key identifier.");
    }
    
}
