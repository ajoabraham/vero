package com.vero.ui.report.querypane;

import static com.vero.ui.constants.CSSConstants.CLASS_REPORT_BLOCK_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SUBSECTION_TITLE;
import static com.vero.ui.constants.ObjectType.QUERY_BLOCK;
import static com.vero.ui.constants.UIConstants.QUERY_BLOCK_PANE_HEIGHT;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.ImageList;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.QueryBlockObjectData;
import com.vero.ui.report.dropzone.DropZonePane;

public class QueryBlockPane extends BlockPane implements EventHandler<MouseEvent> {
    private QueryPane queryPane = null;
    private DropZonePane dropZonePane = null;
    private QueryBlockObjectData queryBlockObjectData = null;
    private boolean selected = false;
    private ImageView statusImageView = null;
    private HBox headerPane = null;
    
    public QueryBlockPane(QueryPane queryPane, DropZonePane dropZonePane, QueryBlockObjectData queryBlockObjectData) {
        this.queryPane = queryPane;
        this.dropZonePane = dropZonePane;
        this.queryBlockObjectData = queryBlockObjectData;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_REPORT_BLOCK_PANE);
        setPrefHeight(QUERY_BLOCK_PANE_HEIGHT);
        setMinHeight(QUERY_BLOCK_PANE_HEIGHT);
        
        headerPane = new HBox();
        headerPane.setAlignment(Pos.CENTER_LEFT);
        statusImageView = new ImageView(ImageList.IMAGE_INACTIVE_CIRCLE);
        headerPane.getChildren().add(statusImageView);
        headerPane.setOnMouseClicked(this);
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
    public ObjectType getType() {
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
        else if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
            showContextMenu();
        }       
    }

    protected void showContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        
        if (getPosition() > 0) {
            MenuItem moveUpMenuItem = new MenuItem("Move Up");
            moveUpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
                public void handle(ActionEvent event) {
    	        queryPane.moveUp(QueryBlockPane.this);
                }
                
            });
        
            contextMenu.getItems().add(moveUpMenuItem);
        }
        
        if (getPosition() < queryPane.getBlockPanesSize() - 1) {
            MenuItem moveDownMenuItem = new MenuItem("Move Down");
            moveDownMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    
    	    @Override
                public void handle(ActionEvent event) {
    	        queryPane.moveDown(QueryBlockPane.this);
                }
                
            });
            
            contextMenu.getItems().add(moveDownMenuItem);
        }
        
        contextMenu.show(headerPane, Side.BOTTOM, 0, 0);   
    }
    
    @Override
    public int getPosition() {
	return queryBlockObjectData.getPosition();
    }
    
    @Override
    public void setPosition(int position) {
	queryBlockObjectData.setPosition(position);
    }
}