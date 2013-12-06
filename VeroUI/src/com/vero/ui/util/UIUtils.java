/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.util;

import com.vero.ui.common.ObjectType;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;

import static com.vero.ui.common.CSSConstants.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tai Hu
 */
public final class UIUtils {
    private static final Logger logger = Logger.getLogger(UIUtils.class.getName());
    
    public static Pane createVerticalSpaceFiller(double height) {
        return HBoxBuilder.create().prefHeight(height).build();
    }
    
    public static String getObjectSytleClass(ObjectType type) {
        String styleClass = "unknown";
        
        switch (type) {
            case DATASOURCE:
                styleClass = CLASS_DATASOURCE_OBJECT_PANE;
                break;
            case TABLE:
                styleClass = CLASS_TABLE_OBJECT_PANE;
                break;
            case COLUMN:
                styleClass = CLASS_COLUMN_OBJECT_PANE;
                break;
            case ATTRIBUTE:
                styleClass = CLASS_ATTRIBUTE_OBJECT_PANE;
                break;
            case METRIC:
                styleClass = CLASS_METRIC_OBJECT_PANE;
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", type);
        }
        
        return styleClass;
    }
}
