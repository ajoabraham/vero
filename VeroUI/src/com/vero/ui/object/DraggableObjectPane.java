/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 *
 * @author Tai Hu
 */
public abstract class DraggableObjectPane extends ObjectPane implements EventHandler<Event> {
    public DraggableObjectPane() {
        
    }
    
    @Override
    public void handle(Event event) {
        EventType<? extends Event> eventType = event.getEventType();
        if (eventType == MouseEvent.DRAG_DETECTED) {
            handleDragDetectedEvent(event);
        }
//        else if (eventType == DragEvent.DRAG_OVER) {
//            handleDragOverEvent(event);
//        }
//        else if (eventType == DragEvent.DRAG_ENTERED) {
//            handleDragEnteredEvent(event);
//        }
//        else if (eventType == DragEvent.DRAG_EXITED) {
//            handleDragExitedEvent(event);
//        }
//        else if (eventType == DragEvent.DRAG_DROPPED) {
//            handleDragDroppedEvent(event);
//        }
        else if (eventType == DragEvent.DRAG_DONE) {
            handleDragDoneEvent(event);
        }
    }
    
    protected void enableDragging() {
        setOnDragDetected(this);
        setOnDragDone(this);
    }

    private void handleDragDetectedEvent(Event event) {                
        Dragboard db = ((Node) event.getSource()).startDragAndDrop(TransferMode.ANY);
                
        ClipboardContent content = new ClipboardContent();
        content.putString("Hello World");
        db.setContent(content);
                
        event.consume();
    }
    
//    private void handleDragOverEvent(Event event) {
//    }

//    private void handleDragEnteredEvent(Event event) {            
//    }
        
//    private void handleDragExitedEvent(Event event) {
//    }

//    private void handleDragDroppedEvent(Event event) {
//    }

    private void handleDragDoneEvent(Event event) {                
//                event.consume();
    }
}
