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
import com.vero.ui.model.UIData;

/**
 * @author Tai Hu
 *
 */
public abstract class DropZoneObjectPane<T extends UIData> extends LabelPane implements EventHandler<MouseEvent> {
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
    
    @Override
    public void handle(MouseEvent event) {        
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

        }
    }
}
