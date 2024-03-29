/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.ExpressionUnit;
import static com.google.common.collect.ImmutableMap.of;
import com.vero.metadata.Attribute;
import com.vero.metadata.Column;
import com.vero.metadata.Expression;
import com.vero.metadata.JoinDefinition;
import com.vero.metadata.Metric;
import static com.vero.metadata.ParameterType.*;
import com.vero.metadata.Table;
import com.vero.report.Block;
import com.vero.report.Report;
import com.vero.session.Session;
import frmw.dialect.GenericSQL;
import frmw.dialect.TeradataSQL;
import frmw.model.Formula;
import frmw.model.Join;
import frmw.parser.Parsing;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.alg.util.UnionFind;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.WeightedMultigraph;

/**
 *
 * @author yulinwen
 */
public class QueryEngine {   
    private class JDRemoveUnit {
        private JoinDefinition joinDef = null;
        private int usedCount = 0;
        private final List<ProcessingUnit> linkedPU = new ArrayList();
        
        public JDRemoveUnit() {}
        
        public void setJoinDef(JoinDefinition inJD) {
            joinDef = inJD;
        }
        
        public JoinDefinition getJoinDef() {
            return joinDef;
        }
        
        public void setUsedCount(int setValue) {
            usedCount = setValue;
        }
        
        public int getUsedCount() {
            return usedCount;
        }
        
        public void addLinkedPU(ProcessingUnit inPU) {
            linkedPU.add(inPU);
        }
        
        public List<ProcessingUnit> getLinkedPU() {
            return linkedPU;
        }
    }
    
    private final Stage stage = new Stage();
    private final WeightedMultigraph<ProcessingUnit, EdgeUnit> joinGraph = 
            new WeightedMultigraph(new ClassBasedEdgeFactory<ProcessingUnit, EdgeUnit>(EdgeUnit.class));
    private Report report = null;
    private List<ProcessingUnit> sortedPU = null;
    private static final Parsing parser = new Parsing();
    
    public QueryEngine() {}
        
    public Report getReport() {
        return report;
    }
    
