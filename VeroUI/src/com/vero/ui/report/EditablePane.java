/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import com.vero.ui.LabelPane;
import static com.vero.ui.common.CSSConstants.CLASS_EDITABLE_PANE;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author Tai Hu
 */
public abstract class EditablePane extends LabelPane implements EventHandler<Event>, ChangeListener<Boolean> {
    private String text = null;
    private TextField textField = null;
    private Label label = null;
    private boolean editable = false;
    
    public EditablePane(String text) {
        this.text = text;
        getStyleClass().add(CLASS_EDITABLE_PANE);        
    
        label = new Label(text);
        getChildren().add(label);
    }

    @Override
    public void handle(Event event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED 
                && ((MouseEvent)event).getClickCount() == 2 && !editable) {
            startEdit();
        }
        else if (event.getEventType() == KeyEvent.KEY_PRESSED
                && ((KeyEvent)event).getCode() == KeyCode.ENTER && editable) {
            commitEdit();
        }
    }
    
    private void startEdit() {
        editable = true;
        if (textField == null) {
            textField = new TextField(text);
            textField.focusedProperty().addListener(this);
            HBox.setHgrow(textField, Priority.ALWAYS);
        }
        else {
            textField.setText(text);
        }
        
        getChildren().setAll(textField);
        textField.requestFocus();
    }
    
    private void commitEdit() {
        text = textField.getText().trim();
        label.setText(text);
        getChildren().setAll(label);
        editable = false;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue == false) {
            cancelEdit();
        }
    }
    
    private void cancelEdit() {
        label.setText(text);
        getChildren().setAll(label);
        editable = false;
    }
    
    final void enableEditting() {
        setOnMouseClicked(this);
        setOnKeyPressed(this);
    }
}
