/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui.report;

import com.vero.ui.LabelPaneFactory;
import com.vero.ui.common.ImageList;
import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.ATTRIBUTE;
import static com.vero.ui.common.ObjectType.METRIC;
import static com.vero.ui.common.ObjectType.TABLE;
import static com.vero.ui.common.ObjectType.COLUMN;
import static com.vero.ui.common.UIConstants.DROP_ZONE_PANE_WIDTH;
import static com.vero.ui.common.UIConstants.DEFAULT_LABEL_PANE_HEIGHT;
import static com.vero.ui.common.UIConstants.TABLE_LABEL_HEIGHT;
import static com.vero.ui.common.UIConstants.TABLE_LABEL_WIDTH;
import static com.vero.ui.common.CSSConstants.*;
import static com.vero.ui.common.ObjectType.TABLE_JOIN;
import com.vero.ui.object.DropPane;
import com.vero.ui.object.DropPaneFactory;
import com.vero.ui.util.UIUtils;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public class DropZonePane extends VBox {
    
    public DropZonePane() {
        buildUI();
    }

    private void buildUI() {
        setId(ID_DROP_ZONE_PANE);
        setPrefWidth(DROP_ZONE_PANE_WIDTH);

        getChildren().add(buildObjectPane("REPORT BLOCK", COLUMN));

        Label attributesLabel = new Label("ATTRIBUTES");
        attributesLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        attributesLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        getChildren().add(attributesLabel);

        DropPaneFactory dropPaneFactory = DropPaneFactory.getInstance();
        LabelPaneFactory labelPaneFactory = LabelPaneFactory.getInstance();
        
        DropPane attributeDropPane = dropPaneFactory.createDropPane(ATTRIBUTE, true);
        attributeDropPane.getChildren().add(
                labelPaneFactory.createPlaceholderPane(attributeDropPane.getPlaceholderText()));
        getChildren().add(attributeDropPane);

        Label metricsLabel = new Label("METRICS");
        metricsLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        metricsLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        getChildren().add(metricsLabel);

        DropPane metricDropPane = dropPaneFactory.createDropPane(METRIC, true);
        metricDropPane.getChildren().add(
                labelPaneFactory.createPlaceholderPane(metricDropPane.getPlaceholderText()));
        getChildren().add(metricDropPane);

        Label tablesLabel = new Label("TABLES");
        tablesLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        tablesLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        getChildren().add(tablesLabel);

        DropPane tableDropPane = dropPaneFactory.createDropPane(TABLE, true);
        tableDropPane.getChildren().add(
                labelPaneFactory.createPlaceholderPane(tableDropPane.getPlaceholderText()));
        getChildren().add(tableDropPane);

        Label tableJoinsLabel = new Label("TABLE JOINS");
        tableJoinsLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        tableJoinsLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        getChildren().add(tableJoinsLabel);
        
        DropPane tableJoinDropPane = dropPaneFactory.createDropPane(TABLE_JOIN, false);
        tableJoinDropPane.getChildren().add(
                labelPaneFactory.createPlaceholderPane(tableJoinDropPane.getPlaceholderText()));
        getChildren().add(tableJoinDropPane);
    }

    private Pane buildObjectPane(String labelText, ObjectType type) {
        HBox objectPane = new HBox();
        objectPane.getStyleClass().addAll(CLASS_OBJECT_PANE, UIUtils.getObjectSytleClass(type));
        objectPane.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);

        if (type == TABLE) {
            ImageView tableImageView = new ImageView();
            tableImageView.getStyleClass().add("object-table-imageview");
            objectPane.getChildren().add(tableImageView);
        }

        Label label = new Label(labelText);
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        objectPane.getChildren().add(label);

        if (type == TABLE) {
            ImageView statusImageView = new ImageView();
            statusImageView.getStyleClass().add("object-table-status-imageview");
            objectPane.getChildren().add(statusImageView);
        }

        return objectPane;
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
