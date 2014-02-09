package com.vero.ui.report.querypane;

import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import com.vero.ui.common.DraggableObject;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.UIData;

public abstract class BlockPane extends BorderPane implements DraggableObject {
 
    public BlockPane() {
	
    }
    
    public abstract ObjectType getType();

    @Override
    public UIData getTransferData() {
	return null;
    }

    @Override
    public Node getDragSource() {
	return null;
    }

    @Override
    public void handleDragDetectedEvent(MouseEvent event) {	
    }

    @Override
    public void handleDragDoneEvent(DragEvent event) {
    }
    
    public int getPosition() {
	return -1;
    }
    
    public void setPosition(int position) {
    }
}
