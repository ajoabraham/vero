package com.vero.ui.editor;

import javafx.stage.Stage;

import com.vero.ui.model.MetricObjectData;

/**
 * 
 * @author Tai Hu
 *
 */
public class UndockedMetricEditorPane extends UndockedEditorPane<MetricObjectData> {
    public UndockedMetricEditorPane(Stage stage, MetricObjectData data, DockHandler dockHandler) {
        super(stage, data, dockHandler);
        buildUI();
    }
    
    private void buildUI() {
        
    }
}
