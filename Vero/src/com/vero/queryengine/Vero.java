/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.session.Session;
import com.vero.metadata.Attribute;
import com.vero.metadata.Metric;
import com.vero.metadata.JoinDefinition;
import com.vero.metadata.Expression;
import com.vero.metadata.Table;
import com.vero.testparser.TestParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.sql.generation.api.grammar.booleans.BooleanExpression;
import org.sql.generation.api.grammar.builders.query.ColumnsBuilder;
import org.sql.generation.api.grammar.builders.query.QuerySpecificationBuilder;
import org.sql.generation.api.grammar.builders.query.TableReferenceBuilder;
import org.sql.generation.api.vendor.SQLVendor;
import org.sql.generation.api.vendor.SQLVendorProvider;
import org.sql.generation.api.grammar.factories.BooleanFactory;
import org.sql.generation.api.grammar.factories.ColumnsFactory;
import org.sql.generation.api.grammar.factories.LiteralFactory;
import org.sql.generation.api.grammar.factories.QueryFactory;
import org.sql.generation.api.grammar.factories.TableReferenceFactory;
import org.sql.generation.api.grammar.query.ColumnReference;
import org.sql.generation.api.grammar.query.ColumnReferenceByName;
import org.sql.generation.api.grammar.query.QueryExpressionBody;

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
    
    public static void queryEngineTest3() {
        // session
        TestParser testParser = new TestParser("test3.json");
        Session userSession = testParser.parse();
                
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // queryEngineTest1();
        // queryEngineTest2();
        queryEngineTest3();
    }
}
