/**
 * 
 */
package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_DROP_ZONE_OBJECT_PANE;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;

import com.vero.ui.common.DroppableObject;
import com.vero.ui.common.LabelPane;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.UIData;

/**
 * @author Tai Hu
 *
 */
public abstract class DropZoneObjectPane extends LabelPane implements DroppableObject {
    public DropZoneObjectPane() {
        getStyleClass().addAll(CLASS_DROP_ZONE_OBJECT_PANE);
    }
    
    public abstract ObjectType getType();

    @Override
    public Node getDropTarget() {
        return this;
    }

    @Override
    public boolean acceptDrop(UIData transferData) {
        return false;
    }

    @Override
    public void handleDragOverEvent(DragEvent event) {        
    }

    @Override
    public void handleDragEnteredEvent(DragEvent event) {        
    }

    @Override
    public void handleDragExitedEvent(DragEvent event) {        
    }

    @Override
    public void handleDragDroppedEvent(DragEvent event, UIData transferData) {
        
    }
}
