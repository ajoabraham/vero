/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.Attribute;
import com.vero.metadata.Metric;
import com.vero.metadata.Table;
import static com.vero.queryengine.ProcessingUnit.PUType.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author yulinwen
 */
public class ProcessingUnit {
    public static enum PUType {
        PUTYPE_NONE,
        PUTYPE_ATTRIBUTE,
        PUTYPE_METRIC,
        PUTYPE_HARDHINT
    }

    private UUID uuid;
    private PUType type;
    private Object content;

    public ProcessingUnit() {
        uuid = UUID.randomUUID();
        type = PUTYPE_NONE;
        content = null;
    }

    public UUID getUUID() {
        return uuid;
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

    public ArrayList<Table> retrieveTables() {
        if (type == PUTYPE_ATTRIBUTE) {
            return ((Attribute)content).retrieveTables();
        } else if (type == PUTYPE_METRIC) {
            return ((Metric)content).retrieveTables();
        } else if (type == PUTYPE_HARDHINT) {
            System.out.println("PUTYPE_HARDHINT not implemented yet");
            return null;
        } else {
            return null;
        }
    }    
}