    public void preprocess(Session inSession) {
        EdgeUnit.resetID();
        ProcessingUnit.resetID();
        
        stage.preprocess(inSession);
        
        // create all verteces (PUs)
        sortedPU = new ArrayList(stage.getPUs().values());
        Collections.sort(sortedPU);
        for (ProcessingUnit curPU : sortedPU) {
            System.out.println("Adding vertex: " + curPU + ", type: " + curPU.getType());
            joinGraph.addVertex(curPU);
        }
        
        // loop on PUs
        // for each table, find where it is used and connect vertex and create edges
        for (ProcessingUnit curPU : sortedPU) {
            System.out.println("### PU id = " + curPU.getID() + ". Current PU content = " + curPU.getContent());
            List<Table> listTables = curPU.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    Table aTab = iterTable.next();
                    System.out.println("Processing table: " + aTab.getPhysicalName());
                    HashMap<String, JoinDefinition> tabJD = stage.getJoindefHTByTable(aTab.getPhysicalName());

                    if (tabJD != null) {                    
                        HashMap<String, JoinDefinition> allTabJDs = tabJD;
                        for (Map.Entry<String, JoinDefinition> jdEntry : allTabJDs.entrySet()) {
                            JoinDefinition curJD = jdEntry.getValue();
                            String otherTable = curJD.getOtherTable(aTab.getPhysicalName());
                            System.out.println("curJD = " + curJD.getName() + ", other table = " + otherTable);                                                

                            HashMap<String, Attribute> allOtherAttrs = stage.getAttributeHTByTable(otherTable);
                            for (Map.Entry<String, Attribute> allOtherAttrsEntry : allOtherAttrs.entrySet()) {
                                Attribute otherAttr = allOtherAttrsEntry.getValue();

                                System.out.println("Other Attr: " + otherAttr.getName());
                                for (ProcessingUnit otherPU : sortedPU) {
                                    if (otherPU.getContent() == otherAttr) {
                                        System.out.println("Found PU == otherAttr");
                                        int rowCost = aTab.getRowCount();
                                        int otherRowCost = inSession.getTable(otherTable).getRowCount();
                                        double weight = (double)rowCost * (double)otherRowCost;
                                        System.out.println("RowCost = " + rowCost + ", otherRowCost = " + otherRowCost + ", weight = " + weight);
                                                                                
                                        EdgeUnit aEU = new EdgeUnit();
                                        aEU.setType(EdgeUnit.EUType.EUTYPE_PHYSICAL);
                                        aEU.setJoinDef(curJD);
                                        
                                        if (curPU.getID() < otherPU.getID()) {
                                            aEU.setSrcTable(aTab.getPhysicalName());
                                            aEU.setDstTable(otherTable);
                                        } else {
                                            aEU.setSrcTable(otherTable);
                                            aEU.setDstTable(aTab.getPhysicalName());
                                        }
                                        
                                        joinGraph.setEdgeWeight(aEU, weight);
                                        joinGraph.addEdge(curPU, otherPU, aEU);
                                    }
                                }
                            }

                            HashMap<String, Metric> allOtherMetrics = stage.getMetricHTByTable(otherTable);
                            for (Map.Entry<String, Metric> allOtherMetricsEntry : allOtherMetrics.entrySet()) {
                                Metric otherMetric = allOtherMetricsEntry.getValue();

                                System.out.println("Other Metric: " + otherMetric.getName());
                                for (ProcessingUnit otherPU : sortedPU) {
                                    if (otherPU.getContent() == otherMetric) {
                                        System.out.println("Found PU == otherMetric");
                                        int rowCost = aTab.getRowCount();
                                        int otherRowCost = inSession.getTable(otherTable).getRowCount();
                                        double weight = (double)rowCost * (double)otherRowCost;
                                        System.out.println("RowCost = " + rowCost + ", otherRowCost = " + otherRowCost + ", weight = " + weight);

                                        EdgeUnit aEU = new EdgeUnit();
                                        aEU.setType(EdgeUnit.EUType.EUTYPE_PHYSICAL);
                                        aEU.setJoinDef(curJD);
                                        
                                        if (curPU.getID() < otherPU.getID()) {
                                            aEU.setSrcTable(aTab.getPhysicalName());
                                            aEU.setDstTable(otherTable);
                                        } else {
                                            aEU.setSrcTable(otherTable);
                                            aEU.setDstTable(aTab.getPhysicalName());
                                        }                                        
                                                                                
                                        joinGraph.setEdgeWeight(aEU, weight);
                                        joinGraph.addEdge(curPU, otherPU, aEU);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // dump graph
        System.out.println("#### Before removing extra edges...");
        dumpGraph(joinGraph);
        
        // loop each vertex and remove edges that have same definition until one left
        removeExtraEdges(joinGraph);
        
        // dump graph
        System.out.println("#### After removing extra edges and table aliasing...");
        dumpGraph(joinGraph);
        
        // mst algo
        KruskalMinimumSpanningTree kmt = new KruskalMinimumSpanningTree(joinGraph);
        System.out.println("kmt total cost: " + kmt.getMinimumSpanningTreeTotalWeight());
        Set<EdgeUnit> euSet = kmt.getMinimumSpanningTreeEdgeSet();
        for (EdgeUnit eu : euSet) {
            System.out.println("Edge joindef name = " + eu.getJoinDef().getName() + ", weight = " + eu.getWeight() + ", tleft = " + eu.getJoinDef().getTLeft() + ", tright = " + eu.getJoinDef().getTRight());
        }
        
        // match expression
        matchExpression(sortedPU, euSet);

        // dump graph
        System.out.println("#### After matching expression...");
        dumpGraph(joinGraph);
        
        // merge PUs
        mergePU(joinGraph, sortedPU, euSet);

        // dump graph
        System.out.println("#### After merging PU...");
        dumpGraph(joinGraph);
        
        // generate report
        report = generateReport(joinGraph, sortedPU, euSet);        
    }
    
    private void removeExtraEdges(WeightedMultigraph<ProcessingUnit, EdgeUnit> inGraph) {
        Set<ProcessingUnit> graphVertexSet = inGraph.vertexSet();
        for (ProcessingUnit pu : graphVertexSet) {
            ArrayList<JDRemoveUnit> jdRemoveAL = new ArrayList();                        
            Set<EdgeUnit> graphEdgeSet = inGraph.edgesOf(pu);
            
            // build remove array
            for (EdgeUnit eu : graphEdgeSet) {
                int sizeJDRemoveAL = jdRemoveAL.size();
                
                if (sizeJDRemoveAL == 0) {
                    JDRemoveUnit aJDRemoveUnit = new JDRemoveUnit();
                    aJDRemoveUnit.setJoinDef(eu.getJoinDef());
                    aJDRemoveUnit.setUsedCount(1);
                    aJDRemoveUnit.addLinkedPU((ProcessingUnit)eu.retrieveOtherEndPoint(pu));     
                    jdRemoveAL.add(aJDRemoveUnit);
                } else {
                    boolean found = false;
                    for (int i = 0; i <sizeJDRemoveAL; i++) {
                        JDRemoveUnit curJDRemoveUnit = jdRemoveAL.get(i);                        
                        
                        if (curJDRemoveUnit.getJoinDef() == eu.getJoinDef()) {
                            found = true;
                            int usedCount = curJDRemoveUnit.getUsedCount();
                            
                            curJDRemoveUnit.setUsedCount(usedCount+1);
                            curJDRemoveUnit.addLinkedPU((ProcessingUnit)eu.retrieveOtherEndPoint(pu));
                        }
                    }
                    
                    if (found == false) {
                        JDRemoveUnit aJDRemoveUnit = new JDRemoveUnit();
                        aJDRemoveUnit.setJoinDef(eu.getJoinDef());
                        aJDRemoveUnit.setUsedCount(1);
                        aJDRemoveUnit.addLinkedPU((ProcessingUnit)eu.retrieveOtherEndPoint(pu));
                        jdRemoveAL.add(aJDRemoveUnit);
                    }
                }
            }
            
            // find and remove edge
            int sizeJDRemoveAL = jdRemoveAL.size();
            System.out.println("### JDRemoveAL: " + pu.getContent() + ", size = " + sizeJDRemoveAL);
            for (int i = 0; i<sizeJDRemoveAL; i++) {
                JDRemoveUnit curJDRemoveUnit = jdRemoveAL.get(i);
                System.out.println("JoinDef: " + curJDRemoveUnit.getJoinDef().getName() + ", usedCount = " + curJDRemoveUnit.getUsedCount());
                List<ProcessingUnit> curJDRemoveUnitLinkedPUAL = curJDRemoveUnit.getLinkedPU();                
                int sizeCurJDRemoveUnitLinkedPUAL = curJDRemoveUnitLinkedPUAL.size();
                
                if (sizeCurJDRemoveUnitLinkedPUAL > 1) {
                    int difference = curJDRemoveUnit.getUsedCount() - 1;
                    
                    Collections.sort(curJDRemoveUnitLinkedPUAL, new Comparator<ProcessingUnit>(){
                        @Override
                        public int compare(ProcessingUnit pu1, ProcessingUnit pu2) {
                            if (pu1.getRemoveCount() < pu2.getRemoveCount()) {
                                return -1;
                            } else if (pu1.getRemoveCount() > pu2.getRemoveCount()) {
                                return 1;
                            } else {
                                if (pu1.getID() < pu2.getID()) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            }
                        }
                    });
                        
                    // dump
                    for (int j = 0; j<sizeCurJDRemoveUnitLinkedPUAL; j++) {
                        ProcessingUnit curPU = curJDRemoveUnitLinkedPUAL.get(j);
                        System.out.println("  LinkedPU: " + curPU.getContent() + ", removeCount = " + curPU.getRemoveCount());
                    }
                                        
                    for (int j = 0; j<difference; j++) {
                        ProcessingUnit curPU = curJDRemoveUnitLinkedPUAL.get(j);
                        int removeCount = curPU.getRemoveCount();
                        inGraph.removeEdge(pu, curPU);
                        curPU.setRemoveCount(removeCount+1);
                    }
                }
            }            
        }
    }
    
    private void matchExpression(List<ProcessingUnit> sortedPU, Set<EdgeUnit> euSet) {
        for (EdgeUnit eu : euSet) {
            ProcessingUnit srcPU = eu.getSrcPU();
            if (srcPU.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) {
                Attribute srcAttr = (Attribute)srcPU.getContent();
                //srcPU.setUsedExp(srcAttr.getExpressionByTableName(eu.getSrcTable()));
                srcPU.setUsedExp(srcAttr.getExpressionUnitByTableName(eu.getSrcTable()));
            } else if (srcPU.getType() == ProcessingUnit.PUType.PUTYPE_METRIC) {
                Metric srcMet = (Metric)srcPU.getContent();
                //srcPU.setUsedExp(srcMet.getExpressionByTableName(eu.getSrcTable()));
                srcPU.setUsedExp(srcMet.getExpressionUnitByTableName(eu.getSrcTable()));
            } else {
                // PUTYPE_HARDHINT                
            }
            srcPU.setProcessed(true);
            
            ProcessingUnit dstPU = eu.getDstPU();
            if (dstPU.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) {
                Attribute dstAttr = (Attribute)dstPU.getContent();
                //dstPU.setUsedExp(dstAttr.getExpressionByTableName(eu.getDstTable()));
                dstPU.setUsedExp(dstAttr.getExpressionUnitByTableName(eu.getDstTable()));
            } else if (dstPU.getType() == ProcessingUnit.PUType.PUTYPE_METRIC) {
                Metric dstMet = (Metric)dstPU.getContent();
                //dstPU.setUsedExp(dstMet.getExpressionByTableName(eu.getDstTable()));
                dstPU.setUsedExp(dstMet.getExpressionUnitByTableName(eu.getDstTable()));
            }
            dstPU.setProcessed(true);
        }
        
        // now loop on PU for PUs not yet processed because not linked by joindef
        //Set<ProcessingUnit> graphVertexSet = inGraph.vertexSet();
        for (ProcessingUnit pu : sortedPU) {
            if (pu.getProcessed() == false) {
                if (pu.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) {
                    Attribute curAttr = (Attribute)pu.getContent();
                    List<Expression> expAL = curAttr.getExpressions();
                    Collections.sort(expAL);
                    if (expAL.size() > 0) {
                        //pu.setUsedExp(expAL.get(0));
                        pu.setUsedExp(new ExpressionUnit(expAL.get(0), expAL.get(0).getColumns().get(0)));
                    }
                } else if (pu.getType() == ProcessingUnit.PUType.PUTYPE_METRIC) {
                    Metric curMet = (Metric)pu.getContent();
                    List<Expression> expAL = curMet.getExpressions();
                    Collections.sort(expAL);
                    if (expAL.size() > 0) {
                        //pu.setUsedExp(expAL.get(0));
                        pu.setUsedExp(new ExpressionUnit(expAL.get(0), expAL.get(0).getColumns().get(0)));
                    }
                }
            } else {
                pu.setProcessed(false);
            }
        }
    }
    
    // ugly fix for test8
    private void mergePU(WeightedMultigraph<ProcessingUnit, EdgeUnit> inGraph, List<ProcessingUnit>sortedPU, Set<EdgeUnit> euSet) {
        Map<Table, ProcessingUnit> table2PUMap = new HashMap();
        Map<Column, ProcessingUnit> column2PUMap = new HashMap();
        
        for (ProcessingUnit pu : sortedPU) {
            if (pu.getUsedExp() != null) {            
                Table usedTab = pu.getUsedExp().getColumn().getTable();

                if (table2PUMap.containsKey(usedTab)) {
                    ProcessingUnit tabMatchingPU = table2PUMap.get(usedTab);
                    Column usedCol = pu.getUsedExp().getColumn();                

                    if (column2PUMap.containsKey(usedCol)) {
                        ProcessingUnit colMatchingPU = column2PUMap.get(usedCol);

                        if (tabMatchingPU == colMatchingPU) {
                            // table and column both match
                            // don't do anything
                        }
                    } else {
                        // same table but different column
                        column2PUMap.put(usedCol, pu);
                        // FIXME: try to merge/remove the PU
                        pu.setMasterPU(tabMatchingPU);
                        inGraph.removeVertex(pu);
                        // FIXME: change the destination EU from the removed one the the master one
                        for (EdgeUnit patchEU : euSet) {
                            if (patchEU.getSrcPU() == pu) {
                                patchEU.setSrcPU(tabMatchingPU);
                            }
                            
                            if (patchEU.getDstPU() == pu) {
                                patchEU.setDstPU(tabMatchingPU);
                            }
                        }
                    }
                } else {
                    table2PUMap.put(usedTab, pu);
                    Column usedCol = pu.getUsedExp().getColumn();
                    column2PUMap.put(usedCol, pu);
                }
            }
        }
    }
    
    public StringBuilder generateSqlString(String prefix, String inplace, StringBuilder builder) {
        builder.append(prefix).append(" ").append(inplace).append("\n");
       
        return builder;
    }    
    
    private Block generateBlockNew(List<EdgeUnit> sortedEUs, List<ProcessingUnit> sortedVertex) {
        int attrCount = 0;
        int metCount = 0;
        int cnt = 0;
        Block aBlock = new Block();
        StringBuilder selectStr = new StringBuilder();
        StringBuilder fromStr = new StringBuilder();
        StringBuilder groupByStr = new StringBuilder();
        List<String> selectList = new ArrayList();
        List<String> groupByList = new ArrayList();
        
        if (sortedEUs.isEmpty()) {
            // single attribute or metric but can be multiple because some PUs are linked to master PU
            ProcessingUnit curPU = null;
            for (ProcessingUnit masterPU : sortedVertex) {
                if (masterPU.getMasterPU() == masterPU) {
                    curPU = masterPU;
                    break;
                }
            }
                        
            String tempStr = "\"" + curPU.getUsedExp().getColumn().getTable().getPhysicalName() + "\"" + " AS " + "\"" + curPU.assignTableAlias() + "\"";
            fromStr = generateSqlString("FROM", tempStr, fromStr);
            
            curPU.setProcessed(true);
            cnt++;
        } else {        
            for (EdgeUnit eu : sortedEUs) {
                if (eu.getType() == EdgeUnit.EUType.EUTYPE_PHYSICAL) {
                    JoinDefinition aJoin = eu.getJoinDef();
                    
                    if (aJoin.getExpression() == null) {
                        // the join-definition is a cross join that came from query engine, so there is no expression
                        ProcessingUnit srcPU = eu.getSrcPU();
                        ProcessingUnit dstPU = eu.getDstPU();

                        Table srcTable;
                        Table dstTable;
                        String srcTableName;
                        String dstTableName;

                        if (srcPU.getType() == ProcessingUnit.PUType.PUTYPE_HARDHINT) {
                            srcTable = (Table)srcPU.getContent();
                            srcTableName = ((Table)srcPU.getContent()).getPhysicalName();
                        } else {
                            srcTable = srcPU.getUsedExp().getExpression().getSmallestColumn().getTable();
                            srcTableName = srcPU.getUsedExp().getExpression().getSmallestColumn().getTable().getPhysicalName();
                        }

                        if (dstPU.getType() == ProcessingUnit.PUType.PUTYPE_HARDHINT) {
                            dstTable = (Table)dstPU.getContent();
                            dstTableName = ((Table)dstPU.getContent()).getPhysicalName();
                        } else {
                            dstTable = dstPU.getUsedExp().getExpression().getSmallestColumn().getTable();
                            dstTableName = dstPU.getUsedExp().getExpression().getSmallestColumn().getTable().getPhysicalName();
                        }

                        // make a virtual join definition
                        JoinDefinition virtualJD = null;

                        if (cnt == 0) {
                            srcPU.setProcessed(true);
                            dstPU.setProcessed(true);

                            String tempStr = "\"" + srcTableName + "\"" + " AS " + "\"" + srcPU.assignTableAlias() + "\"";
                            fromStr = generateSqlString("FROM", tempStr, fromStr);
                            tempStr = "\"" + dstTableName + "\"" + " AS " + "\"" + dstPU.assignTableAlias() + "\"";
                            fromStr = generateSqlString("CROSS JOIN", tempStr, fromStr);
                        } else {
                            if (srcPU.getProcessed() == false) {
                                srcPU.setProcessed(true);

                                String tempStr = "\"" + srcTableName + "\"" + " AS " + "\"" + srcPU.assignTableAlias() + "\"";
                                fromStr = generateSqlString("CROSS JOIN", tempStr, fromStr);                          
                            } else {
                                dstPU.setProcessed(true);

                                String tempStr = "\"" + dstTableName + "\"" + " AS " + "\"" + dstPU.assignTableAlias() + "\"";
                                fromStr = generateSqlString("CROSS JOIN", tempStr, fromStr);                                                       
                            }
                        }

                        aBlock.addJoinDefList(aJoin);
                    } else {
                        // change join type to be returned to UI
                        if (aJoin.getType() == JoinDefinition.JoinType.CROSS) {
                            aJoin.setType(JoinDefinition.JoinType.INNER);
                        }

                        String jExp = aJoin.getExpression();
                        System.out.println("XXXXXX: " + jExp);

                        Join j = QueryEngine.parser.parseJoin(jExp);
                        j = QueryEngine.parser.parseJoin(j.rewriteFormula());
                        Join rewrite = QueryEngine.parser.parseJoin(j.rewriteFormula(eu.retrieveMatchingAlias(aJoin.getTLeft()), eu.retrieveMatchingAlias(aJoin.getTRight())));

                        if (cnt == 0) {
                            ProcessingUnit matchingPU = eu.retrieveMatchingPU(aJoin.getTLeft());
                            String tempStr = "\"" + aJoin.getTLeft() + "\"" + " AS " + "\"" + matchingPU.assignTableAlias() + "\"";
                            fromStr = generateSqlString("FROM", tempStr, fromStr);

                            matchingPU.setProcessed(true);
                        }

                        String tempStr;
                        if (cnt == 0) {
                            tempStr = "\"" + aJoin.getTRight() + "\"" + " AS " + "\"" + eu.retrieveMatchingAlias(aJoin.getTRight()) + "\"" + " ON " + rewrite.sql(new TeradataSQL());
                        } else {
                            ProcessingUnit leftPU = eu.retrieveMatchingPU(aJoin.getTLeft());
                            ProcessingUnit rightPU = eu.retrieveMatchingPU(aJoin.getTRight());

                            if (leftPU.getProcessed() == false) {
                                tempStr = "\"" + aJoin.getTLeft() + "\"" + " AS " + "\"" + eu.retrieveMatchingAlias(aJoin.getTLeft()) + "\"" + " ON " + rewrite.sql(new TeradataSQL());
                            } else {
                                tempStr = "\"" + aJoin.getTRight() + "\"" + " AS " + "\"" + eu.retrieveMatchingAlias(aJoin.getTRight()) + "\"" + " ON " + rewrite.sql(new TeradataSQL());
                            }
                        }
                        fromStr = generateSqlString("INNER JOIN", tempStr, fromStr);
                        
                        aBlock.addJoinDefList(aJoin);
                    }
                } else { //EUTYPE_VIRTUAL
                    ProcessingUnit srcPU = eu.getSrcPU();
                    ProcessingUnit dstPU = eu.getDstPU();

                    Table srcTable;
                    Table dstTable;
                    String srcTableName;
                    String dstTableName;

                    if (srcPU.getType() == ProcessingUnit.PUType.PUTYPE_HARDHINT) {
                        srcTable = (Table)srcPU.getContent();
                        srcTableName = ((Table)srcPU.getContent()).getPhysicalName();
                    } else {
                        srcTable = srcPU.getUsedExp().getExpression().getSmallestColumn().getTable();
                        srcTableName = srcPU.getUsedExp().getExpression().getSmallestColumn().getTable().getPhysicalName();
                    }

                    if (dstPU.getType() == ProcessingUnit.PUType.PUTYPE_HARDHINT) {
                        dstTable = (Table)dstPU.getContent();
                        dstTableName = ((Table)dstPU.getContent()).getPhysicalName();
                    } else {
                        dstTable = dstPU.getUsedExp().getExpression().getSmallestColumn().getTable();
                        dstTableName = dstPU.getUsedExp().getExpression().getSmallestColumn().getTable().getPhysicalName();
                    }

                    // make a virtual join definition
                    JoinDefinition virtualJD = null;
                    
                    if (cnt == 0) {
                        srcPU.setProcessed(true);
                        dstPU.setProcessed(true);
                        
                        String tempStr = "\"" + srcTableName + "\"" + " AS " + "\"" + srcPU.assignTableAlias() + "\"";
                        fromStr = generateSqlString("FROM", tempStr, fromStr);
                        tempStr = "\"" + dstTableName + "\"" + " AS " + "\"" + dstPU.assignTableAlias() + "\"";
                        fromStr = generateSqlString("CROSS JOIN", tempStr, fromStr);
                        
                        // make a virtual join def
                        virtualJD = new JoinDefinition(null, null, null, srcTableName, srcTable.getUUID().toString(), 
                            dstTableName, dstTable.getUUID().toString(), null, JoinDefinition.JoinType.CROSS);
                    } else {
                        if (srcPU.getProcessed() == false) {
                            srcPU.setProcessed(true);
                            
                            String tempStr = "\"" + srcTableName + "\"" + " AS " + "\"" + srcPU.assignTableAlias() + "\"";
                            fromStr = generateSqlString("CROSS JOIN", tempStr, fromStr);
                            
                            // make a virtual join def
                            virtualJD = new JoinDefinition(null, null, null, srcTableName, srcTable.getUUID().toString(), 
                                dstTableName, dstTable.getUUID().toString(), null, JoinDefinition.JoinType.CROSS);                            
                        } else {
                            dstPU.setProcessed(true);
                            
                            String tempStr = "\"" + dstTableName + "\"" + " AS " + "\"" + dstPU.assignTableAlias() + "\"";
                            fromStr = generateSqlString("CROSS JOIN", tempStr, fromStr);
                            
                            // make a virtual join def
                            virtualJD = new JoinDefinition(null, null, null, dstTableName, dstTable.getUUID().toString(), 
                                srcTableName, srcTable.getUUID().toString(), null, JoinDefinition.JoinType.CROSS);                                                       
                        }
                    }
                    
                    aBlock.addJoinDefList(virtualJD);
                }
                cnt++;
            }
        }

        // construct select
        // get all expressions from all attributes/metrics
        for (ProcessingUnit curPU : sortedVertex) {
            if ((curPU.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) || (curPU.getType() == ProcessingUnit.PUType.PUTYPE_METRIC)) {
                // sql-function parsing
                Formula curFormula = QueryEngine.parser.parse(curPU.getUsedExp().getExpression().getFormula());
                curFormula.setTableAliases(of(curPU.getUsedExp().getColumn().getObjectName(), curPU.assignTableAlias()));
                
                if (curPU.getUsedExp().getExpression().getParameters().isEmpty() == false) {
                    if (curPU.getUsedExp().getExpression().getParameters().containsKey(PARAMTYPE_DISTINCT)) {
                        String value = curPU.getUsedExp().getExpression().getParameters().get(PARAMTYPE_DISTINCT);
                        Boolean bValue = !value.equals("false");
                        
                        curFormula.aggregationParameters().get(0).distinct(bValue);
                    }
                }
                
                // FIXME: use specific db setting
                String selectItem = curFormula.sql(new TeradataSQL());
                selectList.add(selectItem);

                if (curPU.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) {
                    attrCount++;
                    groupByList.add(selectItem);
                    Expression curUsedExp = curPU.getUsedExp().getExpression();
                    Table curUsedTab = curPU.getUsedExp().getColumn().getTable();
                    aBlock.addAttributeMap(((Attribute)curPU.getContent()).getUUID(), curUsedExp.getUUID());
                    aBlock.addExpressionMap(curUsedExp.getUUID(), curUsedTab.getUUID());
                } else if (curPU.getType() == ProcessingUnit.PUType.PUTYPE_METRIC) {
                    metCount++;
                    Expression curUsedExp = curPU.getUsedExp().getExpression();
                    Table curUsedTab = curPU.getUsedExp().getColumn().getTable();
                    aBlock.addMetricMap(((Metric)curPU.getContent()).getUUID(), curUsedExp.getUUID());
                    aBlock.addExpressionMap(curUsedExp.getUUID(), curUsedTab.getUUID());
                }
            }
            
            // for all PUs, retrun the table <-> table aliase
            if (curPU.getType() == ProcessingUnit.PUType.PUTYPE_HARDHINT) {
                System.out.println("Add to tableMap: " + ((Table)curPU.getContent()).getPhysicalName() + " : " + curPU.getTableAlias());
                aBlock.addTableMap(((Table)curPU.getContent()).getUUID(), curPU.getTableAlias());
            } else {
                System.out.println("Add to tableMap: " + curPU.getUsedExp().getColumn().getTable().getPhysicalName() + " : " + curPU.getTableAlias());
                aBlock.addTableMap(curPU.getUsedExp().getColumn().getTable().getUUID(), curPU.getTableAlias());
            }
        }
                
        // construct groupby
        if ((attrCount > 0) && (metCount > 0)) {
            String tempStr = "";
            for (int i=0; i<groupByList.size(); i++) {
                if (i == groupByList.size()-1) {
                    tempStr = tempStr.concat(groupByList.get(i));
                } else {
                    tempStr = tempStr.concat(groupByList.get(i) + ", ");
                }
            }
            groupByStr = generateSqlString("GROUP BY", tempStr, groupByStr);
        }       
        
        // construct select
        String tempStr = "";
        for (int i=0; i<selectList.size(); i++) {
            if (i == selectList.size()-1) {
                tempStr = tempStr.concat(selectList.get(i));
            } else {
                tempStr = tempStr.concat(selectList.get(i) + ", ");
            }
        }
        selectStr = generateSqlString("SELECT", tempStr, selectStr);
        
        StringBuilder finalSqlStr = selectStr.append(fromStr);
        if (groupByStr.length() > 0) {
            finalSqlStr = finalSqlStr.append(groupByStr);
        }
        
        // remove trailing \n character
        finalSqlStr.deleteCharAt(finalSqlStr.length()-1);
        
        aBlock.setSqlString(finalSqlStr.toString());
        
        System.out.println("New Result: " + finalSqlStr.toString());
        
        return aBlock;
    }
    
    private Report generateReport(WeightedMultigraph<ProcessingUnit, EdgeUnit> inGraph, List<ProcessingUnit> sortedPU, Set<EdgeUnit> euSet) {
        Set<ProcessingUnit> vertexSet = inGraph.vertexSet();
        UnionFind<ProcessingUnit> unionPU = new UnionFind(vertexSet);
        Report aReport = new Report();
                     
        // union find on PU
        for (EdgeUnit eu : euSet) {
            System.out.println("The eu: " + eu + " : " + eu.getDstTable() + " : " + eu.getSrcTable());
            unionPU.union(eu.getSrcPU(), eu.getDstPU());
        }
        
        // build hashmap and count how many disjoint groups
        int nGroup = 0;
        ProcessingUnit allLinkedPU = null;
        HashMap<ProcessingUnit, ArrayList<ProcessingUnit>> puHM = new HashMap();
        for (ProcessingUnit pu : vertexSet) {
            ProcessingUnit masterPU = unionPU.find(pu);            
            
            // for cross join, initiate a PU that should link to all the other groups
            if (allLinkedPU == null) {
                allLinkedPU = masterPU;
            }
            
            if (puHM.containsKey(masterPU) == false) {
                puHM.put(masterPU, new ArrayList());
                nGroup++;
                
                if (allLinkedPU != masterPU) {
                    EdgeUnit aEU = new EdgeUnit();
                    aEU.setType(EdgeUnit.EUType.EUTYPE_VIRTUAL);
                    aEU.setJoinDef(null);
                    
                    if (allLinkedPU.getID() < masterPU.getID()) {
                        aEU.setSrcPU(allLinkedPU);
                        aEU.setDstPU(masterPU);
                    } else {
                        aEU.setSrcPU(masterPU);
                        aEU.setDstPU(allLinkedPU);
                    }
                    euSet.add(aEU);
                }                
            }
            
            puHM.get(masterPU).add(pu);            
        }
        System.out.println("Join group count = " + nGroup);                
        
        // dump puHM
        for (Map.Entry<ProcessingUnit, ArrayList<ProcessingUnit>> entry : puHM.entrySet()) {
            ProcessingUnit masterPU = entry.getKey();
            ArrayList<ProcessingUnit> puAL = entry.getValue();
            System.out.println("Master pu id = " + masterPU.getID());
            
            for (int i=0; i<puAL.size(); i++) {
                ProcessingUnit curPU = puAL.get(i);
                System.out.println("  pu id = " + curPU.getID());
            }
        }
        
        // sort eu set
        // you can only sort eu here because you may need to add new virtual eu
        ArrayList<EdgeUnit> sortedEUs = new ArrayList(euSet);
        Collections.sort(sortedEUs);
        
        // dump sortedEUs
        System.out.println("########## Dump sortedEUs...");
        for (EdgeUnit eu : sortedEUs) {
            System.out.println("eu id: " + eu.getID() + " : " + eu);
        }

        Block curBlock = generateBlockNew(sortedEUs, sortedPU);
        aReport.addBlock(curBlock);  
                
        return aReport;
    }
    
    private void dumpGraph(WeightedMultigraph<ProcessingUnit, EdgeUnit> inGraph) {
        System.out.println("### Dumping graph...");
        Set<ProcessingUnit> graphVertexSet = inGraph.vertexSet();
        int vertexCount = 0;
        for (ProcessingUnit pu : graphVertexSet) {
            System.out.println("  Vertex = " + vertexCount + " : " + pu.getContent() + " : " + pu.getTableAlias());
            if (pu.getUsedExp() != null) {
                System.out.println("    UsedExp (table:column:formula) = " + 
                    pu.getUsedExp().getColumn().getTable().getPhysicalName() + ":" +
                    pu.getUsedExp().getColumn().getObjectName() + ":" +
                    pu.getUsedExp().getExpression().getFormula());
            }
            
            Set<EdgeUnit> graphEdgeSet = inGraph.edgesOf(pu);
            
            int edgeCount = 0;
            for (EdgeUnit eu : graphEdgeSet) {
                System.out.println("    Edge = " + edgeCount + " : " + eu.getJoinDef().getName());
                edgeCount++;
            }
            
            vertexCount++;
        }
    }
    
    private void expirimentOnGraph() {
        WeightedMultigraph<String, EdgeUnit> testGraph
            = new WeightedMultigraph(new ClassBasedEdgeFactory<String, EdgeUnit>(EdgeUnit.class));
        
        testGraph.addVertex("V1");
        testGraph.addVertex("V2");
        testGraph.addVertex("V3");
        testGraph.addVertex("V4");
        
        EdgeUnit e1 = new EdgeUnit();
        testGraph.setEdgeWeight(e1, 5);
        testGraph.addEdge("V1", "V2", e1);
        
        EdgeUnit e2 = new EdgeUnit();
        testGraph.setEdgeWeight(e2, 3);
        testGraph.addEdge("V1", "V2", e2);
        
        EdgeUnit e3 = new EdgeUnit();
        testGraph.setEdgeWeight(e3, 7);
        testGraph.addEdge("V2", "V4", e3);
      
        EdgeUnit e4 = new EdgeUnit();
        testGraph.setEdgeWeight(e4, 8);
        testGraph.addEdge("V4", "V3", e4);

        EdgeUnit e5 = new EdgeUnit();
        testGraph.setEdgeWeight(e5, 4);
        testGraph.addEdge("V4", "V3", e5);
        KruskalMinimumSpanningTree kmt = new KruskalMinimumSpanningTree(testGraph);
        Set kmtEdges = kmt.getMinimumSpanningTreeEdgeSet();
        Iterator<EdgeUnit> kmtIter = kmtEdges.iterator();
        while (kmtIter.hasNext()) {
            EdgeUnit eU = kmtIter.next();
            System.out.println("Vertext: " + eU +":"+"cost: " + eU.getWeight());
        }
        System.out.println("kmt total cost: " + kmt.getMinimumSpanningTreeTotalWeight());
    }
    
    public static void main(String [] arg) {
        Formula f = QueryEngine.parser.parse("id");
        
        //Formula f = aParsing.parse("count(trim(\"col1\" || \"col2\"))");
        
        Set<String> aSet = new HashSet(f.entityNames());
        
        for (String curString : aSet) {
            System.out.println("Individual column: " + curString);
        }
        
        String oriSql = f.sql(new GenericSQL());
        
        f.setTableAliases(of("id", "t1", "col2", "t2", "col 3", "t3", "col6", "t6"));
        
        String patchedSql = f.sql(new GenericSQL());
        
        System.out.println("Original sql output: " + oriSql);
        System.out.println("Patched sql output: " + patchedSql);
                
        //Formula f = PARSER.parse("case col1 when 1 then 2 when 2 then 3 else 5 end");
        //String sql = f.sql(GENERIC_SQL);
        //assertEquals("CASE col1 WHEN 1 THEN 2 WHEN 2 THEN 3 ELSE 5 END", sql);   
    }
    
    /*
    private Block generateBlock(List<EdgeUnit> sortedEUs, List<ProcessingUnit> sortedVertex) {
        int attrCount = 0;
        int metCount = 0;
        Block aBlock = new Block();
        
        // generate SQL
        // Create or acquire vendor
        SQLVendor vendor = null;
        try {
            vendor = SQLVendorProvider.createVendor(SQLVendor.class);
        } catch (java.io.IOException e) {
            System.out.println("Exception: " + e);
        }
        
        if (vendor == null) return null;
                
        QueryFactory q = vendor.getQueryFactory();
        BooleanFactory b = vendor.getBooleanFactory();
        TableReferenceFactory t = vendor.getTableReferenceFactory();
        LiteralFactory l = vendor.getLiteralFactory();
        ColumnsFactory c = vendor.getColumnsFactory();
        QuerySpecificationBuilder sqlQuery = q.querySpecificationBuilder();
                 
        // construct join
        TableReferenceBuilder allJoins = null;
        int cnt = 0;
        
        if (sortedEUs.isEmpty()) {
            // single attribute or metric
            assert(sortedVertex.size() == 1);
            ProcessingUnit curPU = sortedVertex.get(0);
            
            allJoins = t.tableBuilder(t.table(t.tableName(null, curPU.getUsedExp().getColumn().getTable().getPhysicalName()), t.tableAlias(curPU.assignTableAlias())));
            curPU.setProcessed(true);
            cnt++;
        } else {        
            for (EdgeUnit eu : sortedEUs) {
                if (eu.getType() == EdgeUnit.EUType.EUTYPE_PHYSICAL) {
                    JoinDefinition aJoin = eu.getJoinDef();
                    aBlock.addJoinDefList(aJoin.getUUID());

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
                        ProcessingUnit matchingPU = eu.retrieveMatchingPU(aJoin.getTLeft());
                        allJoins = t.tableBuilder(t.table(t.tableName(null, aJoin.getTLeft()), t.tableAlias(matchingPU.assignTableAlias())));
                        matchingPU.setProcessed(true);
                    }

                    BooleanExpression bE;
                    switch (jOper) {
                        case ">":
                            bE = b.gt(c.colName(eu.retrieveMatchingAlias(aJoin.getTLeft()), aJoin.getCLeft()), c.colName(eu.retrieveMatchingAlias(aJoin.getTRight()), aJoin.getCRight()));
                            break;
                        case "=":
                            bE = b.eq(c.colName(eu.retrieveMatchingAlias(aJoin.getTLeft()), aJoin.getCLeft()), c.colName(eu.retrieveMatchingAlias(aJoin.getTRight()), aJoin.getCRight()));
                            break;
                        case "<":
                            bE = b.lt(c.colName(eu.retrieveMatchingAlias(aJoin.getTLeft()), aJoin.getCLeft()), c.colName(eu.retrieveMatchingAlias(aJoin.getTRight()), aJoin.getCRight()));
                            break;
                        default:
                            bE = b.eq(c.colName(eu.retrieveMatchingAlias(aJoin.getTLeft()), aJoin.getCLeft()), c.colName(eu.retrieveMatchingAlias(aJoin.getTRight()), aJoin.getCRight()));
                            break;
                    }

                    if (cnt == 0) {
                        allJoins.addQualifiedJoin(
                            jT,
                            t.table(t.tableName(null, aJoin.getTRight()), t.tableAlias(eu.retrieveMatchingAlias(aJoin.getTRight()))),
                            t.jc(b.booleanBuilder(bE).createExpression()));
                    } else {
                        ProcessingUnit leftPU = eu.retrieveMatchingPU(aJoin.getTLeft());
                        ProcessingUnit rightPU = eu.retrieveMatchingPU(aJoin.getTRight());

                        if (leftPU.getProcessed() == false) {
                            allJoins.addQualifiedJoin(
                                jT,
                                t.table(t.tableName(null, aJoin.getTLeft()), t.tableAlias(eu.retrieveMatchingAlias(aJoin.getTLeft()))),
                                t.jc(b.booleanBuilder(bE).createExpression()));                            
                            leftPU.setProcessed(true);
                        } else {
                            allJoins.addQualifiedJoin(
                                jT,
                                t.table(t.tableName(null, aJoin.getTRight()), t.tableAlias(eu.retrieveMatchingAlias(aJoin.getTRight()))),
                                t.jc(b.booleanBuilder(bE).createExpression()));
                            rightPU.setProcessed(true);
                        }
                    }
                } else {
                    ProcessingUnit srcPU = eu.getSrcPU();
                    ProcessingUnit dstPU = eu.getDstPU();

                    String srcTableName = null;
                    String dstTableName = null;

                    if (srcPU.getType() == ProcessingUnit.PUType.PUTYPE_HARDHINT) {
                        srcTableName = ((Table)srcPU.getContent()).getPhysicalName();
                    } else {
                        //srcTableName = srcPU.getUsedExp().getSmallestColumn().getTable().getPhysicalName();
                        srcTableName = srcPU.getUsedExp().getExpression().getSmallestColumn().getTable().getPhysicalName();
                    }

                    if (dstPU.getType() == ProcessingUnit.PUType.PUTYPE_HARDHINT) {
                        dstTableName = ((Table)dstPU.getContent()).getPhysicalName();
                    } else {
                        //dstTableName = dstPU.getUsedExp().getSmallestColumn().getTable().getPhysicalName();
                        dstTableName = dstPU.getUsedExp().getExpression().getSmallestColumn().getTable().getPhysicalName();
                    }

                    if (cnt == 0) {
                        allJoins = t.tableBuilder(t.table(t.tableName(null, srcTableName), t.tableAlias(srcPU.assignTableAlias())));
                        srcPU.setProcessed(true);
                        allJoins.addCrossJoin(t.table(t.tableName(null, dstTableName), t.tableAlias(dstPU.assignTableAlias())));
                        dstPU.setProcessed(true);
                    } else {
                        if (srcPU.getProcessed() == false) {
                            allJoins.addCrossJoin(t.table(t.tableName(null, srcTableName), t.tableAlias(srcPU.assignTableAlias())));
                            srcPU.setProcessed(true);
                        } else {
                            allJoins.addCrossJoin(t.table(t.tableName(null, dstTableName), t.tableAlias(dstPU.assignTableAlias())));
                            dstPU.setProcessed(true);
                        }
                    }
                }
                cnt++;
            }
        }

        // construct select
        // get all expressions from all attributes/metrics
        ArrayList<ColumnReferenceByName> colRefByName = new ArrayList();
        ArrayList<ColumnReferenceByName> colAttrRefByName = new ArrayList();
        for (ProcessingUnit curPU : sortedVertex) {
            if ((curPU.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) || (curPU.getType() == ProcessingUnit.PUType.PUTYPE_METRIC)) {
                // sql-function parsing
                Formula curFormula = QueryEngine.parser.parse(curPU.getUsedExp().getExpression().getFormula());
                curFormula.setTableAliases(of(curPU.getUsedExp().getColumn().getObjectName(), curPU.assignTableAlias()));
                
                if (curPU.getUsedExp().getExpression().getParameters().isEmpty() == false) {
                    if (curPU.getUsedExp().getExpression().getParameters().containsKey(PARAMTYPE_DISTINCT)) {
                        String value = curPU.getUsedExp().getExpression().getParameters().get(PARAMTYPE_DISTINCT);                        
                        Boolean bValue = !value.equals("false");
                        
                        curFormula.aggregationParameters().get(0).distinct(bValue);
                    }
                }
                
                //ColumnReferenceByName aColExp = c.colName(curPU.assignTableAlias(), curPU.getUsedExp().getFormula());
                // FIXME: use specific db setting
                ColumnReferenceByName aColExp = c.colName(curFormula.sql(new TeradataSQL()));

                if (curPU.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) {
                    attrCount++; 
                    colAttrRefByName.add(aColExp);
                    Expression curUsedExp = curPU.getUsedExp().getExpression();
                    Table curUsedTab = curPU.getUsedExp().getColumn().getTable();
                    aBlock.addAttributeMap(((Attribute)curPU.getContent()).getUUID(), curUsedExp.getUUID());
                    aBlock.addExpressionMap(curUsedExp.getUUID(), curUsedTab.getUUID());
                } else if (curPU.getType() == ProcessingUnit.PUType.PUTYPE_METRIC) {
                    metCount++;
                    Expression curUsedExp = curPU.getUsedExp().getExpression();
                    Table curUsedTab = curPU.getUsedExp().getColumn().getTable();
                    aBlock.addMetricMap(((Metric)curPU.getContent()).getUUID(), curUsedExp.getUUID());
                    aBlock.addExpressionMap(curUsedExp.getUUID(), curUsedTab.getUUID());
                }

                colRefByName.add(aColExp);
            }
            
            // for all PUs, retrun the table <-> table aliase
            if (curPU.getType() == ProcessingUnit.PUType.PUTYPE_HARDHINT) {
                System.out.println("Add to tableMap: " + ((Table)curPU.getContent()).getPhysicalName() + " : " + curPU.getTableAlias());
                aBlock.addTableMap(((Table)curPU.getContent()).getUUID(), curPU.getTableAlias());
            } else {
                System.out.println("Add to tableMap: " + curPU.getUsedExp().getColumn().getTable().getPhysicalName() + " : " + curPU.getTableAlias());
                aBlock.addTableMap(curPU.getUsedExp().getColumn().getTable().getUUID(), curPU.getTableAlias());
            }
        }
        
        ColumnReference[] colRef = new ColumnReference[colRefByName.size()];
        colRef = colRefByName.toArray(colRef);                
        ColumnsBuilder selectCols = q.columnsBuilder().addUnnamedColumns(colRef);
        
        // construct groupby
        if ((attrCount > 0) && (metCount > 0)) {
            ColumnReference[] colAttrRefAR = new ColumnReference[colAttrRefByName.size()];
            colAttrRefAR = colAttrRefByName.toArray(colAttrRefAR);                       
            sqlQuery.getGroupBy().addGroupingElements(q.groupingElement(colAttrRefAR));
        }
        
        sqlQuery.setSelect(selectCols);
        
        if (allJoins != null) {
            sqlQuery.getFrom().addTableReferences(allJoins);
        }
        
        QueryExpressionBody queryExp = q.queryBuilder(sqlQuery.createExpression()).createExpression();
        aBlock.setSqlString(queryExp.toString());
        
        return aBlock;
    }
    */        
}
