/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.Attribute;
import com.vero.metadata.JoinDefinition;
import com.vero.metadata.Metric;
import com.vero.metadata.Table;
import com.vero.session.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

/**
 *
 * @author yulinwen
 */
public class QueryEngine {
    private class EdgeUnit extends DefaultWeightedEdge {      
        private JoinDefinition joinDef;

        public EdgeUnit() {
            super();
        }
        
        public JoinDefinition getJoinDef() {
            return joinDef;
        }
        
        public void setJoinDef(JoinDefinition inJoinDef) {
            joinDef = inJoinDef;
        }

        public Object retrieveOtherEndPoint(Object thisEndPoint) {
            if (this.getSource() == thisEndPoint) {
                return this.getTarget();
            } else {
                return this.getSource();
            }
        }
        
        @Override
        public double getWeight() {
            return super.getWeight();
        }
    }
    
    private class JDRemoveUnit {
        private JoinDefinition joinDef;
        private int usedCount;
        private ArrayList<ProcessingUnit> linkedPU;
        
        public JDRemoveUnit() {
            joinDef = null;
            usedCount = 0;
            linkedPU = new ArrayList();
        }
        
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
    
    private final Stage stage;
    private final WeightedMultigraph<ProcessingUnit, EdgeUnit> joinGraph;
    
    public QueryEngine() {
        stage = new Stage();
        joinGraph = new WeightedMultigraph(new ClassBasedEdgeFactory<ProcessingUnit, EdgeUnit>(EdgeUnit.class));                
    }
            
    public void preprocess(Session inSession) {
        stage.preprocess(inSession);

        //expirimentOnGraph();
        
        // create all verteces (PU)
        HashMap<UUID, ProcessingUnit> allPUs = stage.getPUs();
        Map<UUID, ProcessingUnit> puMap = allPUs;
        for (Map.Entry<UUID, ProcessingUnit> entry : puMap.entrySet()) {
            ProcessingUnit pu = entry.getValue();
            
            System.out.println("Adding vertex: " + pu + ", type: " + pu.getType());
            joinGraph.addVertex(pu);
        }
        
        // loop on PUs
        // for each table, find where it is used and connect vertex and create edges
        for (Map.Entry<UUID, ProcessingUnit> entry : puMap.entrySet()) {
            ProcessingUnit pu = entry.getValue();
            System.out.println("### Current PU content = " + pu.getContent());
            ArrayList<Table> listTables = pu.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    Table aTab = iterTable.next();
                    System.out.println("Processing table: " + aTab.getPhysicalName());
                    HashMap<String, JoinDefinition> tabJD = stage.getJoindefHTByTable(aTab.getPhysicalName());

                    if (tabJD != null) {                    
                        HashMap<String, JoinDefinition> allTabJDs = tabJD;
                        Map<String, JoinDefinition> allJDMap = allTabJDs;
                        for (Map.Entry<String, JoinDefinition> jdEntry : allJDMap.entrySet()) {
                            JoinDefinition curJD = jdEntry.getValue();
                            String otherTable = curJD.getOtherTable(aTab.getPhysicalName());
                            System.out.println("curJD = " + curJD.getName() + ", other table = " + otherTable);                                                

                            HashMap<String, Attribute> otherAttrHT = stage.getAttributeHTByTable(otherTable);
                            HashMap<String, Attribute> allOtherAttrs = otherAttrHT;
                            Map<String, Attribute> allOtherAttrsMap = allOtherAttrs;
                            for (Map.Entry<String, Attribute> allOtherAttrsEntry : allOtherAttrsMap.entrySet()) {
                                Attribute otherAttr = allOtherAttrsEntry.getValue();

                                System.out.println("Other Attr: " + otherAttr.getName());
                                Map<UUID, ProcessingUnit> OtherPUMap = allPUs;
                                for (Map.Entry<UUID, ProcessingUnit> otherPUEntry : OtherPUMap.entrySet()) {
                                    ProcessingUnit otherPU = otherPUEntry.getValue();

                                    if (otherPU.getContent() == otherAttr) {
                                        System.out.println("Found PU == otherAttr");
                                        int rowCost = aTab.getRowCount();
                                        int otherRowCost = inSession.getTable(otherTable).getRowCount();
                                        double weight = (double)rowCost * (double)otherRowCost;
                                        System.out.println("RowCost = " + rowCost + ", otherRowCost = " + otherRowCost + ", weight = " + weight);

                                        EdgeUnit aEU = new EdgeUnit();
                                        aEU.setJoinDef(curJD);
                                        joinGraph.setEdgeWeight(aEU, weight);
                                        joinGraph.addEdge(pu, otherPU, aEU);
                                    }
                                }
                            }                                                

                            HashMap<String, Metric> otherMetricHT = stage.getMetricHTByTable(otherTable);
                            HashMap<String, Metric> allOtherMetrics = otherMetricHT;
                            Map<String, Metric> allOtherMetricsMap = allOtherMetrics;
                            for (Map.Entry<String, Metric> allOtherMetricsEntry : allOtherMetricsMap.entrySet()) {
                                Metric otherMetric = allOtherMetricsEntry.getValue();

                                System.out.println("Other Metric: " + otherMetric.getName());
                                Map<UUID, ProcessingUnit> OtherPUMap = allPUs;
                                for (Map.Entry<UUID, ProcessingUnit> otherPUEntry : OtherPUMap.entrySet()) {
                                    ProcessingUnit otherPU = otherPUEntry.getValue();

                                    if (otherPU.getContent() == otherMetric) {
                                        System.out.println("Found PU == otherMetric");
                                        int rowCost = aTab.getRowCount();
                                        int otherRowCost = inSession.getTable(otherTable).getRowCount();
                                        double weight = (double)rowCost * (double)otherRowCost;
                                        System.out.println("RowCost = " + rowCost + ", otherRowCost = " + otherRowCost + ", weight = " + weight);

                                        EdgeUnit aEU = new EdgeUnit();
                                        aEU.setJoinDef(curJD);
                                        joinGraph.setEdgeWeight(aEU, weight);
                                        joinGraph.addEdge(pu, otherPU, aEU);
                                    }
                                }                            
                            }                       
                        }
                    }
                }
            }
        }

