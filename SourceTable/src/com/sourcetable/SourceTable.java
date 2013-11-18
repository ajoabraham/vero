/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sourcetable;

import com.sourcetable.datasource.*;
import com.sourcetable.metadata.*;
import com.sourcetable.session.*;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.sql.generation.api.vendor.MySQLVendor;
import org.sql.generation.api.vendor.SQLVendor;
import org.sql.generation.api.vendor.SQLVendorProvider;
import org.sql.generation.api.grammar.factories.BooleanFactory;
import org.sql.generation.api.grammar.factories.ColumnsFactory;
import org.sql.generation.api.grammar.factories.LiteralFactory;
import org.sql.generation.api.grammar.factories.TableReferenceFactory;
import org.sql.generation.api.grammar.query.QueryExpression;

/**
 *
 * @author yulinwen
 */
public class SourceTable {
    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            // it.remove(); // avoids a ConcurrentModificationException
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // session
        Session userSession = new Session();
        
        // only support single datasource, will be set later
        DataSource userDS = null;
        
        // parsing the json file
        File jsonFile = new File("test1.json");
        FileReader jsonFileReader;
        
        try {
            jsonFileReader = new FileReader(jsonFile);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found...");
            return;
        }
        
        try {
            JSONTokener tokener = new JSONTokener(jsonFileReader);
            JSONObject root = new JSONObject(tokener);
            
            // parsing datasources
            JSONArray jsonDSsArray = root.getJSONArray("datasources");
            int DSsArraySize = jsonDSsArray.length();

            for (int i = 0; i < DSsArraySize; i++) {
                JSONObject oneJSONDSObj = jsonDSsArray.getJSONObject(i);
                System.out.println("json DS object " + i + ": ");
                // DS
                System.out.println("name:" + oneJSONDSObj.getString("name"));
                System.out.println("type:" + oneJSONDSObj.getString("type"));
                // add DS
                userSession.addDataSource(
                    oneJSONDSObj.getString("type"), 
                    oneJSONDSObj.getString("name"),
                    oneJSONDSObj.getString("name"));
                userDS = userSession.getDataSource(oneJSONDSObj.getString("name"));
            }
            System.out.println("------------------------------");
            
            // parsing tables
            JSONArray jsonTablesArray = root.getJSONArray("tables");
            int tablesArraySize = jsonTablesArray.length();

            for (int i = 0; i < tablesArraySize; i++) {
                JSONObject oneJSONTableObj = jsonTablesArray.getJSONObject(i);
                System.out.println("json table object " + i + ": ");
                // table
                System.out.println("name:" + oneJSONTableObj.getString("name"));
                System.out.println("datasource:" + oneJSONTableObj.getString("datasource"));
                JSONArray jsonColumnsArray = oneJSONTableObj.getJSONArray("columns");
                // add table
                DataSource specificDS = userSession.getDataSource(oneJSONTableObj.getString("datasource"));
                Table aTable = null;
                if (specificDS != null) {
                    aTable = new Table(oneJSONTableObj.getString("name"), specificDS);
                    specificDS.addTable(aTable);
                } else {
                    System.out.println("WARNING: Can't find specificDS...");
                }
               
                int columnsArraySize = jsonColumnsArray.length();
                for (int j = 0; j < columnsArraySize; j++) {
                    JSONObject oneJSONColumnObj = jsonColumnsArray.getJSONObject(j);
                    // column
                    // System.out.println("json column object " + j + ": " + oneJSONColumnObj.toString());
                    System.out.println("name:" + oneJSONColumnObj.getString("name"));
                    System.out.println("type:" + oneJSONColumnObj.getString("type"));
                    System.out.println("primaryKey:" + oneJSONColumnObj.getBoolean("primaryKey"));
                    
                    // add column
                    Column aColumn;
                    if (oneJSONColumnObj.getString("type").equals("string")) {
                        aColumn = new Column(oneJSONColumnObj.getString("name"),ColDataType.STRING, 
                            oneJSONColumnObj.getBoolean("primaryKey"), aTable);
                    } else if (oneJSONColumnObj.getString("type").equals("integer")) {
                        aColumn = new Column(oneJSONColumnObj.getString("name"),ColDataType.INTEGER, 
                            oneJSONColumnObj.getBoolean("primaryKey"), aTable);
                    } else {
                        System.out.println("ERROR: type is not defined...");
                    }                                        
                }               
            }
            System.out.println("------------------------------");
            
            // parsing attributes
            JSONArray jsonAttrsArray = root.getJSONArray("attributes");
            int attrsArraySize = jsonAttrsArray.length();

            for (int i = 0; i < attrsArraySize; i++) {
                JSONObject oneJSONAttrObj = jsonAttrsArray.getJSONObject(i);
                System.out.println("json attribute object " + i + ": ");
                // attribute
                System.out.println("name:" + oneJSONAttrObj.getString("name"));
                JSONArray jsonExpressionsArray = oneJSONAttrObj.getJSONArray("expressions");
                // add attribute
                AttributeMeta anAttr = new AttributeMeta(oneJSONAttrObj.getString("name"), oneJSONAttrObj.getString("name"));
                userSession.addAttributeMeta(anAttr);
                
                int expressionsArraySize = jsonExpressionsArray.length();
                for (int j = 0; j < expressionsArraySize; j++) {
                    JSONObject oneJSONExpressionObj = jsonExpressionsArray.getJSONObject(j);
                    // expression
                    System.out.println("value:" + oneJSONExpressionObj.getString("value"));
                    JSONArray jsonTableUUIDsArray = oneJSONExpressionObj.getJSONArray("tables");
                    // add expression
                    ExpressionMeta anExp = new ExpressionMeta(oneJSONExpressionObj.getString("value"));
                    anAttr.addExpression(anExp);

                    int tableUUIDsArraySize = jsonTableUUIDsArray.length();
                    for (int k = 0; k < tableUUIDsArraySize; k++) {
                        // table name
                        System.out.println("table's name:" + jsonTableUUIDsArray.getString(k));
                        anExp.addTable(userDS.getTable(jsonTableUUIDsArray.getString(k)));
                    }                    
                }
            }
            System.out.println("------------------------------");

            // parsing metrics
            JSONArray jsonMetricsArray = root.getJSONArray("metrics");
            int metricsArraySize = jsonMetricsArray.length();

            for (int i = 0; i < metricsArraySize; i++) {
                JSONObject oneJSONMetricObj = jsonMetricsArray.getJSONObject(i);
                System.out.println("json metric object " + i + ": ");
                // metric
                System.out.println("name:" + oneJSONMetricObj.getString("name"));
                JSONArray jsonExpressionsArray = oneJSONMetricObj.getJSONArray("expressions");
                // add metric
                MetricMeta aMetric = new MetricMeta(oneJSONMetricObj.getString("name"), oneJSONMetricObj.getString("name"));
                userSession.addMetricMeta(aMetric);
                
                int expressionsArraySize = jsonExpressionsArray.length();
                for (int j = 0; j < expressionsArraySize; j++) {
                    JSONObject oneJSONExpressionObj = jsonExpressionsArray.getJSONObject(j);
                    // expression
                    System.out.println("value:" + oneJSONExpressionObj.getString("value"));
                    JSONArray jsonTableUUIDsArray = oneJSONExpressionObj.getJSONArray("tables");
                    // add expression
                    ExpressionMeta anExp = new ExpressionMeta(oneJSONExpressionObj.getString("value"));
                    aMetric.addExpression(anExp);
                    
                    int tableUUIDsArraySize = jsonTableUUIDsArray.length();
                    for (int k = 0; k < tableUUIDsArraySize; k++) {
                        // table name
                        System.out.println("table's name:" + jsonTableUUIDsArray.getString(k));
                        anExp.addTable(userDS.getTable(jsonTableUUIDsArray.getString(k)));
                    }                    
                }
            }
            System.out.println("------------------------------");
            
            // parsing joindefs
            JSONArray jsonJDsArray = root.getJSONArray("joindefs");
            int JDsArraySize = jsonJDsArray.length();

            for (int i = 0; i < JDsArraySize; i++) {
                JSONObject oneJSONJDObj = jsonJDsArray.getJSONObject(i);
                System.out.println("json JD object " + i + ": ");
                // DS
                System.out.println("name:" + oneJSONJDObj.getString("name"));
                System.out.println("tleft table name:" + oneJSONJDObj.getString("tleft"));
                System.out.println("tright table name:" + oneJSONJDObj.getString("tright"));
                System.out.println("expression:" + oneJSONJDObj.getString("expression"));
                System.out.println("jointype:" + oneJSONJDObj.getString("jointype"));
                // add DS                
                JoinMeta aJoin = new JoinMeta(
                    oneJSONJDObj.getString("name"),
                    oneJSONJDObj.getString("tleft"),
                    oneJSONJDObj.getString("tright"),
                    oneJSONJDObj.getString("expression"),
                    oneJSONJDObj.getString("jointype")
                );
                userSession.addJoinMeta(aJoin);
            }
            System.out.println("------------------------------");                                              
        } catch (JSONException e) {
            System.out.println("JSONException..." + e.toString());
        }
        
