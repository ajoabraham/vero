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
import com.vero.metadata.ParameterType;
import com.vero.metadata.Table;
import com.vero.session.Session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

/**
 *
 * @author yulinwen
 */
public class TestParser {
    private static enum Type {
        TYPE_ATTRIBUTE,
        TYPE_METRIC,
        TYPE_HH_WHITE,
        TYPE_HH_BLACK
    }
    
    private File testFile;
    private JSONStringer jsonStringer = null;
    
    public TestParser(String fileName) { 
        testFile = new File(fileName);
    }
    
    public TestParser(JSONStringer jsonStringer) {
        this.jsonStringer = jsonStringer;
    }
    
    public Session parse() {
        Session testSession = new Session();        
        BufferedReader jsonFileReader;
        DataSource testDS = null;
        
        try {
            if (jsonStringer != null) {
                jsonFileReader = new BufferedReader(new StringReader(jsonStringer.toString()));
            }
            else {
                jsonFileReader = new BufferedReader(new FileReader(testFile));
            }
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
                //System.out.println("json DS object " + i + ": ");
                // DS
                //System.out.println("name:" + oneJSONDSObj.getString("name"));
                //System.out.println("vendor:" + oneJSONDSObj.getJSONObject("database").getString("vendor"));

                UUID dsUUID = getUUID(oneJSONDSObj);
                // add DS
                testSession.addDataSource(
                    dsUUID,
                    oneJSONDSObj.getJSONObject("database").getString("vendor"), 
                    oneJSONDSObj.getString("name"),
                    oneJSONDSObj.getString("name"));
                testDS = testSession.getDataSource(oneJSONDSObj.getString("name"));
            }
            
            // parsing tables
            JSONArray jsonTablesArray = root.getJSONArray("tables");
            int tablesArraySize = jsonTablesArray.length();

            for (int i = 0; i < tablesArraySize; i++) {
                JSONObject oneJSONTableObj = jsonTablesArray.getJSONObject(i);
                System.out.println("json table object " + i + ": ");
                // table
                System.out.println("name:" + oneJSONTableObj.getString("name"));
                //System.out.println("rowCount:" + oneJSONTableObj.getInt("rowCount"));
                //System.out.println("tableType:" + oneJSONTableObj.getString("tableType"));
                //System.out.println("datasource:" + oneJSONTableObj.getString("datasource"));
                JSONArray jsonColumnsArray = oneJSONTableObj.getJSONArray("columns");
                
                UUID tabUUID = getUUID(oneJSONTableObj);
                // add table
                DataSource specificDS = testSession.getDataSource(oneJSONTableObj.getString("datasource"));
                Table aTable = null;
                if (specificDS != null) {
                    aTable = new Table(tabUUID, oneJSONTableObj.getString("name"), specificDS);
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

                    testSession.addTable(aTable);
                    specificDS.addTable(aTable);
                } else {
                    System.out.println("WARNING: Can't find specificDS...");
                }
               
                int columnsArraySize = jsonColumnsArray.length();
                for (int j = 0; j < columnsArraySize; j++) {
                    JSONObject oneJSONColumnObj = jsonColumnsArray.getJSONObject(j);
                    // column
                    //System.out.println("json column object " + j + ": " + oneJSONColumnObj.toString());
                    //System.out.println("name:" + oneJSONColumnObj.getString("name"));
                    //System.out.println("type:" + oneJSONColumnObj.getString("type"));
                    //System.out.println("primaryKey:" + oneJSONColumnObj.getBoolean("primaryKey"));
                    //System.out.println("foreignKey:" + oneJSONColumnObj.getBoolean("foreignKey"));
                    
                    // add column
                    Column aColumn;

                    UUID colUUID = getUUID(oneJSONColumnObj);
                    String dataType = oneJSONColumnObj.getString("type");
                    aColumn = new Column(colUUID, oneJSONColumnObj.getString("name"), dataType, 10, aTable);
                    
                    if (oneJSONColumnObj.getBoolean("primaryKey") == true) {
                        aColumn.setKeyType(Column.KeyTypes.PRIMARY_KEY);
                    } else if (oneJSONColumnObj.getBoolean("foreignKey") == true) {
                        aColumn.setKeyType(Column.KeyTypes.FOREIGN_KEY);
                    } else {
                        aColumn.setKeyType(Column.KeyTypes.NO_KEY_TYPE);
                    }
                    
                    aTable.addColumn(aColumn);
                }        
            }
            //System.out.println("------------------------------");
            
            // parsing attributes
            if (root.isNull("attributes") == false) {
                JSONArray jsonAttrsArray = root.getJSONArray("attributes");
                int attrsArraySize = jsonAttrsArray.length();

                for (int i = 0; i < attrsArraySize; i++) {
                    JSONObject oneJSONAttrObj = jsonAttrsArray.getJSONObject(i);
                    System.out.println("json attribute object " + i + ": ");
                    // attribute
                    System.out.println("name:" + oneJSONAttrObj.getString("name"));
                                        
                    UUID attrUUID = getUUID(oneJSONAttrObj);
                    // add attribute
                    Attribute anAttr = new Attribute(attrUUID, oneJSONAttrObj.getString("name"));
                    testSession.addAttribute(anAttr);

                    parseHardhints(oneJSONAttrObj, anAttr, null, null);
                    parseExpressions(oneJSONAttrObj, testDS, TestParser.Type.TYPE_ATTRIBUTE, anAttr);
                }
                System.out.println("------------------------------");
            }

            // parsing metrics
            if (root.isNull("metrics") == false) {
                JSONArray jsonMetricsArray = root.getJSONArray("metrics");
                int metricsArraySize = jsonMetricsArray.length();

                for (int i = 0; i < metricsArraySize; i++) {
                    JSONObject oneJSONMetricObj = jsonMetricsArray.getJSONObject(i);
                    System.out.println("json metric object " + i + ": ");
                    // metric
                    System.out.println("name:" + oneJSONMetricObj.getString("name"));
                    JSONArray jsonExpressionsArray = oneJSONMetricObj.getJSONArray("expressions");
                    
                    UUID metUUID = getUUID(oneJSONMetricObj);
                    // add metric
                    Metric aMetric = new Metric(metUUID, oneJSONMetricObj.getString("name"));
                    testSession.addMetric(aMetric);

                    parseHardhints(oneJSONMetricObj, null, aMetric, null);
                    parseExpressions(oneJSONMetricObj, testDS, TestParser.Type.TYPE_METRIC, aMetric);
                }
                System.out.println("------------------------------");
            }
                        
            // parsing joindefs
            if (root.isNull("joindefs") == false) {
                JSONArray jsonJDsArray = root.getJSONArray("joindefs");
                int JDsArraySize = jsonJDsArray.length();

                for (int i = 0; i < JDsArraySize; i++) {
                    JSONObject oneJSONJDObj = jsonJDsArray.getJSONObject(i);
                    String tleft = oneJSONJDObj.getString("tleft");
                    String tright = oneJSONJDObj.getString("tright");                   
                    String jtype = oneJSONJDObj.getString("jointype");
                    String expression;
                    if (oneJSONJDObj.isNull("expression")) {
                        expression = null;
                    } else {
                        expression = oneJSONJDObj.getString("expression");
                    }
                    
                    System.out.println("json JD object " + i + ": ");
                    // DS
                    System.out.println("name:" + oneJSONJDObj.getString("name"));
                    System.out.println("tleft table name:" + tleft);
                    System.out.println("tright table name:" + tright);
                    System.out.println("expression:" + expression);
                    System.out.println("jointype:" + jtype);
                    
                    UUID jdUUID = getUUID(oneJSONJDObj);
                    JoinDefinition.JoinType joinType;
                    
                    switch (jtype) {
                        case "inner":
                            joinType = JoinDefinition.JoinType.INNER;
                            break;
                        case "outer":
                            joinType = JoinDefinition.JoinType.OUTER;
                            break;
                        case "left":
                            joinType = JoinDefinition.JoinType.LEFT;
                            break;
                        case "right":
                            joinType = JoinDefinition.JoinType.RIGHT;
                            break;
                        case "cross":
                            joinType = JoinDefinition.JoinType.CROSS;
                            break;
                        default:
                            joinType = JoinDefinition.JoinType.NONE;
                            break;                                    
                    }
                    
                    // make sure tleft and tright is there
                    Table tabLeft = testDS.getTable(tleft);
                    Table tabRight = testDS.getTable(tright);
                    
                    if ((tabLeft != null ) && (tabRight != null)) {                    
                        JoinDefinition aJoin = new JoinDefinition(
                            jdUUID,
                            jdUUID.toString(),
                            oneJSONJDObj.getString("name"),
                            tleft,
                            tabLeft.getUUID().toString(),
                            tright,
                            tabRight.getUUID().toString(),
                            expression,
                            joinType
                        );
                        testSession.addJoindef(aJoin);
                    }
                }
                System.out.println("------------------------------");
            }
            
            parseHardhints(root, null, null, testSession);
        } catch (JSONException e) {
            System.out.println("JSONException..." + e.toString());
            // System.exit(0);
        }
        
