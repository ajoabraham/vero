package com.vero.ui.editor;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.Stage;

import com.vero.ui.constants.ObjectType;

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

    public DockedEditorPane createDockedEditorPane(ObjectType type) {
        DockedEditorPane dockedEditorPane = null;
        switch (type) {
            case ATTRIBUTE:
                break;
            case METRIC:
                dockedEditorPane = new DockedMetricEditorPane();
                break;
            case TABLE_JOIN:
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", type);
        }

        return dockedEditorPane;
    }
    
    public EditorPane createUndockedEditorPane(ObjectType type, Stage stage) {
        EditorPane editorPane = null;
        switch (type) {
            case ATTRIBUTE:
                break;
            case METRIC:
                editorPane = new UndockedMetricEditorPane(stage);
                break;
            case TABLE_JOIN:
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", type);
        }

        return editorPane;
    }
}
