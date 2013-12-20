package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_EDITOR_PANE;
import javafx.scene.layout.BorderPane;

import com.vero.ui.constants.ObjectType;

/**
 * Top level class for all dock/undock data editors
 * 
 * @author Tai Hu
 *
 */
public abstract class EditorPane extends BorderPane {
    public EditorPane() {
        getStyleClass().add(CLASS_EDITOR_PANE);
    }
    
    public abstract ObjectType getType();
}
