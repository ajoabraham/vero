package com.vero.ui.report.querypane;

import static com.vero.ui.constants.BlockType.COMMENT_BLOCK;
import static com.vero.ui.constants.CSSConstants.CLASS_COMMENT_BLOCK_PANE;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import com.vero.ui.constants.BlockType;
import com.vero.ui.constants.ImageList;

public class CommentBlockPane extends BlockPane {

    public CommentBlockPane() {
	buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_COMMENT_BLOCK_PANE);
        
        setLeft(new ImageView(ImageList.IMAGE_COMMENT));
        
        Label commentLabel = new Label("This is comment block. Since its at the top its likely describing the whole report. Comments can be repositioned anywhere.");
        setCenter(commentLabel);
    }
    
    @Override
    public BlockType getType() {
	return COMMENT_BLOCK;
    }

}
