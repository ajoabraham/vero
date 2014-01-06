/**
 * 
 */
package com.vero.ui.editor;

import javafx.stage.Stage;

import com.vero.ui.model.AttributeObjectData;

/**
 * @author Tai Hu
 *
 */
public class UndockedAttributeEditorPane extends UndockedEditorPane<AttributeObjectData> {

    /**
     * @param stage
     * @param data
     */
    public UndockedAttributeEditorPane(Stage stage, AttributeObjectData data, DockHandler dockHandler) {
        super(stage, data, dockHandler);
        buildUI();
    }

    private void buildUI() {
        
    }
}
