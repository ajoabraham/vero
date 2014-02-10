/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.common.DropManager;
import com.vero.ui.report.querypane.QueryPane;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tai Hu
 */
public final class DropTargetPaneFactory {
    private static final Logger logger = Logger.getLogger(DropTargetPaneFactory.class.getName());
    
    private DropTargetPaneFactory() {
        
    }
    
    public static DropTargetPane createDropPane(ObjectType type, QueryPane queryPane, boolean isDroppable) {
        DropTargetPane dropPane = null;
        
        switch (type) {
            case TABLE:
                dropPane = new TableDropTargetPane(queryPane);
                break;
            case ATTRIBUTE:
                dropPane = new AttributeDropTargetPane(queryPane);
                break;
            case METRIC:
                dropPane = new MetricDropTargetPane(queryPane);
                break;
            case TABLE_JOIN:
                dropPane = new TableJoinDropTargetPane(queryPane);
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
