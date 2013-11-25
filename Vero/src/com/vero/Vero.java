/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero;

import com.vero.session.Session;
import com.vero.metadata.AttributeMeta;
import com.vero.metadata.MetricMeta;
import com.vero.metadata.JoinMeta;
import com.vero.metadata.ExpressionMeta;
import com.vero.datasource.ColDataType;
import com.vero.datasource.DataSource;
import com.vero.datasource.Table;
import com.vero.datasource.Column;
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
import org.sql.generation.api.grammar.booleans.BooleanExpression;
import org.sql.generation.api.grammar.builders.query.ColumnsBuilder;
import org.sql.generation.api.grammar.builders.query.QuerySpecificationBuilder;
import org.sql.generation.api.grammar.builders.query.TableReferenceBuilder;
import org.sql.generation.api.grammar.common.SetQuantifier;
import org.sql.generation.api.vendor.MySQLVendor;
import org.sql.generation.api.vendor.SQLVendor;
import org.sql.generation.api.vendor.SQLVendorProvider;
import org.sql.generation.api.grammar.factories.BooleanFactory;
import org.sql.generation.api.grammar.factories.ColumnsFactory;
import org.sql.generation.api.grammar.factories.LiteralFactory;
import org.sql.generation.api.grammar.factories.QueryFactory;
import org.sql.generation.api.grammar.factories.TableReferenceFactory;
import org.sql.generation.api.grammar.query.ColumnReference;
import org.sql.generation.api.grammar.query.ColumnReferenceByName;
import org.sql.generation.api.grammar.query.Ordering;
import org.sql.generation.api.grammar.query.QueryExpression;
import org.sql.generation.api.grammar.query.QueryExpressionBody;

/**
 *
 * @author yulinwen
 */
public class Vero {
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
                System.out.println("cleft column name:" + oneJSONJDObj.getString("cleft"));
                System.out.println("operator:" + oneJSONJDObj.getString("operator"));
                System.out.println("tright table name:" + oneJSONJDObj.getString("tright"));
                System.out.println("cright column name:" + oneJSONJDObj.getString("cright"));
                System.out.println("expression:" + oneJSONJDObj.getString("expression"));
                System.out.println("jointype:" + oneJSONJDObj.getString("jointype"));
                // add DS                
                JoinMeta aJoin = new JoinMeta(
                    oneJSONJDObj.getString("name"),
                    oneJSONJDObj.getString("tleft"),
                    oneJSONJDObj.getString("cleft"),
                    oneJSONJDObj.getString("operator"),
                    oneJSONJDObj.getString("tright"),
                    oneJSONJDObj.getString("cright"),
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
        System.out.println("------------------------------");
        
        System.out.println("Dumping sql...");
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
        String sqlString2 = query.toString();
        System.out.println("Output sql is: " + sqlString2);
        */
        
        // @formatter:off
        /*
          SELECT t0.entity_identity
          FROM (SELECT DISTINCT t0.entity_pk, t0.entity_identity
            FROM qi4j.entities t0
            WHERE t0.entity_type_id IN (3, 4)
            EXCEPT
            SELECT DISTINCT t0.entity_pk, t0.entity_identity
            FROM qi4j.entities t0
            JOIN qi4j.qname_6 t1 ON (t0.entity_pk = t1.entity_pk AND t1.parent_qname IS NULL)
            JOIN qi4j.qname_11 t2 ON (t1.qname_id = t2.parent_qname AND t1.entity_pk = t2.entity_pk)
            LEFT JOIN qi4j.qname_12 t3 ON (t2.qname_id = t3.parent_qname AND t2.entity_pk = t3.entity_pk)
            LEFT JOIN qi4j.qname_13 t4 ON (t2.qname_id = t4.parent_qname AND t2.entity_pk = t4.entity_pk)
            WHERE t0.entity_type_id IN (3, 4)
            GROUP BY t0.entity_pk, t0.entity_identity
            HAVING COUNT(t2.qname_value) >= 2
            ORDER BY t0.entity_pk ASC
          ) AS t0
        */
        // @formatter:on

        QueryFactory q = vendor.getQueryFactory();
        BooleanFactory b = vendor.getBooleanFactory();
        TableReferenceFactory t = vendor.getTableReferenceFactory();
        LiteralFactory l = vendor.getLiteralFactory();
        ColumnsFactory c = vendor.getColumnsFactory();

        QuerySpecificationBuilder firstInnerQuery = q.querySpecificationBuilder();
        
        // construct select
        // get all expressions from all attributes
        ArrayList<ColumnReferenceByName> colRefByName = new ArrayList();
        HashMap attributes = userSession.getAttributes();
        Iterator it = attributes.entrySet().iterator();
        while (it.hasNext()) {            
            Map.Entry pairs = (Map.Entry)it.next();
            
            AttributeMeta anAttr = (AttributeMeta) pairs.getValue();
            ArrayList<ExpressionMeta> exps = anAttr.getExpressions();
            for (int i = 0; i < exps.size(); i++) {
                ColumnReferenceByName aColExp = c.colName(exps.get(i).getExpression());
                colRefByName.add(aColExp);
            }
        }
        ColumnReference[] colRefAttr = new ColumnReference[colRefByName.size()];
        colRefAttr = colRefByName.toArray(colRefAttr);                
        ColumnsBuilder innerSelectCols = q.columnsBuilder().addUnnamedColumns(colRefAttr);

        // get all expressions from all metrics
        colRefByName = new ArrayList();
        HashMap metrics = userSession.getMetrics();
        it = metrics.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            
            MetricMeta anMet = (MetricMeta) pairs.getValue();
            ArrayList<ExpressionMeta> exps = anMet.getExpressions();
            for (int i = 0; i < exps.size(); i++) {
                ColumnReferenceByName aColExp = c.colName(exps.get(i).getExpression());
                colRefByName.add(aColExp);
            }
        }
        ColumnReference[] colRefMet = new ColumnReference[colRefByName.size()];
        colRefMet = colRefByName.toArray(colRefMet);
        innerSelectCols.addUnnamedColumns(colRefMet);
        