        return testSession;
    }
    
    private void parseHardhints(JSONObject root, Attribute attr, Metric met, Session sess) {
        JSONArray jsonHHsArray;
        int HHsArraySize;        
        TestParser.Type[] blackWhite = {TestParser.Type.TYPE_HH_WHITE, TestParser.Type.TYPE_HH_BLACK};
        
        for (TestParser.Type type : blackWhite) {
            switch (type) {
                case TYPE_HH_WHITE:
                    if (root.isNull("hardhints_white") == false) {                
                        jsonHHsArray = root.getJSONArray("hardhints_white");
                        HHsArraySize = jsonHHsArray.length();
                        for (int i = 0; i < HHsArraySize; i++) {
                            String hhName = jsonHHsArray.getString(i);
                            System.out.println("json whh object " + i + ": " + hhName);
                            if (attr != null) {
                                attr.addWhiteHardhint(hhName);
                            } else if (met != null) {
                                met.addWhiteHardhint(hhName);
                            } else {
                                sess.addWhiteHardhint(hhName);
                            }
                        }
                    }
                    break;
                case TYPE_HH_BLACK:
                    if (root.isNull("hardhints_black") == false) {
                        jsonHHsArray = root.getJSONArray("hardhints_black");
                        HHsArraySize = jsonHHsArray.length();
                        for (int i = 0; i < HHsArraySize; i++) {
                            String hhName = jsonHHsArray.getString(i);
                            System.out.println("json whh object " + i + ": " + hhName);
                            if (attr != null) {
                                attr.addBlackHardhint(hhName);
                            } else if (met != null) {
                                met.addBlackHardhint(hhName);
                            } else {
                                sess.addBlackHardhint(hhName);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
    
    private void parseExpressions(JSONObject root, DataSource datasource, TestParser.Type type, Object obj) {
        JSONArray expressionsArray = root.getJSONArray("expressions");
        int expressionsArraySize = expressionsArray.length();
        for (int i = 0; i < expressionsArraySize; i++) {
            JSONObject expressionObj = expressionsArray.getJSONObject(i);
            UUID expUUID = getUUID(expressionObj);
            // add expression            
            Expression anExp = new Expression(expUUID, expressionObj.getString("definition"));
            
            if (type == TestParser.Type.TYPE_ATTRIBUTE) {
                ((Attribute)obj).addExpression(anExp);
            } else {
                ((Metric)obj).addExpression(anExp);
            }

            parseParameters(expressionObj, anExp);
            
            JSONArray columnsArray = expressionObj.getJSONArray("columns");
            int columnsArraySize = columnsArray.length();
            for (int j = 0; j < columnsArraySize; j++) {
                String colName = columnsArray.getJSONArray(j).getString(0);
                String tabName = columnsArray.getJSONArray(j).getString(1);
                Table curTab = datasource.getTable(tabName);

                //System.out.println("table's column:" + colName);
                //System.out.println("table's name:" + tabName);

                if (curTab != null) {
                    Column aCol = curTab.getColumn(colName);

                    if (aCol != null) {
                        anExp.addColumn(aCol);
                    } else {
                        System.out.println("can't find column...:" + colName);
                    }
                } else {
                    System.out.println("can't find table...:" + tabName);
                }
            }                
        }
    }
    
    private void parseParameters(JSONObject root, Expression expression) {
        if (root.isNull("parameters") == false) {
            JSONArray paramArray = root.getJSONArray("parameters");
            int paramArraySize = paramArray.length();
            for (int i = 0; i < paramArraySize; i++) {
                JSONObject paramObj = paramArray.getJSONObject(i);
                if (paramObj.isNull("distinct") == false) {
                    //System.out.println("Got a distinct object = " + paramObj.getString("distinct"));
                    expression.addParameter(ParameterType.PARAMTYPE_DISTINCT, paramObj.getString("distinct"));
                }

                if (paramObj.isNull("partition_by") == false) {
                    //System.out.println("Got a partition by object = " + paramObj.getString("partition_by"));
                    expression.addParameter(ParameterType.PARAMTYPE_PARTITION_BY, paramObj.getString("partition_by"));
                }

                if (paramObj.isNull("order_by") == false) {
                    //System.out.println("Got a order by object = " + paramObj.getString("order_by"));
                    expression.addParameter(ParameterType.PARAMTYPE_ORDER_BY, paramObj.getString("order_by"));
                }                                
            }
        }
    }        
    
    private UUID getUUID(JSONObject root) {
        UUID uuid;
        if (root.isNull("uuid") == false) {
            String strUUID = root.getString("uuid");

            try {
                uuid = UUID.fromString(strUUID);
            } catch (Exception e) {
                uuid = UUID.randomUUID();
            }
        } else {
            uuid = UUID.randomUUID();
        }
        
        return uuid;
    }
}
