/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.Attribute;
import com.vero.metadata.JoinDefinition;
import com.vero.metadata.Metric;
import com.vero.session.Session;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yulinwen
 */
public class Stage {      
    private class ReferenceUnit {
        private HashMap<String, Attribute> attrHT;
        private HashMap<String, Metric> metricHT;
        private HashMap<String, JoinDefinition> joindefHT;
    }
     
    private HashMap<String, ReferenceUnit> table2ReferenceUnitHT;
    
    public Stage() {
        table2ReferenceUnitHT = new HashMap();
    }
    
    public void preprocess(Session inSession) {
        HashMap inAttr = inSession.getAttributes();
        HashMap inMetric = inSession.getMetrics();
        HashMap inJoindef = inSession.getJoins();
        
        Map<String, Attribute> attrMap = inAttr;
        for (Map.Entry<String, Attribute> entry : attrMap.entrySet()) {
            System.out.println("Attr Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        
        Map<String, Metric> metMap = inMetric;
        for (Map.Entry<String, Metric> entry : metMap.entrySet()) {
            System.out.println("Met Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        
        Map<String, Joindef> jdMap = inJoindef;
        for (Map.Entry<String, Joindef> entry : jdMap.entrySet()) {
            System.out.println("JD Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }
}
