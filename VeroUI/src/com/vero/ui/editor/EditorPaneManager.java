package com.vero.ui.editor;

import static com.vero.ui.constants.ObjectType.METRIC;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.vero.ui.common.UIManager;

/**
 * 
 * @author Tai Hu
 * 
 */
public final class EditorPaneManager {
    private static EditorPaneManager INSTANCE = null;
    
    private EditorPane dockedMetricEditorPane = null;
    private EditorPane undockedMetricEditorPane = null;

    private EditorPaneFactory editorPaneFactory = null;
    private UIManager uiManager = null;

    private EditorPaneManager() {
        uiManager = UIManager.getInstance();
        editorPaneFactory = EditorPaneFactory.getInstance();
    }

    public static EditorPaneManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EditorPaneManager();
        }

        return INSTANCE;
    }

    public void showDockedMetricEditorPane() {
        if (dockedMetricEditorPane == null) {
            dockedMetricEditorPane = editorPaneFactory.createDockedEditorPane(METRIC);
            StackPane.setAlignment(dockedMetricEditorPane, Pos.BOTTOM_RIGHT);
        }
        
        uiManager.getVeroRootPane().getChildren().add(dockedMetricEditorPane);
    }

    public void hideDockedMetricEditorPane() {
        uiManager.getVeroRootPane().getChildren().remove(dockedMetricEditorPane);
    }

    public void showUndockedMetricEditorPane() {
        if (undockedMetricEditorPane == null) { 
            Stage stage = new Stage();
            stage.initOwner(uiManager.getPrimaryStage());
            stage.initModality(Modality.APPLICATION_MODAL);
            undockedMetricEditorPane = editorPaneFactory.createUndockedEditorPane(METRIC, stage);
        }
    }

    public void hideUndockedMetricEditorPane() {

    }
}
