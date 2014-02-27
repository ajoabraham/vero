/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.metadata;

import java.util.UUID;

/**
 *
 * @author Yulin Wen
 */
public class JoinDefinition {
    private UUID uuid = null;
    private String uuidStr = null;
    private String name = null;
    private String tleft = null;
    private String tleftuuidStr = null;
    private String cleft = null;
    private String tright = null;
    private String trightuuidStr = null;
    private String cright = null;
    private String operator = null;
    private String expression = null;
    JoinType type;   
    
    public enum JoinType {
        NONE,
        INNER,
        OUTER,
        LEFT,
        RIGHT,
        CROSS
    }
        
    public JoinDefinition(
            UUID uuid, 
            String uuidStr,
            String name, 
            String tleft, 
            String tleftUUID,
            String cleft, 
            String operator, 
            String tright, 
            String trightUUID,
            String cright, 
            String expression, 
            JoinType type) {
        this.uuid = uuid;
        this.uuidStr = uuidStr;
        this.name = name;
        this.tleft = tleft;
        this.tleftuuidStr = tleftUUID;
        this.cleft = cleft;
        this.operator = operator;
        this.tright = tright;
        this.trightuuidStr = trightUUID;
        this.cright = cright;
        this.expression = expression;
        this.type = type;
    }
    
    public UUID getUUID() {
        return uuid;
    }

    public String getUUIDStr() {
        return uuidStr;
    }
    
    public String getTLeftStr() {
        return tleftuuidStr;
    }
    
    public String getTRightStr() {
        return trightuuidStr;
    }    
    
    public String getName() {
        return name;
    }
    
    public String getTLeft() {
        return tleft;
    }

    public String getCLeft() {
        return cleft;
    }
    
    public String getTRight() {
        return tright;
    }
    
    public String getCRight() {
        return cright;
    }

    public String getOperator() {
        return operator;
    }
    
    public JoinType getType() {
        return type;
    }

    public String getExpression() {
        return expression;
    }

    public String getOtherTable(String inTable) {
        if (this.hasTable(inTable)) {
            if (inTable.equals(tleft)) {
                return tright;
            } else {
                return tleft;
            }
        } else {
            return null;
        }
    }
    
    public boolean hasTable(String inTable) {
        return (inTable.equals(tleft) || inTable.equals(tright));
    }
}