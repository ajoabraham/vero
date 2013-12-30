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
import org.jgrapht.alg.*;

/**
 *
 * @author yulinwen
 */
public class Vero {    
    public static void queryEngineOldTest1() {
        // session
        Session userSession;
        TestParser testParser = new TestParser("test1.json");        
        userSession = testParser.parse();                
        
        System.out.println("Dumping sql...");
        // start generating sql
        // Create or acquire vendor
        SQLVendor vendor = null;
        try {
            vendor = SQLVendorProvider.createVendor(SQLVendor.class);
        } catch (java.io.IOException e) {
            System.out.println("Exception: " + e);
        }
        
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
        HashMap<String, String> tableAliases = new HashMap();
        int alias_cnt = 0;                       
        
        // construct select
        // get all expressions from all attributes
        ArrayList<ColumnReferenceByName> colRefByName = new ArrayList();
        HashMap attributes = userSession.getAttributes();
        Iterator it = attributes.entrySet().iterator();
        while (it.hasNext()) {            
            Map.Entry pairs = (Map.Entry)it.next();
            
            Attribute anAttr = (Attribute) pairs.getValue();
            ArrayList<Expression> exps = anAttr.getExpressions();
            String colTableRep = "";
            for (int i = 0; i < exps.size(); i++) {                
                ArrayList<Table> expTables = exps.get(i).getTables();
                for (int j =0; j < expTables.size(); j++) {
                    if (j==0) colTableRep = expTables.get(j).getObjectName();
                    if (!tableAliases.containsKey(expTables.get(j).getObjectName())) {
                        tableAliases.put(expTables.get(j).getObjectName(), "T"+alias_cnt);
                        alias_cnt++;
                    }
                }
                
                ColumnReferenceByName aColExp = c.colName(tableAliases.get(colTableRep), exps.get(i).getExpression());
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
            
            Metric anMet = (Metric) pairs.getValue();
            ArrayList<Expression> exps = anMet.getExpressions();
            String colTableRep = "";
            for (int i = 0; i < exps.size(); i++) {
                ArrayList<Table> expTables = exps.get(i).getTables();
                for (int j =0; j < expTables.size(); j++) {
                    if (j==0) colTableRep = expTables.get(j).getObjectName();
                    if (!tableAliases.containsKey(expTables.get(j).getObjectName())) {
                        tableAliases.put(expTables.get(j).getObjectName(), "T"+alias_cnt);
                        alias_cnt++;
                    }
                }
                
                ColumnReferenceByName aColExp = c.colName(tableAliases.get(colTableRep), exps.get(i).getExpression());
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
        HashMap<String, JoinDefinition> joinDefs = userSession.getJoins();
        it = joinDefs.entrySet().iterator();
        int cnt = 0;
        TableReferenceBuilder allJoins = null;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            JoinDefinition aJoin = (JoinDefinition) pairs.getValue();
            
            if (!tableAliases.containsKey(aJoin.getTLeft())) {
                tableAliases.put(aJoin.getTLeft(), "T"+alias_cnt);
                alias_cnt++;
            }
            
            if (!tableAliases.containsKey(aJoin.getTRight())) {
                tableAliases.put(aJoin.getTRight(), "T"+alias_cnt);
                alias_cnt++;
            }
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
