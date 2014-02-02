/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.querypane;

import static com.vero.ui.constants.CSSConstants.CLASS_QUERY_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_QUERY_PANE;

import java.util.ArrayList;
import java.util.List;

import com.vero.ui.model.ReportObjectData;
import com.vero.ui.report.ReportPane;
import com.vero.ui.report.dropzone.DropZonePane;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public class QueryPane extends ScrollPane {
    private ReportPane reportPane = null;
    private ReportObjectData reportObjectData = null;
    private StackPane dropZonePaneContainer = null;
    
    private List<Pane> blockPanes = null;
    
    private VBox contentPane = null;
    
    public QueryPane(ReportPane reportPane) {
	this.reportPane = reportPane;
	this.reportObjectData = reportPane.getReportObjectData();
        this.dropZonePaneContainer = reportPane.getDropZonePaneContainer();
        blockPanes = new ArrayList<Pane>();
	
        buildUI();
        populateData();
    }
    
    private void buildUI() {
	getStyleClass().add(CLASS_QUERY_PANE);
	setFitToWidth(true);
	contentPane = new VBox();
        contentPane.getStyleClass().add(CLASS_QUERY_CONTENT_PANE);
        setContent(contentPane);
    }
    
    private void populateData() {
	// New report
	if (reportObjectData.getId() == null) {
	    DropZonePane dropZonePane = new DropZonePane(reportPane);
	    Pane globalFilter = new GlobalFilterPane();
	    blockPanes.add(globalFilter);
	    Pane commentBlock = BlockPaneFactory.createCommentBlockPane();
	    blockPanes.add(commentBlock);
	    Pane reportBlockPane = BlockPaneFactory.createReportBlockPane(dropZonePane);
	    blockPanes.add(reportBlockPane);
	    
	    contentPane.getChildren().addAll(globalFilter, commentBlock, reportBlockPane);
	    dropZonePaneContainer.getChildren().add(dropZonePane);
	}
    }
    
    public void addNewCommentBlockPane() {
        Pane commentBlock = BlockPaneFactory.createCommentBlockPane();
        blockPanes.add(commentBlock);
        
        contentPane.getChildren().add(1, commentBlock);
    }
    
    public void addNewQueryBlockPane() {
        DropZonePane dropZonePane = new DropZonePane(reportPane);
        Pane queryBlock = BlockPaneFactory.createQueryBlockPane(dropZonePane);
        blockPanes.add(queryBlock);
        
        contentPane.getChildren().add(1, queryBlock);
    }
}
