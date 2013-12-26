/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.constants;

/**
 *
 * @author Tai Hu
 */
public interface CSSConstants {
    public static final String VERO_CSS_FILE = "VeroUI.css";
    
    /**
     * ID for VERO main window
     */
    public static final String ID_ROOT_PANE = "root-pane";
    
    /**
     * ID for VERO tool bar
     */
    public static final String ID_VERO_TOOL_BAR = "vero-tool-bar";
    public static final String ID_DATASOURCES_TOOL_BAR_BUTTON = "datasources-tool-bar-button";
    public static final String ID_REPORTS_TOOL_BAR_BUTTON = "reports-tool-bar-button";
    
    /**
     * ID for navigation pane
     */
    public static final String ID_DATASOURCE_NAVIGATION_PANE = "datasource-navigation-pane";
    public static final String ID_REPORT_NAVIGATION_PANE = "report-navigation-pane";
    public static final String ID_OBJECT_TREE_VIEW = "object-tree-view";
    public static final String CLASS_SECTION_TITLE = "section-title";
    public static final String ID_OBJECTS_PANE = "objects-pane";
    public static final String ID_OBJECT_SEARCH_TEXT_FIELD = "object-search-text-field";
    public static final String CLASS_LABEL_PANE = "label-pane";
    public static final String CLASS_OBJECT_PANE = "object-pane";
    public static final String CLASS_OBJECT_LABEL = "object-label";
    public static final String CLASS_DATASOURCE_OBJECT_PANE = "datasource-object-pane";
    public static final String CLASS_TABLE_OBJECT_PANE = "table-object-pane";
    public static final String CLASS_TABLE_JOIN_OBJECT_PANE = "table-join-object-pane";
    public static final String CLASS_COLUMN_OBJECT_PANE = "column-object-pane";
    public static final String CLASS_ATTRIBUTE_OBJECT_PANE = "attribute-object-pane";
    public static final String CLASS_METRIC_OBJECT_PANE = "metric-object-pane";
    
    /**
     * ID for drop zone pane
     */
    // ID for drop zone main scroll pane
    public static final String ID_DROP_ZONE_PANE = "drop-zone-pane";
    // style class for actual content pane inside of drop zone pane.
    public static final String CLASS_DROP_ZONE_CONTENT_PANE = "drop-zone-content-pane";
    public static final String CLASS_SUBSECTION_TITLE = "subsection-title";
    public static final String CLASS_DROP_PANE = "drop-pane";
    public static final String CLASS_PLACEHOLDER_PANE = "placeholder-pane";
    public static final String CLASS_DROP_HINT_PANE = "drop-hint-pane";
    public static final String CLASS_EDITABLE_PANE = "editable-pane";
    public static final String CLASS_DROP_ZONE_OBJECT_PANE = CLASS_OBJECT_PANE;
    
    /**
     * ID for query pane
     */
    public static final String CLASS_QUERY_PANE = "query-pane";
    public static final String CLASS_GLOBAL_FILTER_PANE = "global-filter-pane";
    public static final String CLASS_COMMENT_BLOCK_PANE = "comment-block-pane";
    public static final String CLASS_REPORT_BLOCK_PANE = "report-block-pane";
    
    /**
     * ID for editor pane
     */
    public static final String CLASS_EDITOR_PANE = "editor-pane";
    public static final String CLASS_DOCKED_EDITOR_PANE = "docked-editor-pane";
    public static final String CLASS_UNDOCKED_EDITOR_PANE = "undocked-editor-pane";
    public static final String CLASS_EDITOR_PANE_TOOL_BAR = "editor-pane-tool-bar";
    public static final String CLASS_METRIC_ICON_LABEL = "metric-icon-label";
    // Style class for all content panes and should be styled as .docked-editor-pane .content-pane
    public static final String CLASS_CONTENT_PANE = "content-pane";
    // Style class for all object container panes in editor pane (e.x. order by, partition by, etc)
    public static final String CLASS_OBJECT_CONTAINER_PANE = "object-container-pane";
}