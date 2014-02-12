/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import static com.vero.ui.constants.CSSConstants.CLASS_EDITOR_PANE_CONTAINER;
import static com.vero.ui.constants.UIConstants.DEFAULT_EDITOR_PANE_CONTAINER_HEIGHT;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import com.vero.ui.model.ReportObjectData;
import com.vero.ui.report.querypane.QueryPane;

/**
 *
 * @author Tai Hu
 * 
 */
public class ReportPane extends BorderPane {
//    private static final Logger logger = Logger.getLogger(ReportPane.class.getName());
   
    private ReportObjectData reportObjectData = null;
    // Contains all drop zone pane which links to
    // each query block in query pane
    private StackPane dropZonePaneContainer = null;
    // Contains all block panes (comment, query, report, etc)
    private QueryPane queryPane = null;
    // At the bottom and contains all editor panes
    private StackPane editorPaneContainer = null;
    
    public ReportPane(ReportObjectData reportObjectData) {
	this.reportObjectData = reportObjectData;
        buildUI();
    }
    
    private void buildUI() {
	dropZonePaneContainer = new StackPane();
        setLeft(dropZonePaneContainer);
        
        queryPane = new QueryPane(this);
        setCenter(queryPane);
        
        editorPaneContainer = new StackPane();
        editorPaneContainer.getStyleClass().add(CLASS_EDITOR_PANE_CONTAINER);
        editorPaneContainer.setPrefHeight(DEFAULT_EDITOR_PANE_CONTAINER_HEIGHT);
        setBottom(editorPaneContainer);
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
        
    public QueryPane getQueryPane() {
        return queryPane;
    }

    public void setQueryPane(QueryPane queryPane) {
        this.queryPane = queryPane;
    }

    public StackPane getEditorPaneContainer() {
        return editorPaneContainer;
    }

    public void setEditorPaneContainer(StackPane editorPaneContainer) {
        this.editorPaneContainer = editorPaneContainer;
    }

    public void addNewCommentBlockPane() {
        queryPane.addNewCommentBlockPane();
    }
    
    public void addNewQueryBlockPane() {
        queryPane.addNewQueryBlockPane();
    }
}
