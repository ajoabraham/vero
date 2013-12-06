/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.ATTRIBUTE;
import static com.vero.ui.common.ObjectType.DATASOURCE;
import static com.vero.ui.common.ObjectType.METRIC;
import static com.vero.ui.common.ObjectType.TABLE;
import static com.vero.ui.common.ObjectType.COLUMN;
import static com.vero.ui.common.UIConstants.OBJECT_PANE_HEIGHT;
import static com.vero.ui.common.CSSConstants.*;
import com.vero.ui.util.UIUtils;
import javafx.scene.control.Label;
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
public class DatasourceNavigationPane extends BorderPane {

    public DatasourceNavigationPane() {
        buildUI();
    }

    private void buildUI() {
        setId(ID_DATASOURCE_NAVIGATION_PANE);

        Label datasourcesLabel = new Label("DATASOURCES");
        datasourcesLabel.getStyleClass().add(CLASS_SECTION_TITLE);
        setTop(datasourcesLabel);

        VBox objectsPane = new VBox();
        objectsPane.setId(ID_OBJECTS_PANE);

        // Build search box first.
        TextField searchField = new TextField();
        searchField.setPrefHeight(OBJECT_PANE_HEIGHT);
        searchField.setPromptText("Search...");
        searchField.setId(ID_OBJECT_SEARCH_TEXT_FIELD);

        objectsPane.getChildren().add(searchField);
        objectsPane.getChildren().add(UIUtils.createVerticalSpaceFiller(20));

        objectsPane.getChildren().add(buildObjectPane("Teradata", TABLE));
        objectsPane.getChildren().add(buildObjectPane("LessionsFact", DATASOURCE));
        objectsPane.getChildren().add(buildObjectPane("Professors", DATASOURCE));
        objectsPane.getChildren().add(buildObjectPane("Professor ID", ATTRIBUTE));
        objectsPane.getChildren().add(buildObjectPane("Professor Name", ATTRIBUTE));
        objectsPane.getChildren().add(buildObjectPane("# Professors", METRIC));
        objectsPane.getChildren().add(buildObjectPane("unused_column_1", COLUMN));
        objectsPane.getChildren().add(buildObjectPane("Students", DATASOURCE));
        objectsPane.getChildren().add(buildObjectPane("Departments", DATASOURCE));
        objectsPane.getChildren().add(buildObjectPane("Oracle-Billing", TABLE));
        objectsPane.getChildren().add(buildObjectPane("Hive-Prod", TABLE));

        setCenter(objectsPane);
    }

    private Pane buildObjectPane(String labelText, ObjectType type) {
        HBox objectPane = new HBox();
        objectPane.getStyleClass().addAll("object-pane", UIUtils.getObjectSytleClass(type));
        objectPane.setPrefHeight(OBJECT_PANE_HEIGHT);

        if (type == TABLE) {
            ImageView tableImageView = new ImageView();
            tableImageView.getStyleClass().add("object-table-imageview");
            objectPane.getChildren().add(tableImageView);
        }

        Label label = new Label(labelText);
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add("object-label");
        objectPane.getChildren().add(label);

        if (type == TABLE) {
            ImageView statusImageView = new ImageView();
            statusImageView.getStyleClass().add("object-table-status-imageview");
            objectPane.getChildren().add(statusImageView);
        }

        return objectPane;
    }
}
