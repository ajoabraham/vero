package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_EDITOR_PANE;
import javafx.scene.layout.BorderPane;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.UIData;

/**
 * Top level class for all dock/undock data editors
 * 
 * @author Tai Hu
 *
 */
public abstract class EditorPane<T extends UIData> extends BorderPane {
    private T data = null;
    public EditorPane(T data) {
        getStyleClass().add(CLASS_EDITOR_PANE);
    }
    
    public ObjectType getType() {
        return data.getType();
    }
    
    public T getData() {
        return data;
    }
}
