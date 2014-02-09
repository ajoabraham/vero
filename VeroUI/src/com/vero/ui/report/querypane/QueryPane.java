/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.querypane;

import static com.vero.ui.constants.CSSConstants.CLASS_QUERY_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_QUERY_PANE;
import static com.vero.ui.constants.ObjectType.COMMENT_BLOCK;
import static com.vero.ui.constants.ObjectType.QUERY_BLOCK;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import com.vero.ui.model.BlockObjectData;
import com.vero.ui.model.CommentBlockObjectData;
import com.vero.ui.model.QueryBlockObjectData;
import com.vero.ui.model.ReportObjectData;
import com.vero.ui.report.ReportPane;
import com.vero.ui.report.dropzone.DropZonePane;

/**
 *
 * @author Tai Hu
 */
public class QueryPane extends ScrollPane {
    private ReportPane reportPane = null;
    private ReportObjectData reportObjectData = null;
    private StackPane dropZonePaneContainer = null;
    
    private List<BlockPane> blockPanes = null;
    private QueryBlockPane selectedQueryBlockPane = null;
    private GlobalFilterPane globalFilterPane = null;
    private ReportBlockPane reportBlockPane = null;
    
    private VBox contentPane = null;
    
    public QueryPane(ReportPane reportPane) {
	this.reportPane = reportPane;
	this.reportObjectData = reportPane.getReportObjectData();
        this.dropZonePaneContainer = reportPane.getDropZonePaneContainer();
        blockPanes = new ArrayList<BlockPane>();
	
        buildUI();
        populateData();
    }
    
    private void buildUI() {
	getStyleClass().add(CLASS_QUERY_PANE);
	setFitToWidth(true);
	contentPane = new VBox();
        contentPane.getStyleClass().add(CLASS_QUERY_CONTENT_PANE);
        setContent(contentPane);
    }
    
    private void populateData() {
        DropZonePane dropZonePane = new DropZonePane(reportPane);
        globalFilterPane = new GlobalFilterPane(reportObjectData.getGlobalFilterObjectData());
        contentPane.getChildren().add(globalFilterPane);
        
        for (BlockObjectData blockObjectData : reportObjectData.getBlockObjectDataList()) {
            if (blockObjectData.getType() == COMMENT_BLOCK) {
                BlockPane commentBlockPane = BlockPaneFactory.createCommentBlockPane(this, (CommentBlockObjectData) blockObjectData);
                blockPanes.add(commentBlockPane);
                contentPane.getChildren().add(commentBlockPane);
            }
            else if (blockObjectData.getType() == QUERY_BLOCK) {
                BlockPane queryBlockPane = BlockPaneFactory.createQueryBlockPane(this, dropZonePane, (QueryBlockObjectData) blockObjectData);
                blockPanes.add(queryBlockPane);
                contentPane.getChildren().add(queryBlockPane);
            }
        }
        
        BlockPane reportBlockPane = BlockPaneFactory.createReportBlockPane(this, dropZonePane, reportObjectData.getReportBlockObjectData());
        contentPane.getChildren().add(reportBlockPane);
        
        dropZonePaneContainer.getChildren().add(dropZonePane);

        ((QueryBlockPane) reportBlockPane).setSelected(true);
        selectedQueryBlockPane = (QueryBlockPane) reportBlockPane;
    }
    
    public void addNewCommentBlockPane() {
        CommentBlockObjectData commentObjectData = new CommentBlockObjectData();
        reportObjectData.addBlockObjectData(commentObjectData);
        BlockPane commentBlock = BlockPaneFactory.createCommentBlockPane(this, commentObjectData);
        blockPanes.add(commentBlock);
        
        contentPane.getChildren().add(1, commentBlock);
    }
    
    public void addNewQueryBlockPane() {
        DropZonePane dropZonePane = new DropZonePane(reportPane);
        QueryBlockObjectData queryBlockObjectData = new QueryBlockObjectData();
        reportObjectData.addBlockObjectData(queryBlockObjectData);
        BlockPane queryBlock = BlockPaneFactory.createQueryBlockPane(this, dropZonePane, queryBlockObjectData);
        blockPanes.add(queryBlock);
        
        contentPane.getChildren().add(1, queryBlock);
        dropZonePaneContainer.getChildren().add(dropZonePane);
        
        if (selectedQueryBlockPane != null) {
            selectedQueryBlockPane.setSelected(false);
        }
        
        selectedQueryBlockPane = (QueryBlockPane) queryBlock;
        selectedQueryBlockPane.setSelected(true);
    }
    
    public void setSelectedBlock(QueryBlockPane blockPane) {
        if (selectedQueryBlockPane != blockPane) {            
            selectedQueryBlockPane.setSelected(false);
            selectedQueryBlockPane = blockPane;
            selectedQueryBlockPane.setSelected(true);
        }
    }
    
    public void moveUp(BlockPane blockPane) {
        int index = contentPane.getChildren().indexOf(blockPane);        
        if (index > 1) {
//            Collections.swap(blockPanes, index - 1, index);
            contentPane.getChildren().remove(blockPane);
            contentPane.getChildren().add(index - 1, blockPane);
        }
    }
    
    public void moveDown(BlockPane blockPane) {
        int index = contentPane.getChildren().indexOf(blockPane);        
        if (index < (blockPanes.size() - 2)) {         
//            Collections.swap(blockPanes, index, index + 1); 
	    contentPane.getChildren().remove(blockPane);
	    contentPane.getChildren().add(index + 1, blockPane);
        }
    }
}
