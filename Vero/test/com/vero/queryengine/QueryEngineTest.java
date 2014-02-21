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
    public void testTai() {
        TestParser testParser = new TestParser("tai.json");
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
        assertEquals("tai", expectedSQL, resultSQL);
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
}