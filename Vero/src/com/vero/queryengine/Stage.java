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
        
        Map<String, Attribute> map = inAttr;
        for (Map.Entry<String, Attribute> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());                        
        }
    }
}
