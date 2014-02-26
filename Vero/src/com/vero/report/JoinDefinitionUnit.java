/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.report;

import com.vero.metadata.JoinDefinition;
import java.util.UUID;

/**
 *
 * @author yulinwen
 */
public class JoinDefinitionUnit {
    private final UUID uuid;
    private final UUID tleft;
    private final UUID tright;
    private final JoinDefinition.JoinType jtype;
    
    JoinDefinitionUnit(UUID uuid, UUID tleft, UUID tright, JoinDefinition.JoinType jtype) {
        this.uuid = uuid;
        this.tleft = tleft;
        this.tright = tright;
        this.jtype = jtype;
    }
    
    public UUID getUUID() {
        return uuid;
    }
    
    public UUID getTleft() {
        return tleft;
    }
    
    public UUID getTright() {
        return tright;
    }
    
    public JoinDefinition.JoinType getJtype() {
        return jtype;
    }
}
