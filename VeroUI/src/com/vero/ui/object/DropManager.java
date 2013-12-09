/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.DragEvent;

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
    
    public static DropManager newInstance(DroppableObject target) {
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
        target.handleDragOverEvent(event);
    }

    private void handleDragEnteredEvent(DragEvent event) {            
        target.handleDragEnteredEvent(event);
    }
        
    private void handleDragExitedEvent(DragEvent event) {
        target.handleDragExitedEvent(event);
    }

    private void handleDragDroppedEvent(DragEvent event) {
        target.handleDragDroppedEvent(event);
    }
}
