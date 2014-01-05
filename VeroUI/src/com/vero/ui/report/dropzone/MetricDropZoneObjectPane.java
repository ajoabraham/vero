package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_LABEL;
import static com.vero.ui.constants.ObjectType.METRIC;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.editor.DockedEditorPane;
import com.vero.ui.editor.EditorPaneFactory;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.util.UIUtils;

/**
 * 
 * @author Tai Hu
 *
 */
public class MetricDropZoneObjectPane extends DropZoneObjectPane {
    private MetricObjectData data = null;
    
    public MetricDropZoneObjectPane(MetricObjectData data) {
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
        return METRIC;
    }
    
    @Override
    protected void handleDoubleClick() {
        if (getDockHandler() != null) {
            DockedEditorPane dockedEditorPane = EditorPaneFactory.getInstance().createDockedEditorPane(METRIC);
            getDockHandler().handle(dockedEditorPane);
        }
    }
}
