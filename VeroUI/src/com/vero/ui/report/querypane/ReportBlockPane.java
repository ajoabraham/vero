package com.vero.ui.report.querypane;

import static com.vero.ui.constants.BlockType.REPORT_BLOCK;
import static com.vero.ui.constants.CSSConstants.CLASS_REPORT_BLOCK_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SUBSECTION_TITLE;
import static com.vero.ui.constants.UIConstants.REPORT_BLOCK_PANE_HEIGHT;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.BlockType;
import com.vero.ui.constants.ImageList;

public class ReportBlockPane extends BlockPane {
    public ReportBlockPane() {
	buildUI();
    }
    
    private void buildUI() {
	getStyleClass().add(CLASS_REPORT_BLOCK_PANE);
        setPrefHeight(REPORT_BLOCK_PANE_HEIGHT);
        setMinHeight(REPORT_BLOCK_PANE_HEIGHT);
        
        HBox headerPane = new HBox();
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
	return REPORT_BLOCK;
    }

}
