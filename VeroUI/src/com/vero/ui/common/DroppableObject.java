/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.common;

import com.vero.ui.model.UIData;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;

/**
 *
 * @author Tai Hu
 */
public interface DroppableObject {
    public Node getDropTarget();
    public boolean acceptDrop(UIData transferData);
    public void handleDragOverEvent(DragEvent event);
    public void handleDragEnteredEvent(DragEvent event);
    public void handleDragExitedEvent(DragEvent event);
    public void handleDragDroppedEvent(DragEvent event, UIData transferData);
}
