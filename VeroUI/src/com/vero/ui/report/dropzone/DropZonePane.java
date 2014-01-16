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

import com.vero.ui.report.ReportPane;
import com.vero.ui.report.dropzone.DropTargetPane;
import com.vero.ui.report.dropzone.DropTargetPaneFactory;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public class DropZonePane extends ScrollPane {
    private ReportPane reportPane = null;
    
    public DropZonePane(ReportPane reportPane) {
        this.reportPane = reportPane;
        buildUI();
    }

    private void buildUI() {
        getStyleClass().add(CLASS_DROP_ZONE_PANE);
        setPrefWidth(DROP_ZONE_PANE_WIDTH);
        setFitToWidth(true);
        VBox contentPane = new VBox();
        contentPane.getStyleClass().add(CLASS_DROP_ZONE_CONTENT_PANE);
        setContent(contentPane);
        
        contentPane.getChildren().add(LabelPaneFactory.createReportNameEditablePane("REPORT BLOCK"));

        Label attributesLabel = new Label("ATTRIBUTES");
        attributesLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        attributesLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(attributesLabel);
        
        DropTargetPane attributeDropPane = DropTargetPaneFactory.createDropPane(ATTRIBUTE, true);
        attributeDropPane.setDockHandler(reportPane);
        attributeDropPane.getChildren().add(
                LabelPaneFactory.createPlaceholderPane(attributeDropPane.getPlaceholderText()));
        contentPane.getChildren().add(attributeDropPane);

        Label metricsLabel = new Label("METRICS");
        metricsLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        metricsLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(metricsLabel);

        DropTargetPane metricDropPane = DropTargetPaneFactory.createDropPane(METRIC, true);
        metricDropPane.setDockHandler(reportPane);
        metricDropPane.getChildren().add(
                LabelPaneFactory.createPlaceholderPane(metricDropPane.getPlaceholderText()));
        contentPane.getChildren().add(metricDropPane);

        Label tablesLabel = new Label("TABLES");
        tablesLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        tablesLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(tablesLabel);

        DropTargetPane tableDropPane = DropTargetPaneFactory.createDropPane(TABLE, true);
        tableDropPane.setDockHandler(reportPane);
        tableDropPane.getChildren().add(
                LabelPaneFactory.createPlaceholderPane(tableDropPane.getPlaceholderText()));
        contentPane.getChildren().add(tableDropPane);

        Label tableJoinsLabel = new Label("TABLE JOINS");
        tableJoinsLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        tableJoinsLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(tableJoinsLabel);
        
        DropTargetPane tableJoinDropPane = DropTargetPaneFactory.createDropPane(TABLE_JOIN, false);
        tableJoinDropPane.getChildren().add(
                LabelPaneFactory.createPlaceholderPane(tableJoinDropPane.getPlaceholderText()));
        tableJoinDropPane.getChildren().add(
                LabelPaneFactory.createTableJoinPane("T1", INNER_JOIN, "T2"));
        contentPane.getChildren().add(tableJoinDropPane);
    }
}
