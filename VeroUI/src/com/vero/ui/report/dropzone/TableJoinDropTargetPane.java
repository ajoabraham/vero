/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.report.querypane.QueryPane;

import static com.vero.ui.constants.ObjectType.TABLE_JOIN;
import static com.vero.ui.constants.UIConstants.DEFAULT_DROP_PANE_HEIGHT;

/**
 *
 * @author Tai Hu
 */
public class TableJoinDropTargetPane extends DropTargetPane {
    
    public TableJoinDropTargetPane(QueryPane queryPane) {
        super(queryPane);
        setPrefHeight(DEFAULT_DROP_PANE_HEIGHT * 2 + 5);
    }
    
    @Override
    public ObjectType getType() {
        return TABLE_JOIN;
    }
}
