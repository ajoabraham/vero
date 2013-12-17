/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.common.DropManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tai Hu
 */
public final class DropTargetPaneFactory {
    private static final Logger logger = Logger.getLogger(DropTargetPaneFactory.class.getName());
    
    private static DropTargetPaneFactory INSTANCE = null;
    
    private DropTargetPaneFactory() {
        
    }
    
    public static DropTargetPaneFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DropTargetPaneFactory();
        }
        
        return INSTANCE;
    }
    
    public DropTargetPane createDropPane(ObjectType type, boolean isDroppable) {
        DropTargetPane dropPane = null;
        
        switch (type) {
            case TABLE:
                dropPane = new TableDropTargetPane();
                break;
            case ATTRIBUTE:
                dropPane = new AttributeDropTargetPane();
                break;
            case METRIC:
                dropPane = new MetricDropTargetPane();
                break;
            case TABLE_JOIN:
                dropPane = new TableJoinDropTargetPane();
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