        // dump session
        System.out.println("Dumping session...");
        HashMap ds = userSession.getAllDataSources();
        printMap(ds);
        
        DataSource specificDS = userSession.getDataSource("Teradata - Prod");
        if (specificDS != null) {
            System.out.println("Got a Teradata - Prod - " + specificDS.toString());
        } else {
            System.out.println("Not found...");
        }
        
        // start generating sql
        // Create or acquire vendor
        SQLVendor vendor = null;
        try {
            vendor = SQLVendorProvider.createVendor(SQLVendor.class);
        } catch (java.io.IOException e) {
            System.out.println("Exception: " + e);
        }

        /*
          Creating query:
          SELECT value
          FROM table
          WHERE table.value = 5
          ORDER BY 1 ASC
        */
        
        BooleanFactory b = vendor.getBooleanFactory();
        ColumnsFactory c = vendor.getColumnsFactory();
        LiteralFactory l = vendor.getLiteralFactory();
        TableReferenceFactory t = vendor.getTableReferenceFactory();

        QueryExpression query = vendor.getQueryFactory().simpleQueryBuilder()
          .select( "value" )
          .from( t.tableName( "table" ) )
          .where( b.eq( c.colName( "table", "value" ), l.n(5) ) )
          .orderByAsc( "1" )
          .createExpression();

        // The following two statements produce equivalent SQL statement string
        // String sqlString = vendor.toString( query );
        String sqlStirng2 = query.toString( );
        System.out.println("Output sql is: " + sqlString2);
    }    
}