        // dump graph
        System.out.println("#### Before remove extra edges...");
        dumpGraph(joinGraph);        
        
        // loop each vertex and remove edges that have same definition until one left        
        removeExtraEdges(joinGraph);
        
        // dump graph
        System.out.println("#### After remove extra edges...");
        dumpGraph(joinGraph);
        
        // mst algo
        KruskalMinimumSpanningTree kmt = new KruskalMinimumSpanningTree(joinGraph);
        System.out.println("kmt total cost: " + kmt.getMinimumSpanningTreeTotalWeight());
        Set<EdgeUnit> euSet = kmt.getMinimumSpanningTreeEdgeSet();
        for (EdgeUnit eu : euSet) {
            System.out.println("Edge joindef name = " + eu.getJoinDef().getName() + ", weight = " + eu.getWeight());
        }
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
            for (int i = 0; i <sizeJDRemoveAL; i++) {
                JDRemoveUnit curJDRemoveUnit = jdRemoveAL.get(i);
                System.out.println("JoinDef: " + curJDRemoveUnit.getJoinDef().getName() + ", usedCount = " + curJDRemoveUnit.getUsedCount());
                ArrayList<ProcessingUnit> curJDRemoveUnitLinkedPUAL = curJDRemoveUnit.getLinkedPU();                
                int sizeCurJDRemoveUnitLinkedPUAL = curJDRemoveUnitLinkedPUAL.size();
                
                if (sizeCurJDRemoveUnitLinkedPUAL > 1) {
                    int difference = curJDRemoveUnit.getUsedCount() - 1;
                    
                    Collections.sort(curJDRemoveUnitLinkedPUAL);
                    
                    // dump
                    for (int j = 0; j<sizeCurJDRemoveUnitLinkedPUAL; j++) {
                        ProcessingUnit curPU = curJDRemoveUnitLinkedPUAL.get(j);
                        System.out.println("  LinkedPU: " + curPU.getContent() + ", removeCount = " + curPU.getRemoveCount());
                    }
                    
                    // try to remove from the head of ArrayList because it is sorted
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
    
    private void dumpGraph(WeightedMultigraph<ProcessingUnit, EdgeUnit> inGraph) {
        System.out.println("### Dumping graph...");
        Set<ProcessingUnit> graphVertexSet = inGraph.vertexSet();
        int vertexCount = 0;
        for (ProcessingUnit pu : graphVertexSet) {
            System.out.println("  Vertex = " + vertexCount + " : " + pu.getContent());
            Set<EdgeUnit> graphEdgeSet = inGraph.edgesOf(pu);
            
            int edgeCount = 0;
            for (EdgeUnit eu : graphEdgeSet) {
                System.out.println("    Edge = " + edgeCount + " : " + eu.joinDef.getName());
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
