package com.vero.ui.editor;

import static com.vero.ui.constants.ObjectType.METRIC;

import com.vero.ui.constants.ObjectType;

/**
 * 
 * @author Tai Hu
 *
 */
public class DockedMetricEditorPane extends DockedEditorPane {
    public DockedMetricEditorPane() {
    }

    @Override
    public ObjectType getType() {
        return METRIC;
    }
}
