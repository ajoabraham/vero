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
import com.vero.ui.report.DropHintPane;
import com.vero.ui.report.PlaceholderPane;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public abstract class DropPane extends VBox implements DroppableObject {
    private static final Logger logger = Logger.getLogger(DropPane.class.getName());
    
    private static final String ATTRIBUTE_PLACEHOLDER_HINT = "drag attributes or columns here...";
    private static final String METRIC_PLACEHOLDER_HINT = "drag metrics or columns here...";
    private static final String TABLE_PLACEHOLDER_HINT = "drag tables here as query hints...";
    private static final String TABLE_JOIN_PLACEHOLDER_HINT = "This is not a drop zone...";
    
    private LabelPaneFactory labelPaneFactory = null;
    
    public DropPane() {
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
    }

    @Override
    public void handleDragEnteredEvent(DragEvent event) {
        List<Node> children = getChildren();
        
        // Empty drop pane
        if (children.get(0) instanceof PlaceholderPane) {
            children.set(0, labelPaneFactory.createDropHintPane());
        }
        else {
            children.add(labelPaneFactory.createDropHintPane());
            setPrefHeight(children.size() * (DEFAULT_LABEL_PANE_HEIGHT + 5));
        }
    }

    @Override
    public void handleDragExitedEvent(DragEvent event) {
        List<Node> children = getChildren();
        
        // Empty drop pane
        if (children.size() == 1 && children.get(0) instanceof DropHintPane) {
            children.set(0, labelPaneFactory.createPlaceholderPane(getPlaceholderText()));
        }
        else {
            children.remove((children.size() - 1));
            setPrefHeight(children.size() * (DEFAULT_LABEL_PANE_HEIGHT + 5));
        }
    }

    @Override
    public void handleDragDroppedEvent(DragEvent event, UIData transferData) {
        ObjectPane objectPane = LabelPaneFactory.getInstance().createObjectPane(getType(), transferData, false);
        getChildren().add(getChildren().size() - 1, objectPane);
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
}
