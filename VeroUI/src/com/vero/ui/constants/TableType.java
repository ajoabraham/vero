/**
 * 
 */
package com.vero.ui.constants;

import static com.vero.ui.constants.ImageList.*;

import javafx.scene.image.Image;

/**
 * @author Tai Hu
 *
 */
public enum TableType {
    DIMENSION {
	@Override
	public Image getImage() {
	    return IMAGE_DIM_TABLE;
	}
    },
    BRIDGE {
	@Override
	public Image getImage() {
	    return IMAGE_BRIDGE_TABLE;
	}
    },
    FACT {
	@Override
	public Image getImage() {
	    return IMAGE_FACT_TABLE;
	}
    },
    AGGREGATE {
	@Override
	public Image getImage() {
	    return IMAGE_AGG_TABLE;
	}
    },
    UNKNOWN {
	@Override
	public Image getImage() {
	    return IMAGE_UNKNOWN_TABLE;
	}
    };
    
    public abstract Image getImage();
}
