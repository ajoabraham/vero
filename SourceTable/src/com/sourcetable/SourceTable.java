/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sourcetable;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author yulinwen
 */
public class SourceTable {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
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
            
            JSONArray jsonTablesArray = root.getJSONArray("tables");
            int tablesArraySize = jsonTablesArray.length();
            ArrayList<JSONObject> arrays = new ArrayList();
            for (int i = 0; i < tablesArraySize; i++) {
                JSONObject oneJSONTableObj = jsonTablesArray.getJSONObject(i);
                System.out.println("json table object " + i + ": ");
                // table
                System.out.println("uuid:" + oneJSONTableObj.getInt("uuid"));
                System.out.println("name:" + oneJSONTableObj.getString("name"));
                System.out.println("datasource:" + oneJSONTableObj.getInt("datasource"));
                JSONArray jsonColumnsArray = oneJSONTableObj.getJSONArray("columns");
               
                int columnsArraySize = jsonColumnsArray.length();
                for (int j = 0; j < columnsArraySize; j++) {
                    JSONObject oneJSONColumnObj = jsonColumnsArray.getJSONObject(j);
                    // column
                    // System.out.println("json column object " + j + ": " + oneJSONColumnObj.toString());
                    System.out.println("uuid:" + oneJSONColumnObj.getInt("uuid"));
                    System.out.println("name:" + oneJSONColumnObj.getString("name"));
                    System.out.println("type:" + oneJSONColumnObj.getString("type"));
                    System.out.println("primaryKey:" + oneJSONColumnObj.getBoolean("primaryKey"));
                }
                
                arrays.add(oneJSONTableObj);
            }
            
            // finally
            JSONObject[] jsons = new JSONObject[arrays.size()];
            arrays.toArray(jsons);
                        
        } catch (JSONException e) {
            System.out.println("JSONException..." + e.toString());
        }       
    }    
}
