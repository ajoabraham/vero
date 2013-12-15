/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.db;

import com.vero.metadata.Column;
import com.vero.metadata.Table;
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
public class TeradataDBTest {
    
    public static TeradataDB db;
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        System.out.println("Testing Teradata DB....");
        db = new TeradataDB();
        db.setUsername("dbc")
                .setPassword("dbc")
                .setDatabaseName("northwind")
                .setHostName("localhost");
        String sql = "CREATE TABLE UpdateStructureTest (id int , col1 int, col2 int, col3 int);";
        db.connect().createStatement().execute(sql);
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        db.connect().createStatement().execute("DROP TABLE UpdateStructureTest;");
        db.close();
    }
    
    @Test
    public void testConnect() throws SQLException {
        db.connect();
        assertTrue("Should connect to DB in 30 Seconds", true);      
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
        assertTrue("CustomerDemographics table should be in the collection",d.containsKey("customerdemographics"));
    }
    
    @Test
    public void testExpectedColumnsArePresent() throws SQLException{
        Map<String, Table> d = db.getDBTables();
        String[] expectedColumns = {"ShipVia","ShipCity","ShipRegion","Freight",
                                    "OrderDate","RequiredDate","ShippedDate",
                                    "OrderID","CustomerID","EmployeeID","ShipName",
                                     "ShipAddress","ShipPostalCode","ShipCountry"};
        
        Table t = d.get("orders");
        Column c;
        for(String col : expectedColumns){
            c = t.getColumn(col);
            assertTrue(col+" column should be in Orders table",c != null);
        }
        
    }
    
    @Test
    public void testIdentifyPrimaryKeys() throws SQLException{
        Map<String, Table> d = db.getDBTables();
        Table t = d.get("employees");
        db.identifyKeys(t);
        Column c = d.get("employees").getPrimaryKeyColumns().get(0);
        
        assertTrue("EmployeeID should be the first and only primary key: ",
                c.getObjectName().equals("EmployeeID"));
        
        assertTrue("There should only be 1 primary key in this table: ",
                d.get("employees").getPrimaryKeyColumns().size()==1);
        
    }
    
    @Test
    public void testIdentifyForeignKeys() throws SQLException{
        Map<String, Table> d = db.getDBTables();
        Table t = d.get("orders");
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
        Table t = d.get("employeeterritories");
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
    
    @Test
    public void testCollectStats() throws SQLException{
        Table t = db.getDBTables().get("orders");
        db.collectStats(t);
        assertTrue("Orders table should have 830 rows.", t.getRowCount()==830);
        assertTrue("Last stat timestamp should have been updated.", t.getLastStatDate()!=null);
    }
    
    @Test
    public void testUpdateStructureNewColumn() throws SQLException{
        Table t = db.getDBTables().get("UpdateStructureTest");
        db.connect().createStatement()
          .execute("ALTER TABLE UpdateStructureTest ADD new_col1 int;");

        db.updateTableStructure(t);
        assertTrue("new_col1 column should have be added to the table.", t.getColumn("new_col1") != null);
    }
    
    @Test
    public void testUpdateStructureRemoveColumn() throws SQLException{
        Table t = db.getDBTables().get("UpdateStructureTest");
        db.connect().createStatement()
          .execute("ALTER TABLE UpdateStructureTest DROP col1;");
        
        db.updateTableStructure(t);
        assertTrue("col1 column should have been removed from the table.", t.getColumn("col1") == null);
        assertTrue("col2 column should still be present.",t.getColumn("col2")!= null);
    }
    
    @Test
    public void testUpdateStructureUpdateColumnType() throws SQLException{
        Table t = db.getDBTables().get("UpdateStructureTest");
        
        // cannot simply alter column datatypes in teradata
        // we have to drop and create new table
        db.connect().createStatement()
          .execute("DROP TABLE UpdateStructureTest;");
        
        String sql = "CREATE TABLE UpdateStructureTest (id int , col1 int, col2 int, col3 int,new_col1 varchar(22) not null);";
        db.connect().createStatement().execute(sql);
        
        db.updateTableStructure(t);
        assertTrue("new_col1 column should now have String datatype.", 
                t.getColumn("new_col1").getDataType().equalsIgnoreCase("varchar"));
        assertTrue("new_col1 column should have size of 22.", 
                t.getColumn("new_col1").getDataTypeSize()==22);       
    }
    
    @Test
    public void testUpdateStructureUpdateColumnKeyType() throws SQLException{
        Table t = db.getDBTables().get("UpdateStructureTest");
        db.connect().createStatement()
          .execute("ALTER TABLE UpdateStructureTest ADD PRIMARY KEY (new_col1)");
        
        db.updateTableStructure(t);
        assertTrue("new_col1 column should now be a primary key.", 
                t.getColumn("new_col1").isPrimaryKey());
    }
    
    @Test
    public void testGetSchemas(){
        try {
            db.getSchemas();
            fail("Teradata doesnt support schemas so this method should throw an exception.");
        } catch (Exception ex) {
            assertTrue(true);
        }
    }
}
