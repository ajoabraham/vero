/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vero.ui.common.DropManager;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.report.ReportPane;

/**
 *
 * @author Tai Hu
 */
public final class DropTargetPaneFactory {
    private static final Logger logger = Logger.getLogger(DropTargetPaneFactory.class.getName());
    
    private DropTargetPaneFactory() {
        
    }
    
    public static DropTargetPane createDropPane(ReportPane reportPane, DropZonePane dropZonePane, ObjectType type, boolean isDroppable) {
        DropTargetPane dropPane = null;
        
        switch (type) {
            case TABLE:
                dropPane = new TableDropTargetPane(reportPane, dropZonePane);
                break;
            case ATTRIBUTE:
                dropPane = new AttributeDropTargetPane(reportPane, dropZonePane);
                break;
            case METRIC:
                dropPane = new MetricDropTargetPane(reportPane, dropZonePane);
                break;
            case TABLE_JOIN:
                dropPane = new TableJoinDropTargetPane(reportPane, dropZonePane);
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", type);
        }
        
        if (isDroppable) {
            DropManager.newInstance(dropPane);
        }
        
        return dropPane;
    }
}
