/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.TABLE;

/**
 *
 * @author Tai Hu
 */
public class TableDropPane extends DropPane {

    public TableDropPane() {
        
    }
    
    @Override
    public ObjectType getType() {
        return TABLE;
    }    
}
