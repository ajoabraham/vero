/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.ExpressionUnit;
import com.vero.metadata.Attribute;
import com.vero.metadata.Expression;
import com.vero.metadata.Metric;
import com.vero.metadata.Table;
import static com.vero.queryengine.ProcessingUnit.PUType.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author yulinwen
 */
public class ProcessingUnit implements Comparable<ProcessingUnit> {
    static private int PU_ID = 0;
    static private int ALIAS_ID = 0;
    
    public static enum PUType {
        PUTYPE_NONE,
        PUTYPE_ATTRIBUTE,
        PUTYPE_METRIC,
        PUTYPE_HARDHINT
    }

    private final UUID uuid = UUID.randomUUID();
    private final int id = PU_ID++;
    private PUType type = PUTYPE_NONE;
    private Object content = null;
    
    // update during processing
    private int removeCount = 0;
    private String tableAlias = null;
    private ExpressionUnit usedExp = null;
    private ProcessingUnit masterPU = this;
    private Boolean processed = false;    
    
    public ProcessingUnit() {}

    public UUID getUUID() {
        return uuid;
    }
    
    public int getID() {
        return id;
    }
    
    public static void resetID() {
        PU_ID = 0;
        ALIAS_ID = 0;
    }
    
    public void setType(PUType inType) {
        type = inType;
    }

    public PUType getType() {
        return type;
    }

    public void setContent(Object inContent) {
        content = inContent;
    }

    public Object getContent() {
        return content;
    }
    
    public void setRemoveCount(int setValue) {
        removeCount = setValue;
    }
    
    public int getRemoveCount() {
        return removeCount;
    }

    public void setTableAlias(String inAlias) {
        masterPU.tableAlias = inAlias;
    }

    public String assignTableAlias() {
        if (masterPU.tableAlias == null) {
            setTableAlias("T" + ALIAS_ID);
            ALIAS_ID++;
        }
        
        return masterPU.tableAlias;
    }

     public String getTableAlias() {        
        return masterPU.tableAlias;
    }   
    
    public void setUsedExp(ExpressionUnit usedExp) {
        this.usedExp = usedExp;
    }
    
    public ExpressionUnit getUsedExp() {
        return usedExp;
    }

    public void setMasterPU(ProcessingUnit inPU) {
        masterPU = inPU;
    }
    
    public ProcessingUnit getMasterPU() {
        return masterPU;
    }
    
    public void setProcessed(Boolean inProc) {
        processed = inProc;
    }
    
    public Boolean getProcessed() {
        return processed;
    }
    
    public List<Table> retrieveTables() {
        if (type == PUTYPE_ATTRIBUTE) {
            return ((Attribute)content).retrieveTables();
        } else if (type == PUTYPE_METRIC) {
            return ((Metric)content).retrieveTables();
        } else if (type == PUTYPE_HARDHINT) {
            List<Table> aAL = new ArrayList();
            aAL.add((Table)content);
            return aAL;
        } else {
            return null;
        }
    }
    
    @Override
    public int compareTo(ProcessingUnit inPU) {
        if (this.id < inPU.id) {
            return -1;
        } else if (this.id > inPU.id) {
            return 1;
        } else {
            return 0;
        }
    }
}
