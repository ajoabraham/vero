/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import com.vero.ui.constants.ObjectType;
import static com.vero.ui.constants.ObjectType.REPORT;

/**
 *
 * @author Tai Hu
 */
public class ReportData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private String name = null;
    
    public ReportData() {
        
    }
    
    @Override
    public ObjectType getType() {
        return REPORT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
