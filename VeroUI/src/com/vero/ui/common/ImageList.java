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
    public static final Image IMAGE_NEW = new Image(VeroUI.class.getResourceAsStream("images/icon_report.png"));
    public static final Image IMAGE_SAVE = new Image(VeroUI.class.getResourceAsStream("images/icon_save.png"));
    public static final Image IMAGE_OPEN = new Image(VeroUI.class.getResourceAsStream("images/icon_open.png"));
    public static final Image IMAGE_RUN = new Image(VeroUI.class.getResourceAsStream("images/icon_run.png"));
    public static final Image IMAGE_ADD_BLOCK = new Image(VeroUI.class.getResourceAsStream("images/icon_add_block.png"));
    public static final Image IMAGE_DELETE = new Image(VeroUI.class.getResourceAsStream("images/icon_delete.png"));
    public static final Image IMAGE_INNER_JOIN = new Image(VeroUI.class.getResourceAsStream("images/icon_inner_join.png"));
    public static final Image IMAGE_COMMENT = new Image(VeroUI.class.getResourceAsStream("images/icon_comment.png"));
    public static final Image IMAGE_ACTIVE_CIRCLE = new Image(VeroUI.class.getResourceAsStream("images/icon_active_circle.png"));
    public static final Image IMAGE_INACTIVE_CIRCLE = new Image(VeroUI.class.getResourceAsStream("images/icon_inactive_circle.png"));
    public static final Image IMAGE_FILTER = new Image(VeroUI.class.getResourceAsStream("images/icon_filter.png"));
    public static final Image IMAGE_DATASOURCE_OBJECT = new Image(VeroUI.class.getResourceAsStream("images/icon_datasource.png"));
    public static final Image IMAGE_AGG_TABLE = new Image(VeroUI.class.getResourceAsStream("images/icon_agg_table.png"));
    public static final Image IMAGE_BRIDGE_TABLE = new Image(VeroUI.class.getResourceAsStream("images/icon_bridge_table.png"));
    public static final Image IMAGE_DIM_TABLE = new Image(VeroUI.class.getResourceAsStream("images/icon_dim_table.png"));
    public static final Image IMAGE_FACT_TABLE = new Image(VeroUI.class.getResourceAsStream("images/icon_fact_table.png"));
    public static final Image IMAGE_UNKNOWN_TABLE = new Image(VeroUI.class.getResourceAsStream("images/icon_unknown_table.png"));
}
