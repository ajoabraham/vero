/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui.report;

import com.vero.ui.common.ImageList;
import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.ATTRIBUTE;
import static com.vero.ui.common.ObjectType.DATASOURCE;
import static com.vero.ui.common.ObjectType.METRIC;
import static com.vero.ui.common.ObjectType.TABLE;
import static com.vero.ui.common.ObjectType.COLUMN;
import static com.vero.ui.common.UIConstants.DROP_ZONE_PANE_WIDTH;
import static com.vero.ui.common.UIConstants.EDIT_BUTTON_HEIGHT;
import static com.vero.ui.common.UIConstants.EDIT_BUTTON_WIDTH;
import static com.vero.ui.common.UIConstants.OBJECT_PANE_HEIGHT;
import static com.vero.ui.common.UIConstants.TABLE_LABEL_HEIGHT;
import static com.vero.ui.common.UIConstants.TABLE_LABEL_WIDTH;
import static com.vero.ui.common.CSSConstants.*;
import com.vero.ui.object.AttributeDropPane;
import com.vero.ui.object.DropPaneFactory;
import com.vero.ui.object.MetricDropPane;
import com.vero.ui.object.TableDropPane;
import com.vero.ui.util.UIUtils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        attributesLabel.setPrefHeight(OBJECT_PANE_HEIGHT);
        getChildren().add(attributesLabel);

        DropPaneFactory dropPaneFactory = DropPaneFactory.getInstance();
        
        getChildren().add(dropPaneFactory.createDropPane(ATTRIBUTE, true));

        Label metricsLabel = new Label("METRICS");
        metricsLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        metricsLabel.setPrefHeight(OBJECT_PANE_HEIGHT);
        getChildren().add(metricsLabel);

        getChildren().add(dropPaneFactory.createDropPane(METRIC, true));

        Label tablesLabel = new Label("TABLES");
        tablesLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        tablesLabel.setPrefHeight(OBJECT_PANE_HEIGHT);
        getChildren().add(tablesLabel);

        getChildren().add(dropPaneFactory.createDropPane(TABLE, true));

        Label tableJoinsLabel = new Label("TABLE JOINS");
        tableJoinsLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        tableJoinsLabel.setPrefHeight(OBJECT_PANE_HEIGHT);
        getChildren().add(tableJoinsLabel);

        getChildren().add(buildTableJoinPane());
        getChildren().add(buildTableJoinPane());

        TextField tableJoinTextField = new TextField();
        tableJoinTextField.setPrefHeight(OBJECT_PANE_HEIGHT);
        getChildren().add(tableJoinTextField);
    }

    private Pane buildObjectPane(String labelText, ObjectType type) {
        HBox objectPane = new HBox();
        objectPane.getStyleClass().addAll(CLASS_OBJECT_PANE, UIUtils.getObjectSytleClass(type));
        objectPane.setPrefHeight(OBJECT_PANE_HEIGHT);

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
}