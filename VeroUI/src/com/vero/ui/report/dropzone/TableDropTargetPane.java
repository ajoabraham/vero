/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.report.querypane.QueryPane;

import static com.vero.ui.constants.ObjectType.TABLE;

/**
 *
 * @author Tai Hu
 */
public class TableDropTargetPane extends DropTargetPane {

    public TableDropTargetPane(QueryPane queryPane) {
        super(queryPane);
    }
    
    @Override
    public ObjectType getType() {
        return TABLE;
    }    
}
