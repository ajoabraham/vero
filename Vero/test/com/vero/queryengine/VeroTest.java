/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.datasource.DataSource;
import com.vero.datasource.DsType;
import com.vero.datasource.*;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ajoabraham
 */
public class VeroTest {
    private static Teradata ds;
    private static Table t1;
    private static Table t2;
    
    public VeroTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        ds = new Teradata(DsType.TERADATA,"Teardata - Prod", null);
        ds.addTable(new Table("DepartmentFacts",ds));
        ds.addTable(new Table("Departments",ds));
        t1 = ds.getTable("Departments");
        t1.addColumn(new Column("id",ColDataType.INTEGER,true,t1));
        t1.addColumn(new Column("name",ColDataType.STRING,false,t1));
        t1.addColumn(new Column("school_name",ColDataType.STRING,false,t1));
        t1.addColumn(new Column("location",ColDataType.STRING,false,t1));
        
        t2 = ds.getTable("DepartmentFacts");
        t2.addColumn(new Column("dept_id",ColDataType.INTEGER,false,t2));
        t2.addColumn(new Column("num_students",ColDataType.INTEGER,false,t2));
        t2.addColumn(new Column("num_professors",ColDataType.INTEGER,false,t2));
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSQLEngine() {
        
        System.out.println("printMap");
        Map mp = null;
        Vero.printMap(mp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
