/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import static com.vero.ui.common.CSSConstants.CLASS_DROP_PANE;
import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.UIConstants.DEFAULT_DROP_PANE_HEIGHT;
import static com.vero.ui.common.UIConstants.OBJECT_PANE_HEIGHT;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public abstract class DropPane extends VBox implements DroppableObject {
    public DropPane() {
        getStyleClass().add(CLASS_DROP_PANE);
        setPrefHeight(DEFAULT_DROP_PANE_HEIGHT);
        setMaxHeight(Double.MAX_VALUE);
    }
    
    public abstract ObjectType getType();

    @Override
    public DropPane getDropTarget() {
        throw new UnsupportedOperationException("Override this method to support this function."); 
    }

    @Override
    public void handleDragOverEvent(DragEvent event) {    
    }

    @Override
    public void handleDragEnteredEvent(DragEvent event) {
    }

    @Override
    public void handleDragExitedEvent(DragEvent event) {
    }

    @Override
    public void handleDragDroppedEvent(DragEvent event) {
        setPrefHeight((getChildren().size() + 1) * OBJECT_PANE_HEIGHT);
    }
}
