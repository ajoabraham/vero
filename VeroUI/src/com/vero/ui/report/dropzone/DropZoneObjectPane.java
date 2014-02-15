/**
 * 
 */
package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.ObjectType.*;
import static com.vero.ui.constants.CSSConstants.CLASS_DROP_ZONE_OBJECT_PANE;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import com.vero.ui.common.LabelPane;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.editor.EditorPane;
import com.vero.ui.editor.EditorPaneFactory;
import com.vero.ui.model.UIData;
import com.vero.ui.report.ReportPane;

/**
 * @author Tai Hu
 *
 */
public abstract class DropZoneObjectPane<T extends UIData> extends LabelPane implements EventHandler<MouseEvent> {
    T data = null;
    ReportPane reportPane = null;
    StackPane editorPaneContainer = null;
    
    public DropZoneObjectPane(ReportPane reportPane, T data) {
        this.reportPane = reportPane;
        editorPaneContainer = reportPane.getEditorPaneContainer();
        this.data = data;
        getStyleClass().addAll(CLASS_DROP_ZONE_OBJECT_PANE);
        setOnMouseClicked(this);
    }
    
    public ObjectType getType() {
        return data.getType();
    }
    
    public T getData() {
        return data;
    }
    
    @Override
    public void handle(MouseEvent event) {        
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            if (getType() == ATTRIBUTE || getType() == METRIC) {
                EditorPane<? extends UIData> editorPane = EditorPaneFactory.createEditorPane(reportPane.getQueryPane().getSelectedQueryBlockPane(), getData());
                editorPaneContainer.getChildren().add(editorPane);
            }
        }
    }
}
