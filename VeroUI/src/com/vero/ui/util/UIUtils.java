/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.util;

import static com.vero.ui.constants.CSSConstants.CLASS_ATTRIBUTE_ICON_LABEL;
import static com.vero.ui.constants.CSSConstants.CLASS_DEFAULT_BUTTON;
import static com.vero.ui.constants.CSSConstants.CLASS_DEFAULT_BUTTON_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_FORM_LABEL;
import static com.vero.ui.constants.CSSConstants.CLASS_FORM_TEXT_FIELD;
import static com.vero.ui.constants.CSSConstants.CLASS_METRIC_ICON_LABEL;
import static com.vero.ui.constants.CSSConstants.CLASS_SELECTED_TOOL_BAR_BUTTON;
import static com.vero.ui.constants.CSSConstants.CLASS_TABLE_JOIN_ICON_LABEL;
import static com.vero.ui.constants.CSSConstants.VERO_CSS_FILE;
import static com.vero.ui.constants.UIConstants.DEFAULT_BUTTON_HEIGHT;
import static com.vero.ui.constants.UIConstants.DEFAULT_BUTTON_WIDTH;
import static com.vero.ui.constants.UIConstants.DEFAULT_FORM_INPUT_HEIGHT;
import static com.vero.ui.constants.UIConstants.DEFAULT_FORM_INPUT_WIDTH;
import static com.vero.ui.constants.UIConstants.DEFAULT_FORM_LABEL_WIDTH;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBoxBuilder;

import com.vero.ui.VeroUI;
import com.vero.ui.constants.ObjectType;


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
        
    public static String getEditorPaneIconStyleClass(ObjectType type) {
        String styleClass = "unknown";
        
        switch (type) {
            case ATTRIBUTE:
                styleClass = CLASS_ATTRIBUTE_ICON_LABEL;
                break;
            case METRIC:
                styleClass = CLASS_METRIC_ICON_LABEL;
                break;
            case TABLE_JOIN:
        	styleClass = CLASS_TABLE_JOIN_ICON_LABEL;
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
    
    public static Button createDefaultButton(String text, Node graphic, double width) {
	Button button = (graphic == null ? new Button(text) : new Button(text, graphic));
	button.setPrefSize(width, DEFAULT_BUTTON_HEIGHT);
	button.getStyleClass().add(CLASS_DEFAULT_BUTTON);
	
	return button;
    }
    
    public static Button createDefaultButton(String text, Node graphic) {
	return createDefaultButton(text, graphic, DEFAULT_BUTTON_WIDTH);
    }
    
    public static Button createDefaultButton(String text) {
	return createDefaultButton(text, null);
    }
    
    public static HBox createDefaultButtonPane() {
	HBox buttonPane = new HBox();
	buttonPane.getStyleClass().add(CLASS_DEFAULT_BUTTON_PANE);
	
	return buttonPane;
    }
    
    public static Label createDefaultFormLabel(String text) {
        return createDefaultFormLabel(text, DEFAULT_FORM_LABEL_WIDTH);
    }
    
    public static Label createDefaultFormLabel(String text, double width) {
        Label label = new Label(text);
        
        label.getStyleClass().add(CLASS_FORM_LABEL);
        label.setPrefWidth(width);
        
        return label;
    }
    
    public static TextField createDefaultFormTextField() {
        return createDefaultFormTextField(DEFAULT_FORM_INPUT_WIDTH, DEFAULT_FORM_INPUT_HEIGHT);
    }
    
    public static TextField createDefaultFormTextField(double width, double height) {
        TextField textField = new TextField();
        textField.setPrefSize(width, height);
        textField.getStyleClass().add(CLASS_FORM_TEXT_FIELD);
        
        return textField;
    }
    
    public static PasswordField createDefaultFormPasswordField() {
        return createDefaultFormPasswordField(DEFAULT_FORM_INPUT_WIDTH, DEFAULT_FORM_INPUT_HEIGHT);
    }
    
    public static PasswordField createDefaultFormPasswordField(double width, double height) {
	PasswordField passwordField = new PasswordField();
        passwordField.setPrefSize(width, height);
        passwordField.getStyleClass().add(CLASS_FORM_TEXT_FIELD);
        
        return passwordField;
    }
}
