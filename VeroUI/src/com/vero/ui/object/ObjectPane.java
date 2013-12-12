/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import com.vero.ui.model.UIData;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 *
 * @author Tai Hu
 */
public abstract class ObjectPane extends HBox implements DraggableObject {
    public ObjectPane() {
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
