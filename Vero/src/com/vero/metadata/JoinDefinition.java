/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.metadata;

import java.util.UUID;

/**
 *
 * @author yulinwen
 */
public class JoinDefinition {
    private UUID uuid;
    private String name;
    private String tleft;
    private String cleft;
    private String tright;
    private String cright;
    private String operator;
    private String expression;
    // JoinType type;
    private String type;
    
    public enum JoinType {
        NONE,
        INNER,
        OUTER,
        LEFT,
        RIGHT
    }
        
    public JoinDefinition(String inName, String inTL, String inCL, String inOp, String inTR, String inCR, String inExp, String inType) {
        uuid = UUID.randomUUID();
        name = inName;
        tleft = inTL;
        cleft = inCL;
        operator = inOp;
        tright = inTR;
        cright = inCR;
        expression = inExp;
        type = inType;
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
}