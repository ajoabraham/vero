/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 *
 * @author Tai Hu
 */
public class DragManager implements EventHandler<Event> {
    private DraggableObject source = null;
    
    private DragManager(DraggableObject source) {
        this.source = source;
        enableDragging();
    }
    
    public static DragManager newInstance(DraggableObject source) {
        return new DragManager(source);
    }
    
    @Override
    public void handle(Event event) {
        EventType<? extends Event> eventType = event.getEventType();
        if (eventType == MouseEvent.DRAG_DETECTED) {
            handleDragDetectedEvent(event);
        }
        else if (eventType == DragEvent.DRAG_DONE) {
            handleDragDoneEvent(event);
        }
    }
    
    private void enableDragging() {
        source.getDragSource().setOnDragDetected(this);
        source.getDragSource().setOnDragDone(this);
    }

    private void handleDragDetectedEvent(Event event) {        
        Dragboard db = source.getDragSource().startDragAndDrop(TransferMode.COPY);                
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getTransferData().getType().toString());
        db.setContent(content);
        
        // TH 12/11/2013, built-in dragboard doesn't support object
        DragAndDropDataManager.getInstance().setData(source.getTransferData());
        
        source.handleDragDetectedEvent((MouseEvent) event);
                
        event.consume();
    }    

    private void handleDragDoneEvent(Event event) {                
        source.handleDragDoneEvent((DragEvent) event);
        event.consume();
    }
}
