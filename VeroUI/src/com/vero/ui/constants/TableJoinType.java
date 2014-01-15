/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.constants;

import javafx.scene.image.Image;

/**
 * 
 * @author Tai Hu
 */
public enum TableJoinType {
    FULL_OUTER_JOIN {
        @Override
        public TableJoinType next() {
            return CROSS_JOIN;
        }

        @Override
        public Image getImage() {
            return ImageList.IMAGE_FULL_OUTER_JOIN;
        }
    },
    CROSS_JOIN {
        @Override
        public TableJoinType next() {
            return INNER_JOIN;
        }

        @Override
        public Image getImage() {
            return ImageList.IMAGE_CROSS_JOIN;
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
            return FULL_OUTER_JOIN;
        }

        @Override
        public Image getImage() {
            return ImageList.IMAGE_RIGHT_JOIN;
        }
    };

    public abstract TableJoinType next();

    public abstract Image getImage();
}
