/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.ObjectType.TABLE;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.report.ReportPane;

/**
 *
 * @author Tai Hu
 */
public class TableDropTargetPane extends DropTargetPane {

    public TableDropTargetPane(ReportPane reportPane, DropZonePane dropZonePane) {
        super(reportPane, dropZonePane);
    }
    
    @Override
    public ObjectType getType() {
        return TABLE;
    }   
}
