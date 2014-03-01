/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.constants;

import static com.vero.metadata.JoinDefinition.JoinType.*;
import com.vero.metadata.JoinDefinition.JoinType;

import javafx.scene.image.Image;

/**
 * 
 * @author Tai Hu
 */
public enum TableJoinType {
    CROSS_JOIN {
	@Override
	public TableJoinType next() {
	    return FULL_OUTER_JOIN;
	}

	@Override
	public Image getImage() {
	    return ImageList.IMAGE_CROSS_JOIN;
	}
    },
    FULL_OUTER_JOIN {
	@Override
	public TableJoinType next() {
	    return INNER_JOIN;
	}

	@Override
	public Image getImage() {
	    return ImageList.IMAGE_FULL_OUTER_JOIN;
	}
    },
    INNER_JOIN {
	@Override
	public TableJoinType next() {
	    return LEFT_JOIN;
	}

	@Override
	public Image getImage() {
	    return ImageList.IMAGE_INNER_JOIN;
	}
    },
    LEFT_JOIN {
	@Override
	public TableJoinType next() {
	    return RIGHT_JOIN;
	}

	@Override
	public Image getImage() {
	    return ImageList.IMAGE_LEFT_JOIN;
	}
    },
    RIGHT_JOIN {
	@Override
	public TableJoinType next() {
	    return CROSS_JOIN;
	}

	@Override
	public Image getImage() {
	    return ImageList.IMAGE_RIGHT_JOIN;
	}
    };

    public abstract TableJoinType next();

    public abstract Image getImage();

    public static TableJoinType convertType(JoinType joinType) {
	switch (joinType) {
	case CROSS:
	case NONE:
	    return CROSS_JOIN;
	case OUTER:
	    return FULL_OUTER_JOIN;
	case INNER:
	    return INNER_JOIN;
	case LEFT:
	    return LEFT_JOIN;
	case RIGHT:
	    return RIGHT_JOIN;
	default:
	    return CROSS_JOIN;
	}
    }

    public static JoinType convertType(TableJoinType joinType) {
	switch (joinType) {
	case CROSS_JOIN:
	    return CROSS;
	case FULL_OUTER_JOIN:
	    return OUTER;
	case INNER_JOIN:
	    return INNER;
	case LEFT_JOIN:
	    return LEFT;
	case RIGHT_JOIN:
	    return RIGHT;
	default:
	    return NONE;
	}
    }
}