        // construct groupby
        //QuerySpecificationBuilder specBuilder = q.querySpecificationBuilder();
        if ((colRefAttr.length > 0) && (colRefMet.length > 0)) {
            firstInnerQuery.getGroupBy().addGroupingElements(q.groupingElement(colRefAttr));
        }

        // construct join
        // ArrayList<String> selectedTables = new ArrayList();
        HashMap<String, String> tableAliases = new HashMap();
        HashMap<String, JoinMeta> joinDefs = userSession.getJoins();
        it = joinDefs.entrySet().iterator();
        int cnt = 0;
        int alias_cnt = 0;
        TableReferenceBuilder allJoins = null;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            JoinMeta aJoin = (JoinMeta) pairs.getValue();
            tableAliases.put(aJoin.getTLeft(), "T"+alias_cnt);
            alias_cnt++;
            tableAliases.put(aJoin.getTRight(), "T"+alias_cnt);
            alias_cnt++;
            // parsing joindef
            String jType = aJoin.getType();
            String jExp = aJoin.getExpression();
            String jOper = aJoin.getOperator();
            org.sql.generation.api.grammar.query.joins.JoinType jT;
            
            switch (jType) {
                case "inner":
                    jT = org.sql.generation.api.grammar.query.joins.JoinType.INNER;
                    break;
                case "outer":
                    jT = org.sql.generation.api.grammar.query.joins.JoinType.FULL_OUTER;
                    break;
                case "left":
                    jT = org.sql.generation.api.grammar.query.joins.JoinType.LEFT_OUTER;
                    break;
                case "right":
                    jT = org.sql.generation.api.grammar.query.joins.JoinType.RIGHT_OUTER;
                    break;
                default:
                    jT = org.sql.generation.api.grammar.query.joins.JoinType.INNER;
                    break;
            }
            
            if (cnt == 0) {
                allJoins = t.tableBuilder(t.table(t.tableName(null, aJoin.getTLeft()), t.tableAlias(tableAliases.get(aJoin.getTLeft()))));
            }

            BooleanExpression bE;
            switch (jOper) {
                case ">":
                    bE = b.gt(c.colName(tableAliases.get(aJoin.getTLeft()), aJoin.getCLeft()), c.colName(tableAliases.get(aJoin.getTRight()), aJoin.getCRight()));
                    break;
                case "=":
                    bE = b.eq(c.colName(tableAliases.get(aJoin.getTLeft()), aJoin.getCLeft()), c.colName(tableAliases.get(aJoin.getTRight()), aJoin.getCRight()));
                    break;
                case "<":
                    bE = b.lt(c.colName(tableAliases.get(aJoin.getTLeft()), aJoin.getCLeft()), c.colName(tableAliases.get(aJoin.getTRight()), aJoin.getCRight()));
                    break;
                default:
                    bE = b.eq(c.colName(tableAliases.get(aJoin.getTLeft()), aJoin.getCLeft()), c.colName(tableAliases.get(aJoin.getTRight()), aJoin.getCRight()));
                    break;
            }                                    
                        
            allJoins.addQualifiedJoin(
                jT,
                t.table(t.tableName(null, aJoin.getTRight()), t.tableAlias(tableAliases.get(aJoin.getTRight()))),
                t.jc(b.booleanBuilder(bE).createExpression()));
        }
        firstInnerQuery.setSelect(innerSelectCols);                              
        firstInnerQuery.getFrom().addTableReferences(allJoins);
        //firstInnerQuery.getWhere().reset( where );

        QueryExpressionBody innerQuery = q.queryBuilder(firstInnerQuery.createExpression()).createExpression();

        String sqlString = innerQuery.toString();
        System.out.println("Output sql is: " + sqlString);       
    }    
}
