/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui;

import static com.vero.ui.common.UIConstants.OBJECT_PANE_HEIGHT;
import static com.vero.ui.common.CSSConstants.*;
import com.vero.ui.object.ObjectPane;
import com.vero.ui.object.ObjectTreeView;
import com.vero.ui.util.UIUtils;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
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

        TreeView<ObjectPane> treeView = new ObjectTreeView();
        objectsPane.getChildren().add(treeView);

        setCenter(objectsPane);
    }
}
