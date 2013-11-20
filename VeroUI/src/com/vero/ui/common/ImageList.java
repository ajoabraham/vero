/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.common;

import com.vero.ui.VeroUI;
import javafx.scene.image.Image;

/**
 *
 * @author Tai Hu
 */
public interface ImageList {
    public static final Image IMAGE_SAVE = new Image(VeroUI.class.getResourceAsStream("images/save_icon.png"));
    public static final Image IMAGE_OPEN = new Image(VeroUI.class.getResourceAsStream("images/open_icon.png"));
    public static final Image IMAGE_RUN = new Image(VeroUI.class.getResourceAsStream("images/run_icon.png"));
    public static final Image IMAGE_ADD_BLOCK = new Image(VeroUI.class.getResourceAsStream("images/add_block_icon.png"));
    public static final Image IMAGE_DELETE = new Image(VeroUI.class.getResourceAsStream("images/delete_icon.png"));
    public static final Image IMAGE_INNER_JOIN = new Image(VeroUI.class.getResourceAsStream("images/inner_join_icon.png"));
    public static final Image IMAGE_COMMENT = new Image(VeroUI.class.getResourceAsStream("images/comment_icon.png"));
    public static final Image IMAGE_ACTIVE_CIRCLE = new Image(VeroUI.class.getResourceAsStream("images/active_circle_icon.png"));
    public static final Image IMAGE_FILTER = new Image(VeroUI.class.getResourceAsStream("images/filter_icon.png"));
}
