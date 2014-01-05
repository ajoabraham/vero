/**
 * 
 */
package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_DROP_ZONE_OBJECT_PANE;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import com.vero.ui.common.LabelPane;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.editor.DockHandler;

/**
 * @author Tai Hu
 *
 */
public abstract class DropZoneObjectPane extends LabelPane implements EventHandler<MouseEvent> {
    private DockHandler dockHandler = null;
    
    public DropZoneObjectPane() {
        getStyleClass().addAll(CLASS_DROP_ZONE_OBJECT_PANE);
        setOnMouseClicked(this);
    }
    
    public abstract ObjectType getType();

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
    }
}
