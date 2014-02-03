package com.vero.ui.report.querypane;

import static com.vero.ui.constants.BlockType.QUERY_BLOCK;
import static com.vero.ui.constants.CSSConstants.CLASS_REPORT_BLOCK_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SUBSECTION_TITLE;
import static com.vero.ui.constants.UIConstants.QUERY_BLOCK_PANE_HEIGHT;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.BlockType;
import com.vero.ui.constants.ImageList;
import com.vero.ui.report.dropzone.DropZonePane;

public class QueryBlockPane extends BlockPane implements EventHandler<MouseEvent> {
    private QueryPane queryPane = null;
    private DropZonePane dropZonePane = null;
    private boolean selected = false;
    private ImageView statusImageView = null;
    
    public QueryBlockPane(QueryPane queryPane, DropZonePane dropZonePane) {
        this.queryPane = queryPane;
        this.dropZonePane = dropZonePane;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_REPORT_BLOCK_PANE);
        setPrefHeight(QUERY_BLOCK_PANE_HEIGHT);
        setMinHeight(QUERY_BLOCK_PANE_HEIGHT);
        
        HBox headerPane = new HBox();
        headerPane.setAlignment(Pos.CENTER_LEFT);
        statusImageView = new ImageView(ImageList.IMAGE_INACTIVE_CIRCLE);
        statusImageView.setOnMouseClicked(this);
        headerPane.getChildren().add(statusImageView);
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
    
    public boolean getSelected() {
        return selected;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
        
        statusImageView.setImage(selected ? ImageList.IMAGE_ACTIVE_CIRCLE : ImageList.IMAGE_INACTIVE_CIRCLE);
        if (selected) {
            dropZonePane.toFront();
        }
    }

    @Override
    public void handle(MouseEvent event) {        
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            if (!getSelected()) {                
                queryPane.setSelectedBlock(this);
            }
         }
    }
}