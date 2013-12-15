/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.Attribute;
import com.vero.metadata.JoinDefinition;
import com.vero.metadata.Metric;
import java.util.HashMap;

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
}
