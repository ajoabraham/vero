/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.TABLE_JOIN;
import static com.vero.ui.common.UIConstants.DEFAULT_DROP_PANE_HEIGHT;

/**
 *
 * @author Tai Hu
 */
public class TableJoinDropPane extends DropPane {
    public TableJoinDropPane() {
        setPrefHeight(DEFAULT_DROP_PANE_HEIGHT * 2 + 5);
    }
    
    @Override
    public ObjectType getType() {
        return TABLE_JOIN;
    }
}
