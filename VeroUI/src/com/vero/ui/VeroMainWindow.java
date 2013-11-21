/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui;

import com.vero.ui.common.ImageList;
import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import static com.vero.ui.common.UIConstants.*;
import static com.vero.ui.common.CSSConstants.*;
import com.vero.ui.util.UIUtils;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author Tai Hu
 */
public class VeroMainWindow extends BorderPane {
    public VeroMainWindow() {
        buildUI();
    }
    
    private void buildUI() {
        setId(ID_ROOT_PANE);
        
        setLeft(getLeftPane());
        setCenter(getCenterPane());
    }
    
    private Pane getLeftPane() {
        GridPane leftPane = new GridPane();
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100);
        leftPane.getRowConstraints().add(rc);
        
        Pane navButtonPane = getNavButtonPane();
        leftPane.getColumnConstraints().add(new ColumnConstraints(40));
        leftPane.add(navButtonPane, 0, 0);
        
        Pane navigationPane = getNavigationPane();
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
        tabContentPane.setLeft(getDropZonePane());
        tabContentPane.setCenter(getQueryPane());
        studentTab.setContent(tabContentPane);
        tabPane.getTabs().add(studentTab);
        
        Tab otherReportTab = new Tab("Some Other Report");
        tabPane.getTabs().add(otherReportTab);
        
