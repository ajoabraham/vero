/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.util;

import com.vero.ui.common.ObjectType;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;

/**
 *
 * @author Tai Hu
 */
public final class UIUtils {
    public static Pane createVerticalSpaceFiller(double height) {
        return HBoxBuilder.create().prefHeight(height).build();
    }
    
    public static String getObjectSytleClass(ObjectType type) {
        String styleClass = "unknown";
        
        switch (type) {
            case TYPE_ATTRIBUTE:
                styleClass = "object-attribute-pane";
                break;
            case TYPE_DATASOURCE:
                styleClass = "object-datasource-pane";
                break;
            case TYPE_TABLE:
                styleClass = "object-table-pane";
                break;
            case TYPE_METRICS:
                styleClass = "object-metrics-pane";
                break;
            case TYPE_UNUSED:
                styleClass = "object-unused-pane";
                break;
            default:
                System.err.println("Invalid object type - " + type);
        }
        
        return styleClass;
    }
}
