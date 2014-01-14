/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

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
            "SELECT ALL T0.school_name, T1.sum(num_students)\n" +
            "FROM Departments AS T0\n" +
            "INNER JOIN DepartmentFacts AS T1 ON T0.id = T1.dept_id\n" +
            "GROUP BY T0.school_name";
        
        String resultSQL = queryEngine.getResultSQL();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals(expectedSQL, resultSQL);
    }

    @Test
    public void testTest2() {
        TestParser testParser = new TestParser("test2.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT ALL T0.name, T1.name, T2.count(distinct course_id)\n" +
            "FROM Departments AS T0\n" +
            " CROSS JOIN Departments AS T1\n" +
            " CROSS JOIN LessonsFact AS T2\n" +
            "GROUP BY T0.name, T1.name";
        
        String resultSQL = queryEngine.getResultSQL();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals(expectedSQL, resultSQL);
    }
    
    @Test
    public void testTest3() {
        TestParser testParser = new TestParser("test3.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT ALL T4.name, T2.name, T0.count(distinct course_id)\n" +
            "FROM LessonsFact AS T0\n" +
            "INNER JOIN Students AS T1 ON T0.std_id = T1.id\n" +
            "INNER JOIN Departments AS T2 ON T2.id = T1.dept_id\n" +
            "INNER JOIN Professors AS T3 ON T0.prof_id = T3.id\n" +
            "INNER JOIN Departments AS T4 ON T4.id = T3.dept_id\n" +
            "GROUP BY T4.name, T2.name";
        
        String resultSQL = queryEngine.getResultSQL();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals(expectedSQL, resultSQL);
    }
    
    @Test
    public void testTest4() {
        TestParser testParser = new TestParser("test4.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
                
        String expectedSQL = 
            "SELECT ALL T4.name, T0.name, T2.count(distinct course_id)\n" +
            "FROM Departments AS T0\n" +
            "INNER JOIN Students AS T1 ON T0.id = T1.dept_id\n" +
            "INNER JOIN LessonsFact AS T2 ON T2.prof_id = T3.id\n" +
            "INNER JOIN Departments AS T4 ON T4.id = T3.dept_id\n" +
            " CROSS JOIN Students AS T1\n" +
            "GROUP BY T4.name, T0.name";
        
        String resultSQL = queryEngine.getResultSQL();
        
        System.out.println("Expected SQL:");
        System.out.println(expectedSQL);
        System.out.println("Result SQL:");
        System.out.println(resultSQL);
        assertEquals(expectedSQL, resultSQL);
    }    
}
