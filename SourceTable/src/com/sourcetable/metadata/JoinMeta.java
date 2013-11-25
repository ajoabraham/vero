/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sourcetable.metadata;

import java.util.UUID;

/**
 *
 * @author yulinwen
 */
public class JoinMeta {
    UUID uuid;
    String name;
    String tleft;
    String cleft;
    String tright;
    String cright;
    String operator;
    String expression;
    // JoinType type;
    String type;
    
    public JoinMeta(String inName, String inTL, String inCL, String inOp, String inTR, String inCR, String inExp, String inType) {
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