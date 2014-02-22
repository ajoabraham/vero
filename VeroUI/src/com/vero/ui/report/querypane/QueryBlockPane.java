package com.vero.ui.report.querypane;

import static com.vero.ui.constants.CSSConstants.CLASS_QUERY_BLOCK_BUTTON;
import static com.vero.ui.constants.CSSConstants.CLASS_REPORT_BLOCK_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SUBSECTION_TITLE;
import static com.vero.ui.constants.ObjectType.QUERY_BLOCK;
import static com.vero.ui.constants.UIConstants.DEFAULT_QUERY_BLOCK_BUTTON_HEIGHT;
import static com.vero.ui.constants.UIConstants.DEFAULT_QUERY_BLOCK_BUTTON_WIDTH;
import static com.vero.ui.constants.UIConstants.QUERY_BLOCK_PANE_HEIGHT;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.common.ConfirmationDialogs;
import com.vero.ui.constants.ImageList;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.DatabaseObjectData;
import com.vero.ui.model.QueryBlockObjectData;
import com.vero.ui.report.ReportPane;
import com.vero.ui.report.dropzone.DropZonePane;
import com.vero.ui.service.QueryExecutionService;
import com.vero.ui.service.ServiceException;
import com.vero.ui.service.ServiceManager;

public class QueryBlockPane extends BlockPane implements EventHandler<MouseEvent> {
    private QueryPane queryPane = null;
    private DropZonePane dropZonePane = null;
    private QueryBlockObjectData queryBlockObjectData = null;
    private boolean selected = false;
    private ImageView statusImageView = null;
    private HBox headerPane = null;
    private TextArea sqlTextArea = null;
    private Button filterButton = null;
    private Button runButton = null;
    
    public QueryBlockPane(ReportPane reportPane, DropZonePane dropZonePane, QueryBlockObjectData queryBlockObjectData) {
        this.queryPane = reportPane.getQueryPane();
        this.dropZonePane = dropZonePane;
        this.dropZonePane.setQueryBlockPane(this);
        this.queryBlockObjectData = queryBlockObjectData;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_REPORT_BLOCK_PANE);
        setPrefHeight(QUERY_BLOCK_PANE_HEIGHT);
        setMinHeight(QUERY_BLOCK_PANE_HEIGHT);
        
        headerPane = new HBox();
        headerPane.setAlignment(Pos.CENTER_LEFT);
        statusImageView = new ImageView(ImageList.IMAGE_INACTIVE_CIRCLE);
        headerPane.getChildren().add(statusImageView);
        headerPane.setOnMouseClicked(this);
        Label headerLabel = new Label("REPORT BLOCK");
        headerLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        headerLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(headerLabel, Priority.ALWAYS);
        headerPane.getChildren().add(headerLabel);
        
        filterButton = new Button();
        filterButton.getStyleClass().add(CLASS_QUERY_BLOCK_BUTTON);
        filterButton.setGraphic(new ImageView(ImageList.IMAGE_FILTER));
        filterButton.setPrefSize(DEFAULT_QUERY_BLOCK_BUTTON_WIDTH, DEFAULT_QUERY_BLOCK_BUTTON_HEIGHT);
        filterButton.setMinSize(DEFAULT_QUERY_BLOCK_BUTTON_WIDTH, DEFAULT_QUERY_BLOCK_BUTTON_HEIGHT);
        filterButton.setTooltip(new Tooltip("Filter"));
        filterButton.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
            public void handle(ActionEvent event) {
	        
            }            
        });
        headerPane.getChildren().add(filterButton);
        
        runButton = new Button();
        runButton.getStyleClass().add(CLASS_QUERY_BLOCK_BUTTON);
        runButton.setGraphic(new ImageView(ImageList.IMAGE_RUN));
        runButton.setPrefSize(DEFAULT_QUERY_BLOCK_BUTTON_WIDTH, DEFAULT_QUERY_BLOCK_BUTTON_HEIGHT);
        runButton.setMinSize(DEFAULT_QUERY_BLOCK_BUTTON_WIDTH, DEFAULT_QUERY_BLOCK_BUTTON_HEIGHT);
        runButton.setTooltip(new Tooltip("Run"));
        runButton.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
            public void handle(ActionEvent event) {
	        QueryExecutionService service = ServiceManager.getQueryExecutionService();
	        DatabaseObjectData databaseObjectData = queryBlockObjectData.getDatasourceObjectData().getDatabaseObjectData();
	        
	        try {
	            service.executeQuery(databaseObjectData, sqlTextArea.getText());
	        }
	        catch (ServiceException e) {
	            ConfirmationDialogs.createErrorConfirmation(null, e.getMessage());
	        }
            }            
        });
        headerPane.getChildren().add(runButton);
        
        setTop(headerPane);
        
        sqlTextArea = new TextArea();
        sqlTextArea.setEditable(false);
        setCenter(sqlTextArea);
    }

    @Override
    public ObjectType getType() {
	return QUERY_BLOCK;
    }    
    
    public boolean getSelected() {
        return selected;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
        
        statusImageView.setImage(selected ? ImageList.IMAGE_ACTIVE_CIRCLE : ImageList.IMAGE_INACTIVE_CIRCLE);
        if (selected) {
            dropZonePane.toFront();
        }
    }

    @Override
    public void handle(MouseEvent event) {        
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            if (!getSelected()) {                
                queryPane.setSelectedQueryBlockPane(this);
            }
        }
        else if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
            showContextMenu();
        }       
    }

    protected void showContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        
        if (getPosition() > 0) {
            MenuItem moveUpMenuItem = new MenuItem("Move Up");
            moveUpMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
                public void handle(ActionEvent event) {
    	        queryPane.moveUp(QueryBlockPane.this);
                }
                
            });
        
            contextMenu.getItems().add(moveUpMenuItem);
        }
        
        if (getPosition() < queryPane.getBlockPanesSize() - 1) {
            MenuItem moveDownMenuItem = new MenuItem("Move Down");
            moveDownMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    
    	    @Override
                public void handle(ActionEvent event) {
    	        queryPane.moveDown(QueryBlockPane.this);
                }
                
            });
            
            contextMenu.getItems().add(moveDownMenuItem);
        }
        
        contextMenu.show(headerPane, Side.BOTTOM, 0, 0);   
    }
    
    @Override
    public int getPosition() {
	return queryBlockObjectData.getPosition();
    }
    
    @Override
    public void setPosition(int position) {
	queryBlockObjectData.setPosition(position);
    }

    public QueryBlockObjectData getQueryBlockObjectData() {
        return queryBlockObjectData;
    }

    public void setQueryBlockObjectData(QueryBlockObjectData queryBlockObjectData) {
        this.queryBlockObjectData = queryBlockObjectData;
    }
    
    public void setSQLString(String sqlString) {
	sqlTextArea.setText(sqlString);
    }
}