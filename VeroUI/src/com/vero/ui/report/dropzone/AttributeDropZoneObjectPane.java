package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_LABEL;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.model.AttributeObjectData;

/**
 * 
 * @author Tai Hu
 *
 */
public class AttributeDropZoneObjectPane extends DropZoneObjectPane<AttributeObjectData> {    
    public AttributeDropZoneObjectPane(AttributeObjectData data) {
        super(data);
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(getType().getStyleClass());

        Label label = new Label(data.getName());
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);
    }
}
