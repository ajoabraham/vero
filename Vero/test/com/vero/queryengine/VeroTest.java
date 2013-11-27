/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.datasource.DsType;
import com.vero.datasource.*;
import com.vero.metadata.*;
import java.util.ArrayList;
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
    private static ArrayList<AttributeMeta> attrs = new ArrayList();
    private static ArrayList<MetricMeta> metrics = new ArrayList();
    private static JoinMeta join;
    
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
        
        AttributeMeta dname = new AttributeMeta("Department Name", null);
        ExpressionMeta dnexp = new ExpressionMeta("name");
        dname.addExpression(dnexp);
        dnexp.addTable(t1);
        attrs.add(dname);
        
        AttributeMeta dschool = new AttributeMeta("Department School", null);
        ExpressionMeta dsexp = new ExpressionMeta("school_name");
        dschool.addExpression(dsexp);
        dnexp.addTable(t1);
        attrs.add(dschool);
        
        MetricMeta met = new MetricMeta("# of Students", null);
        ExpressionMeta metexp = new ExpressionMeta("name");
        met.addExpression(metexp);
        metexp.addTable(t2);
        metrics.add(met);
        
        join = new JoinMeta(
                    "on equal id",
                    "Departments",
                    "id",
                    "=",
                    "DepartmentFacts",
                    "dept_id",
                    "tleft.id=tright.dept_id",
                    "inner"
                );
        
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
        
       
        fail("The test case is a prototype.");
    }
    
}
