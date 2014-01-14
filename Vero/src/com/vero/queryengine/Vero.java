/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.session.Session;
import com.vero.testparser.TestParser;

/**
 *
 * @author yulinwen
 */
public class Vero {
    public static void queryEngineTest1() {
        // session
        TestParser testParser = new TestParser("test1.json");
        Session userSession = testParser.parse();
                
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
    }
    
    
    public static void queryEngineTest2() {
        // session
        TestParser testParser = new TestParser("test2.json");
        Session userSession = testParser.parse();
                
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
    } 
    public static void queryEngineTest4() {
        // session
        TestParser testParser = new TestParser("test4.json");
        Session userSession = testParser.parse();
                
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        queryEngineTest1();
        queryEngineTest2();
        queryEngineTest4();
    }
}
