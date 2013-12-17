/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.navigation;

import com.vero.ui.common.LabelPane;
import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_PANE;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.UIData;
import com.vero.ui.common.DraggableObject;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Tai Hu
 */
public abstract class ObjectPane extends LabelPane implements DraggableObject {
    public ObjectPane() {
        getStyleClass().addAll(CLASS_OBJECT_PANE);
    }
    
    public abstract ObjectType getType();

    /**
     * If dragging function is enabled, subclass must override this method
     * 
     * @return 
     */
    @Override
    public UIData getTransferData() {
        throw new UnsupportedOperationException("Override this method to support this function."); 
    }

    @Override
    public Node getDragSource() {
        return this;
    }
 
    @Override
    public void handleDragDetectedEvent(MouseEvent event) {        
    }

    @Override
    public void handleDragDoneEvent(DragEvent event) {
    }
}
