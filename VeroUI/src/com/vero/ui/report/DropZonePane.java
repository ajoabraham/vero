/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui.report;

import com.vero.ui.LabelPaneFactory;
import com.vero.ui.common.ImageList;
import static com.vero.ui.common.ObjectType.ATTRIBUTE;
import static com.vero.ui.common.ObjectType.METRIC;
import static com.vero.ui.common.ObjectType.TABLE;
import static com.vero.ui.common.UIConstants.DROP_ZONE_PANE_WIDTH;
import static com.vero.ui.common.UIConstants.DEFAULT_LABEL_PANE_HEIGHT;
import static com.vero.ui.common.UIConstants.TABLE_LABEL_HEIGHT;
import static com.vero.ui.common.UIConstants.TABLE_LABEL_WIDTH;
import static com.vero.ui.common.CSSConstants.*;
import static com.vero.ui.common.ObjectType.TABLE_JOIN;
import com.vero.ui.object.DropPane;
import com.vero.ui.object.DropPaneFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public class DropZonePane extends ScrollPane {
    
    public DropZonePane() {
        buildUI();
    }

    private void buildUI() {
        setId(ID_DROP_ZONE_PANE);
        setPrefWidth(DROP_ZONE_PANE_WIDTH);
        setFitToWidth(true);
        VBox contentPane = new VBox();
        contentPane.getStyleClass().add(CLASS_DROP_ZONE_CONTENT_PANE);
        setContent(contentPane);

        DropPaneFactory dropPaneFactory = DropPaneFactory.getInstance();
        LabelPaneFactory labelPaneFactory = LabelPaneFactory.getInstance();
        
        contentPane.getChildren().add(labelPaneFactory.createReportNameEditablePane("REPORT BLOCK"));

        Label attributesLabel = new Label("ATTRIBUTES");
        attributesLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        attributesLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(attributesLabel);
        
        DropPane attributeDropPane = dropPaneFactory.createDropPane(ATTRIBUTE, true);
        attributeDropPane.getChildren().add(
                labelPaneFactory.createPlaceholderPane(attributeDropPane.getPlaceholderText()));
        contentPane.getChildren().add(attributeDropPane);

        Label metricsLabel = new Label("METRICS");
        metricsLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        metricsLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(metricsLabel);

        DropPane metricDropPane = dropPaneFactory.createDropPane(METRIC, true);
        metricDropPane.getChildren().add(
                labelPaneFactory.createPlaceholderPane(metricDropPane.getPlaceholderText()));
        contentPane.getChildren().add(metricDropPane);

        Label tablesLabel = new Label("TABLES");
        tablesLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        tablesLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(tablesLabel);

        DropPane tableDropPane = dropPaneFactory.createDropPane(TABLE, true);
        tableDropPane.getChildren().add(
                labelPaneFactory.createPlaceholderPane(tableDropPane.getPlaceholderText()));
        contentPane.getChildren().add(tableDropPane);

        Label tableJoinsLabel = new Label("TABLE JOINS");
        tableJoinsLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        tableJoinsLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(tableJoinsLabel);
        
        DropPane tableJoinDropPane = dropPaneFactory.createDropPane(TABLE_JOIN, false);
        tableJoinDropPane.getChildren().add(
                labelPaneFactory.createPlaceholderPane(tableJoinDropPane.getPlaceholderText()));
        contentPane.getChildren().add(tableJoinDropPane);
    }

   
    private Pane buildTableJoinPane() {
        HBox tableJoinPane = new HBox();
        tableJoinPane.getStyleClass().add("table-join-pane");
        tableJoinPane.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);

//        Button editButton = new Button();
//        editButton.getStyleClass().add("edit-button");
//        editButton.setPrefSize(EDIT_BUTTON_WIDTH, EDIT_BUTTON_HEIGHT);
//        editButton.setMinSize(EDIT_BUTTON_WIDTH, EDIT_BUTTON_HEIGHT);
//        tableJoinPane.getChildren().add(editButton);

        Label leftTableLabel = new Label("T1");
        leftTableLabel.setPrefSize(TABLE_LABEL_WIDTH, TABLE_LABEL_HEIGHT);
        leftTableLabel.getStyleClass().add("table-label");
        tableJoinPane.getChildren().add(leftTableLabel);

        Label joinLabel = new Label(null, new ImageView(ImageList.IMAGE_INNER_JOIN));
        joinLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        tableJoinPane.getChildren().add(joinLabel);

        Label rightTableLabel = new Label("T2");
        rightTableLabel.setPrefSize(TABLE_LABEL_WIDTH, TABLE_LABEL_HEIGHT);
        rightTableLabel.getStyleClass().add("table-label");
        tableJoinPane.getChildren().add(rightTableLabel);

        return tableJoinPane;
    }
}
