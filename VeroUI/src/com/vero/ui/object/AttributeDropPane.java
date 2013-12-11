/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.ATTRIBUTE;
import javafx.scene.input.DragEvent;

/**
 *
 * @author Tai Hu
 */
public class AttributeDropPane extends DropPane {
    
    public AttributeDropPane() {
        
    }
    
    @Override
    public ObjectType getType() {
        return ATTRIBUTE;
    }

    @Override
    public void handleDragDroppedEvent(DragEvent event) {
        super.handleDragDroppedEvent(event);        
    }

    @Override
    public void handleDragExitedEvent(DragEvent event) {
        
    }

    @Override
    public void handleDragEnteredEvent(DragEvent event) {
        
    }

    @Override
    public void handleDragOverEvent(DragEvent event) {
    }

    @Override
    public DropPane getDropTarget() {
        return this;
    }
    
    
}
