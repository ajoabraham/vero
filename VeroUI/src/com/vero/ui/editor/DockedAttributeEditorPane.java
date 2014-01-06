/**
 * 
 */
package com.vero.ui.editor;

import com.vero.ui.model.AttributeObjectData;

/**
 * @author Tai Hu
 *
 */
public class DockedAttributeEditorPane extends DockedEditorPane<AttributeObjectData> {

    /**
     * @param data
     */
    public DockedAttributeEditorPane(AttributeObjectData data, DockHandler dockHandler) {
        super(data, dockHandler);
        buildUI();
    }
    
    private void buildUI() {
        
    }
}
