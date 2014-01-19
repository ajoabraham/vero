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

        @Override
        public String getName() {
            return "DIMENSION";
        }
    },
    BRIDGE {
        @Override
        public Image getImage() {
            return IMAGE_BRIDGE_TABLE;
        }

        @Override
        public String getName() {
            return "BRIDGE";
        }
    },
    FACT {
        @Override
        public Image getImage() {
            return IMAGE_FACT_TABLE;
        }

        @Override
        public String getName() {
            return "FACT";
        }
    },
    AGGREGATE {
        @Override
        public Image getImage() {
            return IMAGE_AGG_TABLE;
        }

        @Override
        public String getName() {
            return "AGGREGATE";
        }
    },
    UNKNOWN {
        @Override
        public Image getImage() {
            return IMAGE_UNKNOWN_TABLE;
        }

        @Override
        public String getName() {
            return "UNKNOWN";
        }
    };

    public abstract Image getImage();

    public abstract String getName();
}
