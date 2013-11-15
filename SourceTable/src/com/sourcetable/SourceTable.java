/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sourcetable;

import com.sourcetable.session.*;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // session
        Session userSession = new Session();
        
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
            }
            System.out.println("------------------------------");
            
            // parsing tables
            JSONArray jsonTablesArray = root.getJSONArray("tables");
            int tablesArraySize = jsonTablesArray.length();
            ArrayList<JSONObject> arrays = new ArrayList();
            for (int i = 0; i < tablesArraySize; i++) {
                JSONObject oneJSONTableObj = jsonTablesArray.getJSONObject(i);
                System.out.println("json table object " + i + ": ");
                // table
                System.out.println("name:" + oneJSONTableObj.getString("name"));
                System.out.println("datasource:" + oneJSONTableObj.getString("datasource"));
                JSONArray jsonColumnsArray = oneJSONTableObj.getJSONArray("columns");
               
                int columnsArraySize = jsonColumnsArray.length();
                for (int j = 0; j < columnsArraySize; j++) {
                    JSONObject oneJSONColumnObj = jsonColumnsArray.getJSONObject(j);
                    // column
                    // System.out.println("json column object " + j + ": " + oneJSONColumnObj.toString());
                    System.out.println("name:" + oneJSONColumnObj.getString("name"));
                    System.out.println("type:" + oneJSONColumnObj.getString("type"));
                    System.out.println("primaryKey:" + oneJSONColumnObj.getBoolean("primaryKey"));
                }
                
                arrays.add(oneJSONTableObj);
            }
            System.out.println("------------------------------");
            
            // parsing attributes
            JSONArray jsonAttrsArray = root.getJSONArray("attributes");
            int attrsArraySize = jsonAttrsArray.length();

            for (int i = 0; i < attrsArraySize; i++) {
                JSONObject oneJSONAttrObj = jsonAttrsArray.getJSONObject(i);
                System.out.println("json attribute object " + i + ": ");
                // table
                System.out.println("name:" + oneJSONAttrObj.getString("name"));
                JSONArray jsonExpressionsArray = oneJSONAttrObj.getJSONArray("expressions");
               
                int expressionsArraySize = jsonExpressionsArray.length();
                for (int j = 0; j < expressionsArraySize; j++) {
                    JSONObject oneJSONExpressionObj = jsonExpressionsArray.getJSONObject(j);
                    // expression
                    System.out.println("value:" + oneJSONExpressionObj.getString("value"));
                    JSONArray jsonTableUUIDsArray = oneJSONExpressionObj.getJSONArray("tables");

                    int tableUUIDsArraySize = jsonTableUUIDsArray.length();
                    for (int k = 0; k < tableUUIDsArraySize; k++) {
                        // table UUID
                        System.out.println("table's name:" + jsonTableUUIDsArray.getString(k));
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
                // table
                System.out.println("name:" + oneJSONMetricObj.getString("name"));
                JSONArray jsonExpressionsArray = oneJSONMetricObj.getJSONArray("expressions");
               
                int expressionsArraySize = jsonExpressionsArray.length();
                for (int j = 0; j < expressionsArraySize; j++) {
                    JSONObject oneJSONExpressionObj = jsonExpressionsArray.getJSONObject(j);
                    // expression
                    System.out.println("value:" + oneJSONExpressionObj.getString("value"));
                    JSONArray jsonTableUUIDsArray = oneJSONExpressionObj.getJSONArray("tables");

                    int tableUUIDsArraySize = jsonTableUUIDsArray.length();
                    for (int k = 0; k < tableUUIDsArraySize; k++) {
                        // table UUID
                        System.out.println("table's name:" + jsonTableUUIDsArray.getString(k));
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
            }
            System.out.println("------------------------------");
            
            // finally
            JSONObject[] jsons = new JSONObject[arrays.size()];
            arrays.toArray(jsons);                                    
        } catch (JSONException e) {
            System.out.println("JSONException..." + e.toString());
        }
        
        // dump session
        System.out.println("Dumping session...");
        HashMap ds = userSession.getDataSources();
        printMap(ds);
        
    }    
}
