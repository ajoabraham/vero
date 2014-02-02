/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.report;

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
    private final Map<UUID, UUID> attributeMap = new HashMap();
    private final Map<UUID, UUID> metricMap = new HashMap();
    private final List<UUID> joindefList = new ArrayList();
    
    public Block() {

    }
        
    public String getSqlString() {
        return this.sqlString;
    }
    
    public void setSqlString(String inSqlString) {
        this.sqlString = inSqlString;
    }
}
