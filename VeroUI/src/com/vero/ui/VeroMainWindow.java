/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui;

import com.vero.ui.common.UIManager;
import com.vero.ui.report.DropZonePane;
import com.vero.ui.common.ImageList;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import static com.vero.ui.common.UIConstants.*;
import static com.vero.ui.common.CSSConstants.*;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Tai Hu
 */
public class VeroMainWindow extends BorderPane {
    private Pane veroToolBar = null;
    private UIManager uiManager = null;
    
    public VeroMainWindow() {
        uiManager = UIManager.getInstance();
        buildUI();
    }
    
    private void buildUI() {
        setId(ID_ROOT_PANE);
        
        setLeft(getLeftPane());
        setCenter(getCenterPane());
    }
    
    /**
     * Build tool bar and navigation pane
     * 
     * @return pane contains both tool bar and navigation pane 
     */
    private Pane getLeftPane() {
        GridPane leftPane = new GridPane();
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100);
        leftPane.getRowConstraints().add(rc);
        
        veroToolBar = new VeroToolBar();
        leftPane.getColumnConstraints().add(new ColumnConstraints(40));
        leftPane.add(veroToolBar, 0, 0);
        
        Pane navigationPane = new StackPane();
        
        Pane datasourceNavigationPane = uiManager.getDatasourceNavigationPane();
        Pane reportNavigationPane = uiManager.getReportNavigationPane();
        navigationPane.getChildren().setAll(reportNavigationPane, datasourceNavigationPane);
        
        leftPane.getColumnConstraints().add(new ColumnConstraints(NAVIGATION_PANE_WIDTH));
        leftPane.add(navigationPane, 1, 0);
        
        return leftPane;
    }
    
    private Pane getCenterPane() {
        BorderPane centerPane = new BorderPane();
        centerPane.setTop(new VeroMenuBar());
        centerPane.setCenter(getTabPane());
        
        return centerPane;
    }
        
    private Parent getTabPane() {
        TabPane tabPane = new TabPane();
        
        Tab studentTab = new Tab("#Students by Department");
        BorderPane tabContentPane = new BorderPane();
        tabContentPane.setLeft(new DropZonePane());
        tabContentPane.setCenter(getQueryPane());
        studentTab.setContent(tabContentPane);
        tabPane.getTabs().add(studentTab);
        
        Tab otherReportTab = new Tab("Some Other Report");
        tabPane.getTabs().add(otherReportTab);
        
        return tabPane;
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
        globalFilterLabel.getStyleClass().add("section-title");
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
        headerLabel.getStyleClass().add("section-title");
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
