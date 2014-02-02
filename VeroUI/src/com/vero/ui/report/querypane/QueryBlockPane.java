package com.vero.ui.report.querypane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.BlockType;
import com.vero.ui.constants.ImageList;
import com.vero.ui.report.dropzone.DropZonePane;

import static com.vero.ui.constants.BlockType.QUERY_BLOCK;
import static com.vero.ui.constants.CSSConstants.CLASS_REPORT_BLOCK_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SUBSECTION_TITLE;
import static com.vero.ui.constants.UIConstants.QUERY_BLOCK_PANE_HEIGHT;

public class QueryBlockPane extends BlockPane {
private DropZonePane dropZonePane = null;
    
    public QueryBlockPane(DropZonePane dropZonePane) {
        this.dropZonePane = dropZonePane;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_REPORT_BLOCK_PANE);
        setPrefHeight(QUERY_BLOCK_PANE_HEIGHT);
        setMinHeight(QUERY_BLOCK_PANE_HEIGHT);
        
        HBox headerPane = new HBox();
        headerPane.setAlignment(Pos.CENTER_LEFT);
        headerPane.getChildren().add(new ImageView(ImageList.IMAGE_ACTIVE_CIRCLE));
        Label headerLabel = new Label("REPORT BLOCK");
        headerLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        headerLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(headerLabel, Priority.ALWAYS);
        headerPane.getChildren().add(headerLabel);
        headerPane.getChildren().add(new ImageView(ImageList.IMAGE_FILTER));
        headerPane.getChildren().add(new ImageView(ImageList.IMAGE_RUN));
        setTop(headerPane);
        
        TextArea reportBlockTextArea = new TextArea();
        setCenter(reportBlockTextArea);
    }

    @Override
    public BlockType getType() {
	return QUERY_BLOCK;
    }    
}