/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.report;

import com.vero.metadata.JoinDefinition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author yulinwen
 */
public class Block {
    private String sqlString = null;
    private final Map<String, String> attributeMap = new HashMap(); // (UUID String of attribute, UUID String of expression)
    private final Map<String, String> metricMap = new HashMap(); // (UUID String of metric, UUID String of expression)
    private final Map<String, String> expressionMap = new HashMap(); // (UUID String of expression, UUID String of table)
    private final Map<String, String> tableMap = new HashMap(); // (UUID String of table, String of alias)
    private final List<JoinDefinition> joindefList = new ArrayList();
    
    public Block() {
    }
    
    public String getSqlString() {
        return sqlString;
    }
    
    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }
    
    public Map getAttributeMap() {
        return attributeMap;
    }
    
    public void addAttributeMap(UUID attribute, UUID expression) {
        attributeMap.put(attribute.toString(), expression.toString());
    }
    
    public Map getMetricMap() {
        return metricMap;
    }
    
    public void addMetricMap(UUID metric, UUID expression) {
        metricMap.put(metric.toString(), expression.toString());
    }

    public Map getExpressionMap() {
        return expressionMap;
    }
    
    public void addExpressionMap(UUID expression, UUID table) {
        expressionMap.put(expression.toString(), table.toString());
    }

    public Map getTableMap() {
        return tableMap;
    }
    
    public void addTableMap(UUID table, String alias) {
        tableMap.put(table.toString(), alias);
    }
    
    public List getJoinDefList() {
        return joindefList;
    }      

    public void addJoinDefList(JoinDefinition joinDef) {
        joindefList.add(joinDef);
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        
        result.append("Block: ").append(NEW_LINE);
        result.append("    sqlString = ").append(sqlString).append(NEW_LINE);
        result.append("    attributeMap size = ").append(attributeMap.size()).append(NEW_LINE);
        result.append("    metricMap size = ").append(metricMap.size()).append(NEW_LINE);
        result.append("    joindefList size = ").append(joindefList.size()).append(NEW_LINE);
        
        return result.toString();
    }
}
