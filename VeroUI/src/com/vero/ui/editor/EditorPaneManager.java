package com.vero.ui.editor;

import static com.vero.ui.constants.ObjectType.METRIC;
import static com.vero.ui.constants.UIConstants.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.vero.ui.VeroUI;
import com.vero.ui.common.UIManager;

/**
 * 
 * @author Tai Hu
 * 
 */
public final class EditorPaneManager {
    private static EditorPaneManager INSTANCE = null;
    
    private DockedEditorPane dockedMetricEditorPane = null;
    private UndockedEditorPane undockedMetricEditorPane = null;

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
            dockedMetricEditorPane = (DockedEditorPane) editorPaneFactory.createDockedEditorPane(METRIC);
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
            undockedMetricEditorPane = (UndockedEditorPane) editorPaneFactory.createUndockedEditorPane(METRIC, stage);
            Scene scene = new Scene(undockedMetricEditorPane, 
                    UNDOCKED_EDITOR_PANE_WIDTH, UNDOCKED_EDITOR_PANE_HEIGHT);
            scene.getStylesheets().add(VeroUI.class.getResource("VeroUI.css").toExternalForm());
            stage.setScene(scene);
        }
        
        undockedMetricEditorPane.getStage().show();
    }

    public void hideUndockedMetricEditorPane() {
        undockedMetricEditorPane.getStage().close();
    }
}
