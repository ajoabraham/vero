/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

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
    private StackPane dropZonePaneContainer = null; 
    private QueryPane queryPane = null;
    
    public ReportPane(ReportObjectData reportObjectData) {
	this.reportObjectData = reportObjectData;
        buildUI();
    }
    
    private void buildUI() {
	dropZonePaneContainer = new StackPane();
        setLeft(dropZonePaneContainer);
        
        queryPane = new QueryPane(this);
        setCenter(queryPane);
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
    
    public void addNewCommentBlockPane() {
        queryPane.addNewCommentBlockPane();
    }
    
    public void addNewQueryBlockPane() {
        queryPane.addNewQueryBlockPane();
    }
}
