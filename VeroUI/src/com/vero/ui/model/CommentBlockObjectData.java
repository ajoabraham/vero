/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.COMMENT_BLOCK;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.vero.model.entities.SchemaCommentBlock;
import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class CommentBlockObjectData extends BlockObjectData {
    private SchemaCommentBlock schemaCommentBlock = null;
    
    private IntegerProperty position = new SimpleIntegerProperty();
    private StringProperty comment = new SimpleStringProperty();
    
    public CommentBlockObjectData() {
	this(new SchemaCommentBlock());
    }
    
    public CommentBlockObjectData(SchemaCommentBlock schemaCommentBlock) {
	super(schemaCommentBlock);
	this.schemaCommentBlock = schemaCommentBlock;
	
	// init data
	comment.set(schemaCommentBlock.getComment());
	comment.addListener(new ChangeListener<String>() {

	    @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		CommentBlockObjectData.this.schemaCommentBlock.setComment(newValue);
            }
	    
	});

	position.set(schemaCommentBlock.getPosition());
	position.addListener(new ChangeListener<Number>() {

	    @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		CommentBlockObjectData.this.schemaCommentBlock.setPosition(newValue.intValue());
            }
	    
	});
	
    }
    

    @Override
    public ObjectType getType() {
	return COMMENT_BLOCK;
    }

    public int getPosition() {
        return position.get();
    }

    public void setPosition(int position) {
        this.position.set(position);
    }
    
    public IntegerProperty position() {
	return position;
    }

    public String getComment() {
        return comment.get();
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }
    
    public StringProperty comment() {
	return comment;
    }
}
