/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.common;

import com.vero.ui.report.dropzone.DropPane;
import com.vero.ui.model.UIData;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

/**
 *
 * @author Tai Hu
 */
public class DropManager implements EventHandler<DragEvent> {
   private DroppableObject target = null;
    
    private DropManager(DroppableObject target) {
        this.target = target;
        enableDropping();
    }
    
    public static DropManager newInstance(DropPane target) {
        return new DropManager(target);
    }
    
    @Override
    public void handle(DragEvent event) {
        EventType<DragEvent> eventType = (EventType<DragEvent>) event.getEventType();
        if (eventType == DragEvent.DRAG_OVER) {
            handleDragOverEvent(event);
        }
        else if (eventType == DragEvent.DRAG_ENTERED) {
            handleDragEnteredEvent(event);
        }
        else if (eventType == DragEvent.DRAG_EXITED) {
            handleDragExitedEvent(event);
        }
        else if (eventType == DragEvent.DRAG_DROPPED) {
            handleDragDroppedEvent(event);
        }
    }
    
    private void enableDropping() {
        target.getDropTarget().setOnDragOver(this);
        target.getDropTarget().setOnDragEntered(this);
        target.getDropTarget().setOnDragExited(this);
        target.getDropTarget().setOnDragDropped(this);
    }
    
    private void handleDragOverEvent(DragEvent event) {
        if (acceptDrop(event)) {
            event.acceptTransferModes(TransferMode.COPY);
            target.handleDragOverEvent(event);
        }
                        
        event.consume();
    }

    private void handleDragEnteredEvent(DragEvent event) {  
        if (acceptDrop(event)) {
            target.handleDragEnteredEvent(event);        
        }
        
        event.consume();
    }
        
    private void handleDragExitedEvent(DragEvent event) {
        if (acceptDrop(event)) {
            target.handleDragExitedEvent(event);
        }
        
        event.consume();
    }

    private void handleDragDroppedEvent(DragEvent event) {
        if (acceptDrop(event)) {
            target.handleDragDroppedEvent(event, getTransferData(event));
        }
        
        event.consume();
    }
    
    private boolean acceptDrop(DragEvent event) {
        boolean acceptDrop = false;
        if (event.getGestureSource() != target.getDropTarget()) {
            UIData data = getTransferData(event);
            
            if (data != null && target.acceptDrop(data)) {
                acceptDrop = true;
            }
        }
        
        return acceptDrop;
    }
    
    private UIData getTransferData(DragEvent event) {
        String id = event.getDragboard().getString();
        return DragAndDropDataManager.getInstance().getData(id);
    }
}