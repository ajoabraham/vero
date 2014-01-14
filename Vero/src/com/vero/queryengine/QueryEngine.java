/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.Attribute;
import com.vero.metadata.Expression;
import com.vero.metadata.JoinDefinition;
import com.vero.metadata.Metric;
import com.vero.metadata.Table;
import com.vero.session.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.alg.util.UnionFind;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.WeightedMultigraph;
import org.sql.generation.api.grammar.booleans.BooleanExpression;
import org.sql.generation.api.grammar.builders.query.ColumnsBuilder;
import org.sql.generation.api.grammar.builders.query.QuerySpecificationBuilder;
import org.sql.generation.api.grammar.builders.query.TableReferenceBuilder;
import org.sql.generation.api.grammar.factories.BooleanFactory;
import org.sql.generation.api.grammar.factories.ColumnsFactory;
import org.sql.generation.api.grammar.factories.LiteralFactory;
import org.sql.generation.api.grammar.factories.QueryFactory;
import org.sql.generation.api.grammar.factories.TableReferenceFactory;
import org.sql.generation.api.grammar.query.ColumnReference;
import org.sql.generation.api.grammar.query.ColumnReferenceByName;
import org.sql.generation.api.grammar.query.QueryExpressionBody;
import org.sql.generation.api.vendor.SQLVendor;
import org.sql.generation.api.vendor.SQLVendorProvider;

/**
 *
 * @author yulinwen
 */
public class QueryEngine {   
    private class JDRemoveUnit {
        private JoinDefinition joinDef = null;
        private int usedCount = 0;
        private final ArrayList<ProcessingUnit> linkedPU = new ArrayList();
        
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
        
        public ArrayList<ProcessingUnit> getLinkedPU() {
            return linkedPU;
        }        
    }
    
    private final Stage stage = new Stage();
    private final WeightedMultigraph<ProcessingUnit, EdgeUnit> joinGraph = 
            new WeightedMultigraph(new ClassBasedEdgeFactory<ProcessingUnit, EdgeUnit>(EdgeUnit.class));
    private String resultSQL = null;
    
    public QueryEngine() {}
        
    public String getResultSQL () {
        return resultSQL;
    }
    
