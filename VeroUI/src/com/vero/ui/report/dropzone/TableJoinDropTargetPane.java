/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.ObjectType.TABLE_JOIN;
import static com.vero.ui.constants.UIConstants.DEFAULT_DROP_PANE_HEIGHT;

import com.vero.ui.constants.ObjectType;

/**
 *
 * @author Tai Hu
 */
public class TableJoinDropTargetPane extends DropTargetPane {
    
    public TableJoinDropTargetPane(DropZonePane dropZonePane) {
        super(dropZonePane);
        setPrefHeight(DEFAULT_DROP_PANE_HEIGHT * 2 + 5);
    }
    
    @Override
    public ObjectType getType() {
        return TABLE_JOIN;
    }
}
