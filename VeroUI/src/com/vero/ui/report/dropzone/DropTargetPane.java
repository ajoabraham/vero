/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_DROP_PANE;
import static com.vero.ui.constants.UIConstants.DEFAULT_DROP_PANE_HEIGHT;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.VBox;

import com.vero.ui.common.DroppableObject;
import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.UIData;

/**
 *
 * @author Tai Hu
 */
public abstract class DropTargetPane extends VBox implements DroppableObject {
    private static final Logger logger = Logger.getLogger(DropTargetPane.class.getName());
    
    private static final String ATTRIBUTE_PLACEHOLDER_HINT = "drag attributes or columns here...";
    private static final String METRIC_PLACEHOLDER_HINT = "drag metrics or columns here...";
    private static final String TABLE_PLACEHOLDER_HINT = "drag tables here as query hints...";
    private static final String TABLE_JOIN_PLACEHOLDER_HINT = "This is not a drop zone...";
        
    private LabelPaneFactory labelPaneFactory = null;
    
    private int currentDropIndex = -1;
    private boolean isEmpty = false;
    
    public DropTargetPane() {
        labelPaneFactory = LabelPaneFactory.getInstance();
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
        if (!isEmpty) {
            int index = computeDropIndex(event.getY());
            List<Node> children = getChildren();
if (currentDropIndex != index) System.out.println("Index = " + index + " current index = " + currentDropIndex);            
            if (index <= children.size()) {
                // Very first time
                if (currentDropIndex == -1) {
                    double newHeight = computePrefHeight(children.size() + 1);
                    setPrefHeight(newHeight);
                    children.add(index, labelPaneFactory.createDropHintPane());
                    currentDropIndex = index;
                }
                else if (index > currentDropIndex + 1 || index < currentDropIndex - 1){
                    children.add(index, labelPaneFactory.createDropHintPane());
                    children.remove(currentDropIndex);
                    
                    currentDropIndex = index > currentDropIndex ? index - 1 : index;
                }
            }
        }
    }

    @Override
    public void handleDragEnteredEvent(DragEvent event) {
        List<Node> children = getChildren();
        
        // Empty drop pane
        if (children.get(0) instanceof PlaceholderPane) {
            children.set(0, labelPaneFactory.createDropHintPane());
            isEmpty = true;
        }
    }

    @Override
    public void handleDragExitedEvent(DragEvent event) {
        List<Node> children = getChildren();
        
        // Empty drop pane
        if (isEmpty) {
            children.set(0, labelPaneFactory.createPlaceholderPane(getPlaceholderText()));
        }
    }

    @Override
    public void handleDragDroppedEvent(DragEvent event, UIData transferData) {
        DropZoneObjectPane dropZoneObjectPane = LabelPaneFactory.getInstance().createDropZoneObjectPane(getType(), transferData);
        
        if (isEmpty) {
            getChildren().set(0, dropZoneObjectPane);
        }
        else {
            getChildren().set(currentDropIndex, dropZoneObjectPane);
        }
        
        isEmpty = false;
        currentDropIndex = -1;
    }
    
    public String getPlaceholderText() {
        String text = "";
        switch (getType()) {
            case ATTRIBUTE:
                text = ATTRIBUTE_PLACEHOLDER_HINT;
                break;
            case METRIC:
                text = METRIC_PLACEHOLDER_HINT;
                break;
            case TABLE:
                text = TABLE_PLACEHOLDER_HINT;
                break;
            case TABLE_JOIN:
                text = TABLE_JOIN_PLACEHOLDER_HINT;
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", getType());
        }
        
        return text;
    }
    
    private int computeDropIndex(double y) {
        double objectPaneHeight = 31;
        double spacing = getSpacing();
        double topPadding = getPadding().getTop();
//System.out.println("Object pane height = " + objectPaneHeight + " spacing = " + spacing + " padding = " + topPadding);        
        return (int) ((y - topPadding) / (objectPaneHeight + spacing));
    }
    
    private double computePrefHeight(int size) {
        double objectPaneHeight = getChildren().get(0).getBoundsInLocal().getHeight();
        double spacing = getSpacing();
        double padding = getPadding().getTop() + getPadding().getBottom();
        
        return padding + (objectPaneHeight * size) + (spacing * (size - 1));
    }
}
