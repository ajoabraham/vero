/**
 * 
 */
package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_DROP_ZONE_OBJECT_PANE;

import com.vero.ui.common.LabelPane;
import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public abstract class DropZoneObjectPane extends LabelPane {
    public DropZoneObjectPane() {
        getStyleClass().addAll(CLASS_DROP_ZONE_OBJECT_PANE);
    }
    
    public abstract ObjectType getType();
}
