/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sourcetable;

import java.io.File;
import java.io.FileReader;
import java.net.URI;
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
            e.printStackTrace();
            return;
        }
        
        try {
            JSONTokener tokener = new JSONTokener(jsonFileReader);
            JSONObject root = new JSONObject(tokener);
            
            JSONArray jsonArray = root.getJSONArray("tables");
            int size = jsonArray.length();
            ArrayList<JSONObject> arrays = new ArrayList();
            for (int i = 0; i < size; i++) {
                JSONObject another_json_object = jsonArray.getJSONObject(i);
                System.out.println("json object: " + another_json_object.toString());
                //Blah blah blah...
                arrays.add(another_json_object);
            }
            
            // finally
            JSONObject[] jsons = new JSONObject[arrays.size()];
            arrays.toArray(jsons);
                        
        } catch (JSONException e) {
            e.printStackTrace();
        }       
    }    
}
