/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.util;

import com.vero.ui.VeroUI;
import com.vero.ui.constants.ObjectType;

import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import static com.vero.ui.constants.CSSConstants.*;

import com.vero.ui.constants.ImageList;
import com.vero.ui.constants.TableJoinType;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBoxBuilder;

/**
 *
 * @author Tai Hu
 */
public final class UIUtils {
    private static final Logger logger = Logger.getLogger(UIUtils.class.getName());
    
    public static Pane createVerticalSpaceFiller(double height) {
        return HBoxBuilder.create().prefHeight(height).build();
    }
    
    public static Pane createHorizontalSpaceFiller(double width) {
        return VBoxBuilder.create().prefWidth(width).build();
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
            case TABLE_JOIN:
                styleClass = CLASS_TABLE_JOIN_OBJECT_PANE;
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", type);
        }
        
        return styleClass;
    }
    
    public static Image getTableJoinImage(TableJoinType type) {
        Image image = null;
        
        switch (type) {
            case FULL_OUTER_JOIN:
                image = ImageList.IMAGE_FULL_OUTER_JOIN;
                break;
            case CROSS_JOIN:
                image = ImageList.IMAGE_CROSS_JOIN;
                break;
            case INNER_JOIN:
                image = ImageList.IMAGE_INNER_JOIN;
                break;
            case LEFT_JOIN:
                image = ImageList.IMAGE_LEFT_JOIN;
                break;
            case RIGHT_JOIN:
                image = ImageList.IMAGE_RIGHT_JOIN;
                break;
            default:
                logger.log(Level.SEVERE, "Invalid table join type - {0}", type);
        }
        
        return image;
    }
    
    public static String getEditorPaneIconStyleClass(ObjectType type) {
        String styleClass = "unknown";
        
        switch (type) {
            case ATTRIBUTE:
                styleClass = CLASS_ATTRIBUTE_ICON_LABEL;
                break;
            case METRIC:
                styleClass = CLASS_METRIC_ICON_LABEL;
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", type);
        }
        
        return styleClass;
    }
    
    public static String getVeroCSSStyleSheet() {
        return VeroUI.class.getResource(VERO_CSS_FILE).toExternalForm();
    }
    
    public static void enableSelectedButtonStyle(Button button) {
	if (!button.getStyleClass().contains(CLASS_SELECTED_TOOL_BAR_BUTTON)) {
	    button.getStyleClass().add(CLASS_SELECTED_TOOL_BAR_BUTTON);
//	    button.setStyle("-fx-background-color: -fx-button-hover-color;-fx-background-radius: 5;-fx-background-insets: 0 4;");
	}
    }
    
    public static void disableSelectedButtonStyle(Button button) {
	if (button.getStyleClass().contains(CLASS_SELECTED_TOOL_BAR_BUTTON)) {
	    button.getStyleClass().remove(CLASS_SELECTED_TOOL_BAR_BUTTON);
//	    button.setStyle("");
	}
    }
}
