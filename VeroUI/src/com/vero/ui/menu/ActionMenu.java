/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * This Menu will fire action event when clicked.
 * 
 * @author Tai Hu
 */
public class ActionMenu extends Menu implements EventHandler<MouseEvent> {
    private Label label = null;
    private EventHandler<ActionEvent> actionEventHandler = null;
    
    public ActionMenu() {
        
    }
    
    public ActionMenu(String text) {
        this(text, null);
    }
    
    public ActionMenu(String text, Node graphic) {
        super(null, new Label(text, graphic));
        label = (Label) getGraphic();
    }
    
    public void setOnMenuAction(EventHandler<ActionEvent> eventHandler) {
        actionEventHandler = eventHandler;
        label.setOnMouseClicked(this);
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY
                && event.getClickCount() == 1) {
            actionEventHandler.handle(new ActionEvent(this, event.getTarget()));
        }
    }
 }
