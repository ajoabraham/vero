/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

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
   private DropPane target = null;
    
    private DropManager(DropPane target) {
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
        if (event.getGestureSource() != target) {
            UIData data = DragAndDropDataManager.getInstance().getData();
            
            if (data != null && data.getType() == target.getType()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
        }
                
        event.consume();
        target.handleDragOverEvent(event);
    }

    private void handleDragEnteredEvent(DragEvent event) {  
        if (event.getGestureSource() != target) {
            UIData data = DragAndDropDataManager.getInstance().getData();
            
            if (data != null && data.getType() == target.getType()) {
                target.setStyle("-fx-background-color: -fx-light-grey-color;");
            }
        }
        
        target.handleDragEnteredEvent(event);
    }
        
    private void handleDragExitedEvent(DragEvent event) {
        if (event.getGestureSource() != target) {
            UIData data = DragAndDropDataManager.getInstance().getData();
            
            if (data != null && data.getType() == target.getType()) {
                target.setStyle("-fx-background-color: transparent;");
            }
        }
        
        target.handleDragExitedEvent(event);
    }

    private void handleDragDroppedEvent(DragEvent event) {
        if (event.getGestureSource() != target) {
            UIData data = DragAndDropDataManager.getInstance().getData();
            
            if (data != null && data.getType() == target.getType()) {
                ObjectPane objectPane = ObjectPaneFactory.getInstance().createObjectPane(data.getType(), data, false);
                target.getChildren().add(objectPane);
            }
        }
        
        target.handleDragDroppedEvent(event);
    }
}
