package com.vero.ui.editor;

import static com.vero.ui.constants.ObjectType.METRIC;

import com.vero.ui.constants.ObjectType;

/**
 * 
 * @author Tai Hu
 *
 */
public class UndockedMetricEditorPane extends UndockedEditorPane {
    public UndockedMetricEditorPane() {
        
    }

    @Override
    public ObjectType getType() {
        return METRIC;
    } 
}
