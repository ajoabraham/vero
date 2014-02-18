package com.vero.ui.report.querypane;

import static com.vero.ui.constants.UIConstants.DEFAULT_COMMENT_BLOCK_PANE_HEIGHT;
import static com.vero.ui.constants.CSSConstants.CLASS_COMMENT_BLOCK_PANE;
import static com.vero.ui.constants.ObjectType.COMMENT_BLOCK;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import com.vero.ui.constants.ImageList;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.CommentBlockObjectData;
import com.vero.ui.report.ReportPane;

public class CommentBlockPane extends BlockPane implements EventHandler<MouseEvent> {
    private QueryPane queryPane = null;
    private ImageView commentImageView = null;
    private CommentBlockObjectData commentBlockObjectData = null;
    private TextArea commentTextArea = null;
    
    public CommentBlockPane(ReportPane reportPane, CommentBlockObjectData commentBlockObjectData) {
	this.queryPane = reportPane.getQueryPane();
	this.commentBlockObjectData = commentBlockObjectData;
	buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_COMMENT_BLOCK_PANE);
        setPrefHeight(DEFAULT_COMMENT_BLOCK_PANE_HEIGHT);
        
        commentImageView = new ImageView(ImageList.IMAGE_COMMENT);
        commentImageView.setOnMouseClicked(this);
        BorderPane.setAlignment(commentImageView, Pos.CENTER);
        setLeft(commentImageView);
        
        commentTextArea = new TextArea();
        commentTextArea.setPromptText("This is comment block. Since its at the top its likely describing the whole report. Comments can be repositioned anywhere.");
        commentTextArea.setWrapText(true);
        commentTextArea.textProperty().bindBidirectional(commentBlockObjectData.comment());
        setCenter(commentTextArea);
    }
    
    @Override
    public ObjectType getType() {
	return COMMENT_BLOCK;
    }

    @Override
    public void handle(MouseEvent event) {
	if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
            showContextMenu();
        }       
    }

    private void showContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        
        if (getPosition() > 0) {
            MenuItem moveUpMenuItem = new MenuItem("Move Up");
            moveUpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
                public void handle(ActionEvent event) {
    	        queryPane.moveUp(CommentBlockPane.this);
                }
                
            });
            
            contextMenu.getItems().add(moveUpMenuItem);
        }
        
        if (getPosition() < queryPane.getBlockPanesSize() - 1) {
            MenuItem moveDownMenuItem = new MenuItem("Move Down");
            moveDownMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    
    	    @Override
                public void handle(ActionEvent event) {
    	        queryPane.moveDown(CommentBlockPane.this);
                }
                
            });
            
            contextMenu.getItems().add(moveDownMenuItem);
        }
        
        contextMenu.show(commentImageView, Side.BOTTOM, 0, 0);        
    }
    
    @Override
    public int getPosition() {
	return commentBlockObjectData.getPosition();
    }
    
    @Override
    public void setPosition(int position) {
	commentBlockObjectData.setPosition(position);
    }
}
