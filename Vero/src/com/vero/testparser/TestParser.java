/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.testparser;

import com.vero.admin.DataSource;
import com.vero.metadata.Attribute;
import com.vero.metadata.Column;
import com.vero.metadata.Expression;
import com.vero.metadata.JoinDefinition;
import com.vero.metadata.Metric;
import com.vero.metadata.Table;
import com.vero.session.Session;
import java.io.File;
import java.io.FileReader;
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
public class TestParser {  
    private final File testFile;
    
    public TestParser(String fileName) { 
        testFile = new File(fileName);
    }
    
    public Session parse() {
        Session testSession = new Session();        
        FileReader jsonFileReader;
        DataSource testDS = null;
        
        try {
            jsonFileReader = new FileReader(testFile);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found...");
            return null;
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
                System.out.println("vendor:" + oneJSONDSObj.getJSONObject("database").getString("vendor"));
                
                //System.out.println("database:" + oneJSONDSObj.getString("database"));
                // add DS
                testSession.addDataSource(
                    oneJSONDSObj.getJSONObject("database").getString("vendor"), 
                    oneJSONDSObj.getString("name"),
                    oneJSONDSObj.getString("name"));
                testDS = testSession.getDataSource(oneJSONDSObj.getString("name"));
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
                System.out.println("rowCount:" + oneJSONTableObj.getInt("rowCount"));
                System.out.println("tableType:" + oneJSONTableObj.getString("tableType"));
                System.out.println("datasource:" + oneJSONTableObj.getString("datasource"));
                JSONArray jsonColumnsArray = oneJSONTableObj.getJSONArray("columns");
                // add table
                DataSource specificDS = testSession.getDataSource(oneJSONTableObj.getString("datasource"));
                Table aTable = null;
                if (specificDS != null) {
                    aTable = new Table(oneJSONTableObj.getString("name"), specificDS);
                    aTable.setRowCount(oneJSONTableObj.getInt("rowCount")); 
                    switch (oneJSONTableObj.getString("tableType")) {
                        case "dimension":
                            aTable.setLogicalType(Table.LogicalTableTypes.DIMENSION);
                            break;
                        case "bridge":
                            aTable.setLogicalType(Table.LogicalTableTypes.BRIDGE);
                            break;
                        case "fact":
                            aTable.setLogicalType(Table.LogicalTableTypes.FACT);
                            break;
                        case "aggregate":
                            aTable.setLogicalType(Table.LogicalTableTypes.AGGREGATE);
                            break;
                        default:
                            aTable.setLogicalType(Table.LogicalTableTypes.UNKNOWN);
                            break;
                    }

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
                    System.out.println("foreignKey:" + oneJSONColumnObj.getBoolean("foreignKey"));
                    
                    // add column
                    Column aColumn;
                    switch (oneJSONColumnObj.getString("type")) {
                        case "string":
                            aColumn = new Column(oneJSONColumnObj.getString("name"),"String", 10,
                                aTable);
                            break;
                        case "integer":
                            aColumn = new Column(oneJSONColumnObj.getString("name"),"Int", 10,
                                aTable);
                            break;                                        
                        default:
                            System.out.println("ERROR: type is not defined...");
                            aColumn = new Column();
                            break;
                    }
                    
                    if (oneJSONColumnObj.getBoolean("primaryKey") == true) {
                        aColumn.setKeyType(Column.KeyTypes.PRIMARY_KEY);
                    } else if (oneJSONColumnObj.getBoolean("foreignKey") == true) {
                        aColumn.setKeyType(Column.KeyTypes.FOREIGN_KEY);
                    } else {
                        aColumn.setKeyType(Column.KeyTypes.NO_KEY_TYPE);
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
                Attribute anAttr = new Attribute(oneJSONAttrObj.getString("name"), oneJSONAttrObj.getString("name"));
                testSession.addAttributeMeta(anAttr);
                
                int expressionsArraySize = jsonExpressionsArray.length();
                for (int j = 0; j < expressionsArraySize; j++) {
                    JSONObject oneJSONExpressionObj = jsonExpressionsArray.getJSONObject(j);
                    // expression
                    System.out.println("definition:" + oneJSONExpressionObj.getString("definition"));
                    JSONArray jsonTableUUIDsArray = oneJSONExpressionObj.getJSONArray("columns");
                    // add expression
                    Expression anExp = new Expression(oneJSONExpressionObj.getString("definition"));
                    anAttr.addExpression(anExp);

                    int tableUUIDsArraySize = jsonTableUUIDsArray.length();
                    for (int k = 0; k < tableUUIDsArraySize; k++) {
                        // table name
                        System.out.println("table's column:" + jsonTableUUIDsArray.getJSONArray(k).getString(0));
                        System.out.println("table's name:" + jsonTableUUIDsArray.getJSONArray(k).getString(1));

                        anExp.addTable(testDS.getTable(jsonTableUUIDsArray.getJSONArray(k).getString(1)));
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
                Metric aMetric = new Metric(oneJSONMetricObj.getString("name"), oneJSONMetricObj.getString("name"));
                testSession.addMetricMeta(aMetric);
                
                int expressionsArraySize = jsonExpressionsArray.length();
                for (int j = 0; j < expressionsArraySize; j++) {
                    JSONObject oneJSONExpressionObj = jsonExpressionsArray.getJSONObject(j);
                    // expression
                    System.out.println("definition:" + oneJSONExpressionObj.getString("definition"));
                    JSONArray jsonTableUUIDsArray = oneJSONExpressionObj.getJSONArray("columns");
                    // add expression
                    Expression anExp = new Expression(oneJSONExpressionObj.getString("definition"));
                    aMetric.addExpression(anExp);
                    
                    int tableUUIDsArraySize = jsonTableUUIDsArray.length();
                    for (int k = 0; k < tableUUIDsArraySize; k++) {
                        // table name                        
                        System.out.println("table's column:" + jsonTableUUIDsArray.getJSONArray(k).getString(0));
                        System.out.println("table's name:" + jsonTableUUIDsArray.getJSONArray(k).getString(1));

                        anExp.addTable(testDS.getTable(jsonTableUUIDsArray.getJSONArray(k).getString(1)));
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
                System.out.println("cleft column name:" + oneJSONJDObj.getString("cleft"));
                System.out.println("operator:" + oneJSONJDObj.getString("operator"));
                System.out.println("tright table name:" + oneJSONJDObj.getString("tright"));
                System.out.println("cright column name:" + oneJSONJDObj.getString("cright"));
                System.out.println("expression:" + oneJSONJDObj.getString("expression"));
                System.out.println("jointype:" + oneJSONJDObj.getString("jointype"));
                // add DS                
                JoinDefinition aJoin = new JoinDefinition(
                    oneJSONJDObj.getString("name"),
                    oneJSONJDObj.getString("tleft"),
                    oneJSONJDObj.getString("cleft"),
                    oneJSONJDObj.getString("operator"),
                    oneJSONJDObj.getString("tright"),
                    oneJSONJDObj.getString("cright"),
                    oneJSONJDObj.getString("expression"),
                    oneJSONJDObj.getString("jointype")
                );
                testSession.addJoinMeta(aJoin);
            }
            System.out.println("------------------------------");                                              
        } catch (JSONException e) {
            System.out.println("JSONException..." + e.toString());
        }        
                        
        return testSession;
    }    
}
