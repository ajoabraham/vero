/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.COMMENT_BLOCK;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class CommentBlockObjectData extends BlockObjectData {
    private IntegerProperty position = new SimpleIntegerProperty();
    private StringProperty comment = new SimpleStringProperty();
    
    public CommentBlockObjectData() {
	comment.set("This is comment block. Since its at the top its likely describing the whole report. Comments can be repositioned anywhere.");
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
