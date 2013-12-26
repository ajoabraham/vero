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
        
        @Override
        public double getWeight() {
            return super.getWeight();
        }
    }
    
    private Stage stage;
    private WeightedMultigraph<ProcessingUnit, EdgeUnit> joinGraph;
    
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
            
            joinGraph.addVertex(pu);
        }
        
        // loop on PUs
        // for each table, find where it is used and connect vertex and create edges
        for (Map.Entry<UUID, ProcessingUnit> entry : puMap.entrySet()) {
            ProcessingUnit pu = entry.getValue();
            System.out.println("curPD = " + pu);
            ArrayList<Table> listTables = pu.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                    Table aTab = iterTable.next();
                    System.out.println("Processing table: " + aTab.getPhysicalName());
                    HashMap<String, JoinDefinition> tabJD = stage.getJoindefHTByTable(aTab.getPhysicalName());
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
                        }                                                
                        
                        HashMap<String, Metric> otherMetricHT = stage.getMetricHTByTable(otherTable);
                        HashMap<String, Metric> allOtherMetrics = otherMetricHT;
                        Map<String, Metric> allOtherMetricsMap = allOtherMetrics;
                        for (Map.Entry<String, Metric> allOtherMetricsEntry : allOtherMetricsMap.entrySet()) {
                            Metric otherMetric = allOtherMetricsEntry.getValue();
                            
                            System.out.println("Other Metric: " + otherMetric.getName());
                        }
                    }                    
                }
            }
        }
        
        // loop each vertex and remove edges that have same definition until one left        
        
        // mst algo
        //KruskalMinimumSpanningTree kmt = new KruskalMinimumSpanningTree(joinGraph);
        //System.out.println("kmt total cost: " + kmt.getMinimumSpanningTreeTotalWeight());        
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
