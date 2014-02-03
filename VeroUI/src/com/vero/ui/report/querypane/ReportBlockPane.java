package com.vero.ui.report.querypane;

import static com.vero.ui.constants.BlockType.REPORT_BLOCK;
import static com.vero.ui.constants.UIConstants.REPORT_BLOCK_PANE_HEIGHT;

import com.vero.ui.constants.BlockType;
import com.vero.ui.report.dropzone.DropZonePane;

public class ReportBlockPane extends QueryBlockPane {

    public ReportBlockPane(QueryPane queryPane, DropZonePane dropZonePane) {
	super(queryPane, dropZonePane);
	buildUI();
    }
    
    private void buildUI() {
//	getStyleClass().add(CLASS_REPORT_BLOCK_PANE);
        setPrefHeight(REPORT_BLOCK_PANE_HEIGHT);
        setMinHeight(REPORT_BLOCK_PANE_HEIGHT);
        
//        HBox headerPane = new HBox();
//        headerPane.setAlignment(Pos.CENTER_LEFT);
//        headerPane.getChildren().add(new ImageView(ImageList.IMAGE_ACTIVE_CIRCLE));
//        Label headerLabel = new Label("REPORT BLOCK");
//        headerLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
//        headerLabel.setMaxWidth(Double.MAX_VALUE);
//        HBox.setHgrow(headerLabel, Priority.ALWAYS);
//        headerPane.getChildren().add(headerLabel);
//        headerPane.getChildren().add(new ImageView(ImageList.IMAGE_FILTER));
//        headerPane.getChildren().add(new ImageView(ImageList.IMAGE_RUN));
//        setTop(headerPane);
//        
//        TextArea reportBlockTextArea = new TextArea();
//        setCenter(reportBlockTextArea);
    }
    
    @Override
    public BlockType getType() {
	return REPORT_BLOCK;
    }
}
