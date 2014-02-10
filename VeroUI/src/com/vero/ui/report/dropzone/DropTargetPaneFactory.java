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

/**
 *
 * @author Tai Hu
 */
public final class DropTargetPaneFactory {
    private static final Logger logger = Logger.getLogger(DropTargetPaneFactory.class.getName());
    
    private DropTargetPaneFactory() {
        
    }
    
    public static DropTargetPane createDropPane(ObjectType type, DropZonePane dropZonePane, boolean isDroppable) {
        DropTargetPane dropPane = null;
        
        switch (type) {
            case TABLE:
                dropPane = new TableDropTargetPane(dropZonePane);
                break;
            case ATTRIBUTE:
                dropPane = new AttributeDropTargetPane(dropZonePane);
                break;
            case METRIC:
                dropPane = new MetricDropTargetPane(dropZonePane);
                break;
            case TABLE_JOIN:
                dropPane = new TableJoinDropTargetPane(dropZonePane);
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
