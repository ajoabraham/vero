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
    String tright;
    String expression;
    // JoinType type;
    String type;
    
    public JoinMeta(String inName, String inL, String inR, String inExp, String inType) {
        uuid = UUID.randomUUID();
        name = inName;
        tleft = inL;
        tright = inR;
        expression = inExp;
        type = inType;
    }
    
    public String getName() {
        return name;
    }
}
