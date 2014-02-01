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
    private String name = null;
    private String tleft = null;
    private String cleft = null;
    private String tright = null;
    private String cright = null;
    private String operator = null;
    private String expression = null;
    // JoinType type;
    private String type = null;
    
    public enum JoinType {
        NONE,
        INNER,
        OUTER,
        LEFT,
        RIGHT
    }
        
    public JoinDefinition(UUID uuid, String name, String tleft, String cleft, String operator, String tright, String cright, String expression, String type) {
        this.uuid = uuid;
        this.name = name;
        this.tleft = tleft;
        this.cleft = cleft;
        this.operator = operator;
        this.tright = tright;
        this.cright = cright;
        this.expression = expression;
        this.type = type;
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
    
    public String getType() {
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