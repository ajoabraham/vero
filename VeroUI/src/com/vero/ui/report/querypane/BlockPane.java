package com.vero.ui.report.querypane;

import com.vero.ui.common.DraggableObject;
import com.vero.ui.constants.BlockType;
import com.vero.ui.model.UIData;

import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public abstract class BlockPane extends BorderPane implements DraggableObject {
    public BlockPane() {
	
    }
    
    public abstract BlockType getType();

    @Override
    public UIData getTransferData() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Node getDragSource() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void handleDragDetectedEvent(MouseEvent event) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void handleDragDoneEvent(DragEvent event) {
	// TODO Auto-generated method stub
	
    }
}
