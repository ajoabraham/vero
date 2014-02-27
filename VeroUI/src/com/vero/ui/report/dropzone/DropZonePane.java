/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_DROP_ZONE_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_DROP_ZONE_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SUBSECTION_TITLE;
import static com.vero.ui.constants.ObjectType.ATTRIBUTE;
import static com.vero.ui.constants.ObjectType.METRIC;
import static com.vero.ui.constants.ObjectType.TABLE;
import static com.vero.ui.constants.ObjectType.TABLE_JOIN;
import static com.vero.ui.constants.UIConstants.DEFAULT_LABEL_PANE_HEIGHT;
import static com.vero.ui.constants.UIConstants.DROP_ZONE_PANE_WIDTH;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.report.ReportPane;
import com.vero.ui.report.querypane.QueryBlockPane;

/**
 *
 * @author Tai Hu
 */
public class DropZonePane extends ScrollPane {
    private ReportPane reportPane = null;
    private QueryBlockPane queryBlockPane = null;
    private DropTargetPane attributeDropPane = null;
    private DropTargetPane metricDropPane = null;
    private DropTargetPane tableDropPane = null;
    private DropTargetPane tableJoinDropPane = null;
    
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
        
        attributeDropPane = DropTargetPaneFactory.createDropPane(reportPane, this, ATTRIBUTE, true);
        attributeDropPane.getChildren().add(
                LabelPaneFactory.createPlaceholderPane(attributeDropPane.getPlaceholderText()));
        contentPane.getChildren().add(attributeDropPane);

        Label metricsLabel = new Label("METRICS");
        metricsLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        metricsLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(metricsLabel);

        metricDropPane = DropTargetPaneFactory.createDropPane(reportPane, this, METRIC, true);
        metricDropPane.getChildren().add(
                LabelPaneFactory.createPlaceholderPane(metricDropPane.getPlaceholderText()));
        contentPane.getChildren().add(metricDropPane);

        Label tablesLabel = new Label("TABLES");
        tablesLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        tablesLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(tablesLabel);

        tableDropPane = DropTargetPaneFactory.createDropPane(reportPane, this, TABLE, true);
        tableDropPane.getChildren().add(
                LabelPaneFactory.createPlaceholderPane(tableDropPane.getPlaceholderText()));
        contentPane.getChildren().add(tableDropPane);

        Label tableJoinsLabel = new Label("TABLE JOINS");
        tableJoinsLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        tableJoinsLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        contentPane.getChildren().add(tableJoinsLabel);
        
        tableJoinDropPane = DropTargetPaneFactory.createDropPane(reportPane, this, TABLE_JOIN, false);
        tableJoinDropPane.getChildren().add(
                LabelPaneFactory.createPlaceholderPane(tableJoinDropPane.getPlaceholderText()));
        contentPane.getChildren().add(tableJoinDropPane);
    }

    public ReportPane getReportPane() {
        return reportPane;
    }

    public void setReportPane(ReportPane reportPane) {
        this.reportPane = reportPane;
    }

    public QueryBlockPane getQueryBlockPane() {
        return queryBlockPane;
    }

    public void setQueryBlockPane(QueryBlockPane queryBlockPane) {
        this.queryBlockPane = queryBlockPane;
    }

    public DropTargetPane getAttributeDropPane() {
        return attributeDropPane;
    }

    public void setAttributeDropPane(DropTargetPane attributeDropPane) {
        this.attributeDropPane = attributeDropPane;
    }

    public DropTargetPane getMetricDropPane() {
        return metricDropPane;
    }

    public void setMetricDropPane(DropTargetPane metricDropPane) {
        this.metricDropPane = metricDropPane;
    }

    public DropTargetPane getTableDropPane() {
        return tableDropPane;
    }

    public void setTableDropPane(DropTargetPane tableDropPane) {
        this.tableDropPane = tableDropPane;
    }

    public DropTargetPane getTableJoinDropPane() {
        return tableJoinDropPane;
    }

    public void setTableJoinDropPane(DropTargetPane tableJoinDropPane) {
        this.tableJoinDropPane = tableJoinDropPane;
    }
}