    public void preprocess(Session inSession) {
        stage.preprocess(inSession);
        EdgeUnit.resetID();
        ProcessingUnit.resetID();
        
        // create all verteces (PUs)
        List<ProcessingUnit> allSortedPUs = new ArrayList(stage.getPUs().values());
        Collections.sort(allSortedPUs);
        for (ProcessingUnit curPU : allSortedPUs) {
            System.out.println("Adding vertex: " + curPU + ", type: " + curPU.getType());
            joinGraph.addVertex(curPU);
        }
        
        // loop on PUs
        // for each table, find where it is used and connect vertex and create edges
        for (ProcessingUnit curPU : allSortedPUs) {
            System.out.println("### PU id = " + curPU.getID() + ". Current PU content = " + curPU.getContent());
            ArrayList<Table> listTables = curPU.retrieveTables();
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
                                for (ProcessingUnit otherPU : allSortedPUs) {
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
                                for (ProcessingUnit otherPU : allSortedPUs) {
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
        matchExpression(joinGraph, euSet);

        // dump graph
        System.out.println("#### After mtaching expression...");
        dumpGraph(joinGraph);
        
        // generate SQL
        generateSQL(joinGraph, euSet);
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
                ArrayList<ProcessingUnit> curJDRemoveUnitLinkedPUAL = curJDRemoveUnit.getLinkedPU();                
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
    
    private void matchExpression(WeightedMultigraph<ProcessingUnit, EdgeUnit> inGraph, Set<EdgeUnit> euSet) {
        for (EdgeUnit eu : euSet) {
            ProcessingUnit srcPU = eu.getSrcPU();
            if (srcPU.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) {
                Attribute srcAttr = (Attribute)srcPU.getContent();
                srcPU.setUsedExp(srcAttr.getExpressionByTableName(eu.getSrcTable()));
            } else if (srcPU.getType() == ProcessingUnit.PUType.PUTYPE_METRIC) {
                Metric srcMet = (Metric)srcPU.getContent();
                srcPU.setUsedExp(srcMet.getExpressionByTableName(eu.getSrcTable()));
            } else {
                // PUTYPE_HARDHINT
                
            }
            srcPU.setProcessed(true);
            
            ProcessingUnit dstPU = eu.getDstPU();
            if (dstPU.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) {
                Attribute dstAttr = (Attribute)dstPU.getContent();
                dstPU.setUsedExp(dstAttr.getExpressionByTableName(eu.getDstTable()));
            } else if (dstPU.getType() == ProcessingUnit.PUType.PUTYPE_METRIC) {
                Metric dstMet = (Metric)dstPU.getContent();
                dstPU.setUsedExp(dstMet.getExpressionByTableName(eu.getDstTable()));
            }
            dstPU.setProcessed(true);
        }
        
        // now loop on PU for PUs not yet processed because not linked by joindef
        Set<ProcessingUnit> graphVertexSet = inGraph.vertexSet();
        for (ProcessingUnit pu : graphVertexSet) {
            if (pu.getProcessed() == false) {
                if (pu.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) {
                    Attribute curAttr = (Attribute)pu.getContent();
                    ArrayList<Expression> expAL = curAttr.getExpressions();
                    Collections.sort(expAL);
                    if (expAL.size() > 0) {
                        pu.setUsedExp(expAL.get(0));
                    }
                } else if (pu.getType() == ProcessingUnit.PUType.PUTYPE_METRIC) {
                    Metric curMet = (Metric)pu.getContent();
                    ArrayList<Expression> expAL = curMet.getExpressions();
                    Collections.sort(expAL);
                    if (expAL.size() > 0) {
                        pu.setUsedExp(expAL.get(0));
                    }
                }
            } else {
                pu.setProcessed(false);
            }
        }
    }
    
    private void generateSQL(WeightedMultigraph<ProcessingUnit, EdgeUnit> inGraph, Set<EdgeUnit> euSet) {
        Set<ProcessingUnit> vertexSet = inGraph.vertexSet();
        UnionFind<ProcessingUnit> unionPU = new UnionFind(vertexSet);
        List<ProcessingUnit> sortedVertex = new ArrayList(vertexSet);
        Collections.sort(sortedVertex);
        int attrCount = 0;
        int metCount = 0;

        System.out.println("Generate SQL...");
        
        // union find on PU
        for (EdgeUnit eu : euSet) {
            unionPU.union(eu.getSrcPU(), eu.getDstPU());
        }
        
        // build hashmap and count how many disjoint groups
        int nGroup = 0;
        ProcessingUnit allLinkedPU = null;
        HashMap<ProcessingUnit, ArrayList<ProcessingUnit>> puHM = new HashMap();
        for (ProcessingUnit pu : sortedVertex) {
            ProcessingUnit masterPU = unionPU.find(pu);            
            
            // for cross join, initiate a PU that should link to all the other groups
            if (allLinkedPU == null) {
                allLinkedPU = masterPU;
            }
            
            if (puHM.containsKey(masterPU) == false) {
                puHM.put(masterPU, new ArrayList());
                System.out.println("group count + 1");
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
        System.out.println("group count = " + nGroup);                
        
        // dump puHM
        for (Map.Entry<ProcessingUnit, ArrayList<ProcessingUnit>> entry : puHM.entrySet()) {
            ProcessingUnit masterPU = entry.getKey();
            ArrayList<ProcessingUnit> puAL = entry.getValue();
            System.out.println("master pu id = " + masterPU.getID());
            
            for (int i=0; i<puAL.size(); i++) {
                ProcessingUnit curPU = puAL.get(i);
                System.out.println("  pu id = " + curPU.getID());
            }
        }
        
        // sort eu set
        ArrayList<EdgeUnit> sortedEUs = new ArrayList(euSet);
        Collections.sort(sortedEUs);
        
        // dump sortedEUs
        System.out.println("########## Dump sortedEUs...");
        for (EdgeUnit eu : sortedEUs) {
            System.out.println("eu id: " + eu.getID() + " : " + eu);
        }
        
        // generate SQL
        // Create or acquire vendor
        SQLVendor vendor = null;
        try {
            vendor = SQLVendorProvider.createVendor(SQLVendor.class);
        } catch (java.io.IOException e) {
            System.out.println("Exception: " + e);
        }
        
        if (vendor == null) return;
                
        QueryFactory q = vendor.getQueryFactory();
        BooleanFactory b = vendor.getBooleanFactory();
        TableReferenceFactory t = vendor.getTableReferenceFactory();
        LiteralFactory l = vendor.getLiteralFactory();
        ColumnsFactory c = vendor.getColumnsFactory();
        QuerySpecificationBuilder sqlQuery = q.querySpecificationBuilder();
                
        // construct join
        TableReferenceBuilder allJoins = null;
        int cnt = 0;
        for (EdgeUnit eu : sortedEUs) {
            if (eu.getType() == EdgeUnit.EUType.EUTYPE_PHYSICAL) {
                JoinDefinition aJoin = eu.getJoinDef();
                
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
                    srcTableName = srcPU.getUsedExp().getSmallestColumn().getTable().getPhysicalName();
                }
                
                if (dstPU.getType() == ProcessingUnit.PUType.PUTYPE_HARDHINT) {
                    dstTableName = ((Table)dstPU.getContent()).getPhysicalName();
                } else {
                    dstTableName = dstPU.getUsedExp().getSmallestColumn().getTable().getPhysicalName();
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

        // construct select
        // get all expressions from all attributes/metrics
        ArrayList<ColumnReferenceByName> colRefByName = new ArrayList();
        ArrayList<ColumnReferenceByName> colAttrRefByName = new ArrayList();
        for (ProcessingUnit curPU : sortedVertex) {            
            if ((curPU.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) || (curPU.getType() == ProcessingUnit.PUType.PUTYPE_METRIC)) {                    
                ColumnReferenceByName aColExp = c.colName(curPU.assignTableAlias(), curPU.getUsedExp().getExpression());

                if (curPU.getType() == ProcessingUnit.PUType.PUTYPE_ATTRIBUTE) { attrCount++; colAttrRefByName.add(aColExp); }
                if (curPU.getType() == ProcessingUnit.PUType.PUTYPE_METRIC) metCount++;

                colRefByName.add(aColExp);
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
        sqlQuery.getFrom().addTableReferences(allJoins);
        QueryExpressionBody queryExp = q.queryBuilder(sqlQuery.createExpression()).createExpression();
        resultSQL = queryExp.toString();
        System.out.println("Output sql is: " + resultSQL);        
    }
    
    private void dumpGraph(WeightedMultigraph<ProcessingUnit, EdgeUnit> inGraph) {
        System.out.println("### Dumping graph...");
        Set<ProcessingUnit> graphVertexSet = inGraph.vertexSet();
        int vertexCount = 0;
        for (ProcessingUnit pu : graphVertexSet) {
            System.out.println("  Vertex = " + vertexCount + " : " + pu.getContent() + " : " + pu.getTableAlias());
            if (pu.getUsedExp() != null) {
                System.out.println("    UsedExp = " + pu.getUsedExp().getExpression());
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
}
