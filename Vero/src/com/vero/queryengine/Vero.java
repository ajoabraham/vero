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
    public static void main(String [] arg) {
        TestParser testParser = new TestParser("test6.json");
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);               
        
        String resultSQL = queryEngine.getResultSQL();

        System.out.println("Result SQL:");
        System.out.println(resultSQL);
    }
}
