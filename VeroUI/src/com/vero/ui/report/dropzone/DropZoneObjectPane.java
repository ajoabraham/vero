/**
 * 
 */
package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_DROP_ZONE_OBJECT_PANE;
import static com.vero.ui.constants.DockEventType.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import com.vero.ui.common.DockEvent;
import com.vero.ui.common.DockEventBuilder;
import com.vero.ui.common.LabelPane;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.editor.DockHandler;
import com.vero.ui.model.UIData;

/**
 * @author Tai Hu
 *
 */
public abstract class DropZoneObjectPane<T extends UIData> extends LabelPane implements EventHandler<MouseEvent> {
    DockHandler dockHandler = null;
    T data = null;
    
    public DropZoneObjectPane(T data) {
        this.data = data;
        getStyleClass().addAll(CLASS_DROP_ZONE_OBJECT_PANE);
        setOnMouseClicked(this);
    }
    
    public ObjectType getType() {
        return data.getType();
    }
    
    public T getData() {
        return data;
    }

    public DockHandler getDockHandler() {
        return dockHandler;
    }

    public void setDockHandler(DockHandler dockHandler) {
        this.dockHandler = dockHandler;
    }
    
    @Override
    public void handle(MouseEvent event) {        
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            handleDoubleClick();
        }
    }
    
    protected void handleDoubleClick() {   
        if (getDockHandler() != null) {
            DockEvent dockEvent = DockEventBuilder.create().type(DOCK).data(data).build();
            getDockHandler().handle(dockEvent);
        }  
    }
}
