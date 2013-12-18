package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_LABEL;
import static com.vero.ui.constants.ObjectType.ATTRIBUTE;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.util.UIUtils;

/**
 * 
 * @author Tai Hu
 *
 */
public class AttributeDropZoneObjectPane extends DropZoneObjectPane {
    private AttributeObjectData data = null;
    
    public AttributeDropZoneObjectPane(AttributeObjectData data) {
        this.data = data;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(UIUtils.getObjectSytleClass(getType()));

        Label label = new Label(data.getName());
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);
    }
    
    @Override
    public ObjectType getType() {
        return ATTRIBUTE;
    }

}
