/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tai Hu
 */
public final class DropPaneFactory {
    private static final Logger logger = Logger.getLogger(DropPaneFactory.class.getName());
    
    private static DropPaneFactory INSTANCE = null;
    
    private DropPaneFactory() {
        
    }
    
    public static DropPaneFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DropPaneFactory();
        }
        
        return INSTANCE;
    }
    
    public DropPane createDropPane(ObjectType type, boolean isDroppable) {
        DropPane dropPane = null;
        
        switch (type) {
            case TABLE:
                dropPane = new TableDropPane();
                break;
            case ATTRIBUTE:
                dropPane = new AttributeDropPane();
                break;
            case METRIC:
                dropPane = new MetricDropPane();
                break;
            case TABLE_JOIN:
                dropPane = new TableJoinDropPane();
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
