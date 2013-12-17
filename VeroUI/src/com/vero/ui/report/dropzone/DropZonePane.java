/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui.report.dropzone;

import com.vero.ui.common.LabelPaneFactory;
import static com.vero.ui.constants.ObjectType.ATTRIBUTE;
import static com.vero.ui.constants.ObjectType.METRIC;
import static com.vero.ui.constants.ObjectType.TABLE;
import static com.vero.ui.constants.UIConstants.DROP_ZONE_PANE_WIDTH;
import static com.vero.ui.constants.UIConstants.DEFAULT_LABEL_PANE_HEIGHT;
import static com.vero.ui.constants.CSSConstants.*;
import static com.vero.ui.constants.ObjectType.TABLE_JOIN;
import static com.vero.ui.constants.TableJoinType.INNER_JOIN;
import com.vero.ui.report.dropzone.DropPane;
import com.vero.ui.report.dropzone.DropPaneFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
        tableJoinDropPane.getChildren().add(
                labelPaneFactory.createTableJoinPane("T1", INNER_JOIN, "T2"));
        contentPane.getChildren().add(tableJoinDropPane);
    }
}
