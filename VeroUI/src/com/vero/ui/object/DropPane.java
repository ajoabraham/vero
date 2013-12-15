/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.LabelPaneFactory;
import static com.vero.ui.common.CSSConstants.CLASS_DROP_PANE;
import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.UIConstants.DEFAULT_DROP_PANE_HEIGHT;
import static com.vero.ui.common.UIConstants.DEFAULT_LABEL_PANE_HEIGHT;
import com.vero.ui.model.UIData;
import javafx.scene.Node;
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
    public Node getDropTarget() {
        return this;
    }

    @Override
    public boolean acceptDrop(UIData transferData) {
        return getType() == transferData.getType();
    }

    
    @Override
    public void handleDragOverEvent(DragEvent event) {    
    }

    @Override
    public void handleDragEnteredEvent(DragEvent event) {
        setPrefHeight((getChildren().size() + 1) * (DEFAULT_LABEL_PANE_HEIGHT + 5));
        setStyle("-fx-background-color: -fx-light-grey-color;");
    }

    @Override
    public void handleDragExitedEvent(DragEvent event) {
        setPrefHeight(getChildren().size() * (DEFAULT_LABEL_PANE_HEIGHT + 5));
        setStyle("-fx-background-color: transparent;");
    }

    @Override
    public void handleDragDroppedEvent(DragEvent event, UIData transferData) {
        ObjectPane objectPane = LabelPaneFactory.getInstance().createObjectPane(getType(), transferData, false);
        getChildren().add(objectPane);
        setPrefHeight(getChildren().size() * (DEFAULT_LABEL_PANE_HEIGHT + 5));
    }
}
