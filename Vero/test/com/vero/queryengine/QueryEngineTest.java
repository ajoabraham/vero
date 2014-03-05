/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.report.Report;
import com.vero.session.Session;
import com.vero.testparser.TestParser;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yulinwen
 */
public class QueryEngineTest {    
    @Test
    public void testTest1() {
        TestParser testParser = new TestParser("test1.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);               

        String expectedSQL = 
            "SELECT \"T0\".\"school_name\", sum(\"T1\".\"num_students\")\n" +
            "FROM \"Departments\" AS \"T0\"\n" +
            "INNER JOIN \"DepartmentFacts\" AS \"T1\" ON (\"T0\".\"id\" = \"T1\".\"dept_id\")\n" +
            "GROUP BY \"T0\".\"school_name\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("test1", expectedSQL, resultSQL);
    }

    @Test
    public void testTest2() {
        TestParser testParser = new TestParser("test2.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);                

        String expectedSQL = 
            "SELECT \"T0\".\"name\", \"T1\".\"name\", count(DISTINCT \"T2\".\"course_id\")\n" +
            "FROM \"Departments\" AS \"T0\"\n" +
            "CROSS JOIN \"Departments\" AS \"T1\"\n" +
            "CROSS JOIN \"LessonsFact\" AS \"T2\"\n" +
            "GROUP BY \"T0\".\"name\", \"T1\".\"name\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("test2", expectedSQL, resultSQL);
    }
    
    @Test
    public void testTest3() {
        TestParser testParser = new TestParser("test3.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);                

        String expectedSQL = 
            "SELECT \"T4\".\"name\", \"T2\".\"name\", count(\"T0\".\"course_id\")\n" +
            "FROM \"LessonsFact\" AS \"T0\"\n" +
            "INNER JOIN \"Students\" AS \"T1\" ON (\"T0\".\"std_id\" = \"T1\".\"id\")\n" +
            "INNER JOIN \"Departments\" AS \"T2\" ON (\"T2\".\"id\" = \"T1\".\"dept_id\")\n" +
            "INNER JOIN \"Professors\" AS \"T3\" ON (\"T0\".\"prof_id\" = \"T3\".\"id\")\n" +
            "INNER JOIN \"Departments\" AS \"T4\" ON (\"T4\".\"id\" = \"T3\".\"dept_id\")\n" +
            "GROUP BY \"T4\".\"name\", \"T2\".\"name\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("test3", expectedSQL, resultSQL);
    }
    
    @Test
    public void testTest4() {
        TestParser testParser = new TestParser("test4.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL =         
            "SELECT \"T4\".\"name\", \"T0\".\"name\", count(\"T2\".\"course_id\")\n" +
            "FROM \"Departments\" AS \"T0\"\n" +
            "INNER JOIN \"Students\" AS \"T1\" ON (\"T0\".\"id\" = \"T1\".\"dept_id\")\n" +
            "INNER JOIN \"LessonsFact\" AS \"T2\" ON (\"T2\".\"prof_id\" = \"T3\".\"id\")\n" +
            "INNER JOIN \"Departments\" AS \"T4\" ON (\"T4\".\"id\" = \"T3\".\"dept_id\")\n" +
            "CROSS JOIN \"Students\" AS \"T1\"\n" +
            "GROUP BY \"T4\".\"name\", \"T0\".\"name\"";
                
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("test4", expectedSQL, resultSQL);
    }
    
    @Test
    public void testTest5() {
        TestParser testParser = new TestParser("test5.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT upper(\"T0\".\"name\")\n" +
            "FROM \"Departments\" AS \"T0\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("test5", expectedSQL, resultSQL);
    }
    
    @Test
    public void testTest6() {
        TestParser testParser = new TestParser("test6.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT upper(\"T0\".\"name\")\n" +
            "FROM \"Departments\" AS \"T0\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("test6", expectedSQL, resultSQL);
    }
    
    @Test
    public void testTest7() {
        TestParser testParser = new TestParser("test7.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT \"T4\".\"name\", \"T0\".\"name\", avg(\"T2\".\"course_id\") OVER ( ROWS 7 PRECEDING)\n" +
            "FROM \"Departments\" AS \"T0\"\n" +
            "INNER JOIN \"Students\" AS \"T1\" ON (\"T0\".\"id\" = \"T1\".\"dept_id\")\n" +
            "INNER JOIN \"LessonsFact\" AS \"T2\" ON (\"T2\".\"prof_id\" = \"T3\".\"id\")\n" +
            "INNER JOIN \"Departments\" AS \"T4\" ON (\"T4\".\"id\" = \"T3\".\"dept_id\")\n" +
            "CROSS JOIN \"Students\" AS \"T1\"\n" +
            "GROUP BY \"T4\".\"name\", \"T0\".\"name\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("test7", expectedSQL, resultSQL);
    }

    @Test
    public void testTest8() {
        TestParser testParser = new TestParser("test8.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT \"T0\".\"CompanyName\", \"T0\".\"Phone\"\n" +
            "FROM \"shippers\" AS \"T0\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("test8", expectedSQL, resultSQL);
    }    
    
    @Test
    public void testTest9() {
        TestParser testParser = new TestParser("test9.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT \"T4\".\"name\", \"T0\".\"name\", count(\"T2\".\"course_id\")\n" +
            "FROM \"Departments\" AS \"T0\"\n" +
            "INNER JOIN \"Students\" AS \"T1\" ON (\"T0\".\"id\" = \"T1\".\"dept_id\")\n" +
            "INNER JOIN \"LessonsFact\" AS \"T2\" ON (\"T2\".\"prof_id\" = \"T3\".\"id\")\n" +
            "INNER JOIN \"Departments\" AS \"T4\" ON (\"T4\".\"id\" = \"T3\".\"dept_id\")\n" +
            "CROSS JOIN \"Students\" AS \"T1\"\n" +
            "GROUP BY \"T4\".\"name\", \"T0\".\"name\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("test9", expectedSQL, resultSQL);
    }
    
    @Test
    public void testTai1() {
        TestParser testParser = new TestParser("tai1.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT \"T0\".\"Phone\"\n" +
            "FROM \"shippers\" AS \"T0\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("tai1", expectedSQL, resultSQL);
    }
    
    @Test
    public void testTai2() {
        TestParser testParser = new TestParser("tai2.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT \"T0\".\"Phone\", sum(\"T1\".\"UnitPrice\")\n" +
            "FROM \"shippers\" AS \"T0\"\n" +
            "CROSS JOIN \"order_details\" AS \"T1\"\n" +
            "GROUP BY \"T0\".\"Phone\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("tai2", expectedSQL, resultSQL);
    }
    
    @Test
    public void testTai3() {
        TestParser testParser = new TestParser("tai3.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT \"T0\".\"ProductName\", \"T1\".\"CategoryName\", \"T0\".\"ProductID\"\n" +
            "FROM \"products\" AS \"T0\"\n" +
            "INNER JOIN \"categories\" AS \"T1\" ON (\"T0\".\"CategoryID\" = \"T1\".\"CategoryID\")";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("tai3", expectedSQL, resultSQL);
    }
    
    @Test
    public void testTai4() {
        TestParser testParser = new TestParser("tai4.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT \"T0\".\"UnitsInStock\", \"T1\".\"CompanyName\"\n" +
            "FROM \"products\" AS \"T0\"\n" +
            "CROSS JOIN \"shippers\" AS \"T1\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("tai4", expectedSQL, resultSQL);
    }
    
    @Test
    public void testYulin1() {
        TestParser testParser = new TestParser("yulin1.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT \"T2\".\"CompanyName\", \"T1\".\"CategoryName\", \"T0\".\"CompanyName\"\n" +
            "FROM \"shippers\" AS \"T0\"\n" +
            "INNER JOIN \"categories\" AS \"T1\" ON (\"T0\".\"ShipperID\" = \"T1\".\"CategoryID\")\n" +
            "CROSS JOIN \"shippers\" AS \"T2\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("yulin1", expectedSQL, resultSQL);
    }    

    @Test
    public void testYulin2() {
        TestParser testParser = new TestParser("yulin2.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT \"T0\".\"CustomerTypeID\", \"T1\".\"CompanyName\", \"T2\".\"TerritoryDescription\"\n" +
            "FROM \"customercustomerdemo\" AS \"T0\"\n" +
            "CROSS JOIN \"shippers\" AS \"T1\"\n" +
            "CROSS JOIN \"territories\" AS \"T2\"";
        
        Report curReport = queryEngine.getReport();          
        String resultSQL = curReport.getBlocks().get(0).getSqlString();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals("yulin2", expectedSQL, resultSQL);
    }
}