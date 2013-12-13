/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import com.vero.ui.common.ImageList;
import static com.vero.ui.common.UIConstants.OBJECT_PANE_HEIGHT;
import static com.vero.ui.common.UIConstants.REPORT_BLOCK_PANE_HEIGHT;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public class ReportPane extends BorderPane {
    public ReportPane() {
        buildUI();
    }
    
    private void buildUI() {
        setLeft(new DropZonePane());
        setCenter(getQueryPane());
    }
            
    private Pane getQueryPane() {
        VBox queryPane = new VBox();
        queryPane.setId("query-pane");
        
        queryPane.getChildren().addAll(getGlobalFilterPane(), getCommentPane(), getReportBlockPane());
        return queryPane;
    }
    
    private Pane getGlobalFilterPane() {
        VBox globalFilterPane = new VBox();
        globalFilterPane.setId("global-filter-pane");
        
        Label globalFilterLabel = new Label("GLOBAL FILTERS");
        globalFilterLabel.setPrefHeight(OBJECT_PANE_HEIGHT);
        globalFilterLabel.getStyleClass().add("subsection-title");
        globalFilterPane.getChildren().add(globalFilterLabel);
        
        TextField filterTextField = new TextField();
        filterTextField.setPromptText("Type a column, attribute, metric, or table name to start...");
        filterTextField.setId("global-filter-text-field");
        globalFilterPane.getChildren().add(filterTextField);
        
        return globalFilterPane;
    }
    
    private Pane getCommentPane() {
        HBox commentPane = new HBox();
        commentPane.setId("comment-pane");
        
        commentPane.getChildren().add(new ImageView(ImageList.IMAGE_COMMENT));
        
        Label commentLabel = new Label("This is comment block. Since its at the top its likely describing the whole report. Comments can be repositioned anywhere.");
        commentLabel.setId("comment-label");
        HBox.setHgrow(commentLabel, Priority.ALWAYS);
        commentPane.getChildren().add(commentLabel);
        
        return commentPane;
    }
    
    private Pane getReportBlockPane() {
        BorderPane reportBlockPane = new BorderPane();
        reportBlockPane.setId("report-block-pane");
        reportBlockPane.setPrefHeight(REPORT_BLOCK_PANE_HEIGHT);
        reportBlockPane.setMinHeight(REPORT_BLOCK_PANE_HEIGHT);
        
        HBox headerPane = new HBox();
        headerPane.getChildren().add(new ImageView(ImageList.IMAGE_ACTIVE_CIRCLE));
        Label headerLabel = new Label("REPORT BLOCK");
        headerLabel.getStyleClass().add("subsection-title");
        headerLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(headerLabel, Priority.ALWAYS);
        headerPane.getChildren().add(headerLabel);
        headerPane.getChildren().add(new ImageView(ImageList.IMAGE_FILTER));
        headerPane.getChildren().add(new ImageView(ImageList.IMAGE_RUN));
        reportBlockPane.setTop(headerPane);
        
        TextArea reportBlockTextArea = new TextArea();
        reportBlockTextArea.setId("report-block-text-area");
        reportBlockPane.setCenter(reportBlockTextArea);
        
        return reportBlockPane;
    }
}
