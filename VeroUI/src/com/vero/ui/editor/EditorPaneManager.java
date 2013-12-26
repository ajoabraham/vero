package com.vero.ui.editor;

import static com.vero.ui.constants.ObjectType.METRIC;
import static com.vero.ui.constants.UIConstants.UNDOCKED_EDITOR_PANE_HEIGHT;
import static com.vero.ui.constants.UIConstants.UNDOCKED_EDITOR_PANE_WIDTH;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

import com.vero.ui.common.PopupDialog;
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
            PopupDialog dialog = new PopupDialog(uiManager.getPrimaryStage());
            undockedMetricEditorPane = (UndockedEditorPane) editorPaneFactory.createUndockedEditorPane(METRIC, dialog);
            dialog.createScene(undockedMetricEditorPane, UNDOCKED_EDITOR_PANE_WIDTH, UNDOCKED_EDITOR_PANE_HEIGHT);
        }
        
        undockedMetricEditorPane.getStage().show();
    }

    public void hideUndockedMetricEditorPane() {
        undockedMetricEditorPane.getStage().close();
    }
}
