/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import static com.vero.ui.constants.UIConstants.UNDOCKED_EDITOR_PANE_HEIGHT;
import static com.vero.ui.constants.UIConstants.UNDOCKED_EDITOR_PANE_WIDTH;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import com.vero.ui.common.DockEvent;
import com.vero.ui.common.PopupDialog;
import com.vero.ui.common.UIManager;
import com.vero.ui.editor.DockHandler;
import com.vero.ui.editor.DockedEditorPane;
import com.vero.ui.editor.EditorPaneFactory;
import com.vero.ui.editor.UndockedEditorPane;
import com.vero.ui.model.ReportObjectData;
import com.vero.ui.model.UIData;
import com.vero.ui.report.querypane.QueryPane;

/**
 *
 * @author Tai Hu
 * 
 */
public class ReportPane extends BorderPane implements DockHandler {
    private static final Logger logger = Logger.getLogger(ReportPane.class.getName());
    
    private UIManager uiManager = null;
    private DockedEditorPane<? extends UIData> dockedEditorPane = null;
    private ReportObjectData reportObjectData = null;
    private StackPane dropZonePaneContainer = null; 
    private QueryPane queryPane = null;
    
    public ReportPane(ReportObjectData reportObjectData) {
	this.reportObjectData = reportObjectData;
        uiManager = UIManager.getInstance();
        buildUI();
    }
    
    private void buildUI() {
	dropZonePaneContainer = new StackPane();
        setLeft(dropZonePaneContainer);
        
        queryPane = new QueryPane(this);
        setCenter(queryPane);
    }

    @Override
    public void handle(DockEvent dockEvent) {
        switch (dockEvent.getType()) {
            case DOCK:
                handleDockEvent(dockEvent.getData());
                break;
            case UNDOCK:
                handleUndockEvent(dockEvent.getData());
                break;
            case CANCEL:
                handleCancelEvent();
                break;
            default:
              logger.log(Level.SEVERE, "Invalid dock event type - {0}", dockEvent.getType());
        }
    }
    
    private void handleDockEvent(UIData data) {
        dockedEditorPane = EditorPaneFactory.createDockedEditorPane(data, this);
        setBottom(dockedEditorPane);
    }
    
    private void handleUndockEvent(UIData data) {
        PopupDialog dialog = new PopupDialog(uiManager.getPrimaryStage());
        UndockedEditorPane<? extends UIData> unDockedEditorPane = EditorPaneFactory.createUndockedEditorPane(dialog, data, this);
        dialog.createScene(unDockedEditorPane, UNDOCKED_EDITOR_PANE_WIDTH, UNDOCKED_EDITOR_PANE_HEIGHT);
        handleCancelEvent();
        dialog.show();
    }
    
    private void handleCancelEvent() {
        setBottom(null);
    }

    public ReportObjectData getReportObjectData() {
        return reportObjectData;
    }

    public void setReportObjectData(ReportObjectData reportObjectData) {
        this.reportObjectData = reportObjectData;
    }

    public StackPane getDropZonePaneContainer() {
        return dropZonePaneContainer;
    }

    public void setDropZonePaneContainer(StackPane dropZonePaneContainer) {
        this.dropZonePaneContainer = dropZonePaneContainer;
    }
}