        return tabPane;
    }
    
    private Pane getDropZonePane() {
        VBox dropZonePane = new VBox();
        dropZonePane.setId("drop-zone-pane");
        dropZonePane.setPrefWidth(DROP_ZONE_PANE_WIDTH);
                
        dropZonePane.getChildren().add(buildObjectPane("REPORT BLOCK", TYPE_UNUSED));

        Label attributesLabel = new Label("ATTRIBUTES");
        attributesLabel.getStyleClass().add("subsection-title");
        attributesLabel.setPrefHeight(OBJECT_PANE_HEIGHT);
        dropZonePane.getChildren().add(attributesLabel);
               
        dropZonePane.getChildren().add(buildObjectPane("Student Department Name", TYPE_ATTRIBUTE));
        dropZonePane.getChildren().add(buildObjectPane("Professor Department Name", TYPE_ATTRIBUTE));
        
        Label metricsLabel = new Label("METRICS");
        metricsLabel.getStyleClass().add("subsection-title");
        metricsLabel.setPrefHeight(OBJECT_PANE_HEIGHT);
        dropZonePane.getChildren().add(metricsLabel);
        
        dropZonePane.getChildren().add(buildObjectPane("# Lessons", TYPE_METRICS));
        
        Label tablesLabel = new Label("TABLES");
        tablesLabel.getStyleClass().add("subsection-title");
        tablesLabel.setPrefHeight(OBJECT_PANE_HEIGHT);
        dropZonePane.getChildren().add(tablesLabel);
        
        dropZonePane.getChildren().add(buildObjectPane("LessionsFact T1", TYPE_DATASOURCE));
        dropZonePane.getChildren().add(buildObjectPane("Professors T2", TYPE_DATASOURCE));
        dropZonePane.getChildren().add(buildObjectPane("Students T3", TYPE_DATASOURCE));
        dropZonePane.getChildren().add(buildObjectPane("Departments T4", TYPE_DATASOURCE));
        dropZonePane.getChildren().add(buildObjectPane("Departments T5", TYPE_DATASOURCE));

        Label tableJoinsLabel = new Label("TABLE JOINS");
        tableJoinsLabel.getStyleClass().add("subsection-title");
        tableJoinsLabel.setPrefHeight(OBJECT_PANE_HEIGHT);
        dropZonePane.getChildren().add(tableJoinsLabel);
        
        dropZonePane.getChildren().add(buildTableJoinPane());
        dropZonePane.getChildren().add(buildTableJoinPane());
        
        TextField tableJoinTextField = new TextField();
        tableJoinTextField.setPrefHeight(OBJECT_PANE_HEIGHT);
        dropZonePane.getChildren().add(tableJoinTextField);
                
        return dropZonePane;
    }
    
    private Pane getNavButtonPane() {
        VBox navButtonPane = new VBox();
        navButtonPane.setId("nav-button-pane");
        
        Button datasourceNavPaneButton = new Button();
        datasourceNavPaneButton.setId("datasource-nav-pane-button");
        datasourceNavPaneButton.setPrefSize(DATASOURCE_NAV_PANE_BTN_WIDTH, DATASOURCE_NAV_PANE_BTN_HEIGHT);
        datasourceNavPaneButton.setMinSize(DATASOURCE_NAV_PANE_BTN_WIDTH, DATASOURCE_NAV_PANE_BTN_HEIGHT);
        Button reportsNavPaneButton = new Button();
        reportsNavPaneButton.setId("reports-nav-pane-button");
        reportsNavPaneButton.setPrefSize(REPORTS_NAV_PANE_BTN_WIDTH, REPORTS_NAV_PANE_BTN_HEIGHT);
        reportsNavPaneButton.setMinSize(REPORTS_NAV_PANE_BTN_WIDTH, REPORTS_NAV_PANE_BTN_HEIGHT);
        
        navButtonPane.getChildren().addAll(datasourceNavPaneButton, reportsNavPaneButton);
        
        return navButtonPane;
    }
    
    private Pane getNavigationPane() {
        BorderPane navigationPane = new BorderPane();
        navigationPane.setId("navigation-pane");
        
        Label datasourcesLabel = new Label("DATASOURCES");
        datasourcesLabel.getStyleClass().add("section-title");
        navigationPane.setTop(datasourcesLabel);
        
        VBox objectsPane = new VBox();
        objectsPane.setId("objects-pane");
        
        // Build search box first.
        TextField searchField = new TextField();
        searchField.setPrefHeight(OBJECT_PANE_HEIGHT);
        searchField.setPromptText("Search...");
        searchField.setId("object-search-text-field");
        
        objectsPane.getChildren().add(searchField);
        objectsPane.getChildren().add(UIUtils.createVerticalSpaceFiller(20));
        
        objectsPane.getChildren().add(buildObjectPane("Teradata", TYPE_TABLE));
        objectsPane.getChildren().add(buildObjectPane("LessionsFact", TYPE_DATASOURCE));
        objectsPane.getChildren().add(buildObjectPane("Professors", TYPE_DATASOURCE));
        objectsPane.getChildren().add(buildObjectPane("Professor ID", TYPE_ATTRIBUTE));
        objectsPane.getChildren().add(buildObjectPane("Professor Name", TYPE_ATTRIBUTE));
        objectsPane.getChildren().add(buildObjectPane("# Professors", TYPE_METRICS));
        objectsPane.getChildren().add(buildObjectPane("unused_column_1", TYPE_UNUSED));
        objectsPane.getChildren().add(buildObjectPane("Students", TYPE_DATASOURCE));
        objectsPane.getChildren().add(buildObjectPane("Departments", TYPE_DATASOURCE));
        objectsPane.getChildren().add(buildObjectPane("Oracle-Billing", TYPE_TABLE));
        objectsPane.getChildren().add(buildObjectPane("Hive-Prod", TYPE_TABLE));
        
        navigationPane.setCenter(objectsPane);
        
        return navigationPane;
    }
    
    private Pane buildObjectPane(String labelText, ObjectType type) {
        HBox objectPane = new HBox();
        objectPane.getStyleClass().addAll("object-pane", UIUtils.getObjectSytleClass(type));
        objectPane.setPrefHeight(OBJECT_PANE_HEIGHT);
        
        if (type == TYPE_TABLE) {
            ImageView tableImageView = new ImageView();
            tableImageView.getStyleClass().add("object-table-imageview");
            objectPane.getChildren().add(tableImageView);
        }
        
        Label label = new Label(labelText);
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add("object-label");
        objectPane.getChildren().add(label);
        
        if (type == TYPE_TABLE) {
            ImageView statusImageView = new ImageView();
            statusImageView.getStyleClass().add("object-table-status-imageview");
            objectPane.getChildren().add(statusImageView);
        }
        
        return objectPane;
    }
    
    private Pane buildTableJoinPane() {
        HBox tableJoinPane = new HBox();
        tableJoinPane.getStyleClass().add("table-join-pane");
        tableJoinPane.setPrefHeight(OBJECT_PANE_HEIGHT);
        
        Button editButton = new Button();
        editButton.getStyleClass().add("edit-button");
        editButton.setPrefSize(EDIT_BUTTON_WIDTH, EDIT_BUTTON_HEIGHT);
        editButton.setMinSize(EDIT_BUTTON_WIDTH, EDIT_BUTTON_HEIGHT);
        tableJoinPane.getChildren().add(editButton);
        
        Label leftTableLabel = new Label("T1");
        leftTableLabel.setPrefSize(TABLE_LABEL_WIDTH, TABLE_LABEL_HEIGHT);
        leftTableLabel.getStyleClass().add("table-label");
        tableJoinPane.getChildren().add(leftTableLabel);
        
        Label joinLabel = new Label(null, new ImageView(ImageList.IMAGE_INNER_JOIN));
        joinLabel.setPrefHeight(OBJECT_PANE_HEIGHT);
        tableJoinPane.getChildren().add(joinLabel);
        
        Label rightTableLabel = new Label("T2");
        rightTableLabel.setPrefSize(TABLE_LABEL_WIDTH, TABLE_LABEL_HEIGHT);
        rightTableLabel.getStyleClass().add("table-label");
        tableJoinPane.getChildren().add(rightTableLabel);
        
        return tableJoinPane;
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
