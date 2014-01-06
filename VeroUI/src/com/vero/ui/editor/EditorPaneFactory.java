package com.vero.ui.editor;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.Stage;

import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.UIData;

/**
 * 
 * @author Tai Hu
 * 
 */
public final class EditorPaneFactory {
    private static final Logger logger = Logger.getLogger(EditorPaneFactory.class.getName());
    
    private static EditorPaneFactory INSTANCE = null;

    private EditorPaneFactory() {

    }

    public static EditorPaneFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EditorPaneFactory();
        }

        return INSTANCE;
    }

    public DockedEditorPane<? extends UIData> createDockedEditorPane(UIData data, DockHandler dockHandler) {
        DockedEditorPane<? extends UIData> dockedEditorPane = null;
        switch (data.getType()) {
            case ATTRIBUTE:
                dockedEditorPane = new DockedAttributeEditorPane((AttributeObjectData) data, dockHandler);
                break;
            case METRIC:
                dockedEditorPane = new DockedMetricEditorPane((MetricObjectData) data, dockHandler);
                break;
            case TABLE_JOIN:
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", data.getType());
        }

        return dockedEditorPane;
    }
    
    public UndockedEditorPane<? extends UIData> createUndockedEditorPane(Stage stage, UIData data, DockHandler dockHandler) {
        UndockedEditorPane<? extends UIData> undockedEditorPane = null;
        switch (data.getType()) {
            case ATTRIBUTE:
                undockedEditorPane = new UndockedAttributeEditorPane(stage, (AttributeObjectData) data, dockHandler);
                break;
            case METRIC:
                undockedEditorPane = new UndockedMetricEditorPane(stage, (MetricObjectData) data, dockHandler);
                break;
            case TABLE_JOIN:
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", data.getType());
        }

        return undockedEditorPane;
    }
}
