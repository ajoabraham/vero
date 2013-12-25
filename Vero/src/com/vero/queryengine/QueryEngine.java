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
import static com.vero.queryengine.QueryEngine.VertexType.*;
import com.vero.session.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
    
    public static enum VertexType {
        VERTEX_TYPE_NONE,
        VERTEX_TYPE_ATTRIBUTE,
        VERTEX_TYPE_METRIC,
        VERTEX_TYPE_HARDHINT
    }
    
    private class VertexUnit {
        private VertexType type;
        private Object content;
        
        public VertexUnit() {
            type = VERTEX_TYPE_NONE;
            content = null;
        }
        
        public void setType(VertexType inType) {
            type = inType;
        }
        
        public VertexType getType() {
            return type;
        }
        
        public void setContent(Object inContent) {
            content = inContent;
        }
        
        public ArrayList<Table> retrieveTables() {
            if (type == VERTEX_TYPE_ATTRIBUTE) {
                return ((Attribute)content).retrieveTables();
            } else if (type == VERTEX_TYPE_METRIC) {
                return ((Metric)content).retrieveTables();
            } else if (type == VERTEX_TYPE_HARDHINT) {
                System.out.println("VERTEX_TYPE_HARDHINT not implemented yet");
                return null;
            } else {
                return null;
            }
        }
    }
        
    private Stage stage;
    private WeightedMultigraph<Attribute, EdgeUnit> joinGraph;
    
    public QueryEngine() {
        stage = new Stage();
        joinGraph = new WeightedMultigraph(new ClassBasedEdgeFactory<Attribute, EdgeUnit>(EdgeUnit.class));                
    }
            
    public void preprocess(Session inSession) {
        stage.preprocess(inSession);

        //expirimentOnGraph();
        
        // loop on attributes/metrics
        HashMap<String, Attribute> allAttrs = stage.getAttributes();
        Map<String, Attribute> attrMap = allAttrs;
        for (Map.Entry<String, Attribute> entry : attrMap.entrySet()) {
            Attribute attr = entry.getValue();
             
            ArrayList<Table> listTables = attr.retrieveTables();
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {                    
                }
            }
        }
        
        HashMap<String, Metric> allMetrics = stage.getMetrics();
        Map<String, Metric> metricMap = allMetrics;
        for (Map.Entry<String, Metric> entry : metricMap.entrySet()) {
            Metric met = entry.getValue();
            
            ArrayList<Table> listTables = met.retrieveTables();            
            if (listTables.size() > 0) {
                Iterator<Table> iterTable = listTables.iterator();

                while (iterTable.hasNext()) {
                }
            }            
        }
        
        // for each expression, find tables used and connect vertex and create edges
        
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
