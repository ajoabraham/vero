/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_DROP_PANE;
import static com.vero.ui.constants.ObjectType.ATTRIBUTE;
import static com.vero.ui.constants.ObjectType.COLUMN;
import static com.vero.ui.constants.ObjectType.METRIC;
import static com.vero.ui.constants.UIConstants.DEFAULT_DROP_PANE_HEIGHT;
import static com.vero.ui.constants.UIConstants.DEFAULT_LABEL_PANE_HEIGHT;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import com.vero.metadata.JoinDefinition;
import com.vero.report.Block;
import com.vero.report.Report;
import com.vero.ui.common.DroppableObject;
import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.constants.TableJoinType;
import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.ExpressionObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.PositionableObjectData;
import com.vero.ui.model.QueryBlockObjectData;
import com.vero.ui.model.TableJoinObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.model.UIData;
import com.vero.ui.report.ReportPane;
import com.vero.ui.report.querypane.QueryBlockPane;
import com.vero.ui.service.ServiceManager;

/**
 *
 * @author Tai Hu
 */
public abstract class DropTargetPane extends VBox implements DroppableObject, ListChangeListener<UIData>, EventHandler<MouseEvent> {
    private static final Logger logger = Logger.getLogger(DropTargetPane.class.getName());
    
    private static final String ATTRIBUTE_PLACEHOLDER_HINT = "drag attributes or columns here...";
    private static final String METRIC_PLACEHOLDER_HINT = "drag metrics or columns here...";
    private static final String TABLE_PLACEHOLDER_HINT = "drag tables here as query hints...";
    private static final String TABLE_JOIN_PLACEHOLDER_HINT = "This is not a drop zone...";
            
    private int currentDropIndex = -1;
    private boolean isEmpty = true;
    private boolean nondropCase = false;
    
    ReportPane reportPane = null;
    private DropZonePane dropZonePane = null;
    
    public DropTargetPane(ReportPane reportPane, DropZonePane dropZonePane) {
        this.reportPane = reportPane;
        this.dropZonePane = dropZonePane;
        
        getStyleClass().add(CLASS_DROP_PANE);
        setPrefHeight(DEFAULT_DROP_PANE_HEIGHT);
        setMaxHeight(Double.MAX_VALUE);
    }
    
    public abstract ObjectType getType();

    @Override
    public Node getDropTarget() {
        return this;
    }

    @Override
    public boolean acceptDrop(UIData transferData) {
	boolean acceptDrop = getType() == transferData.getType();
	
	if (!acceptDrop) {
	    if (transferData.getType() == COLUMN && (getType() == ATTRIBUTE || getType() == METRIC)) {
		acceptDrop = true;
	    }
	}
	
        return acceptDrop;
    }
    
    @Override
    public void handleDragOverEvent(DragEvent event) {
        if (!isEmpty) {
            int index = computeDropIndex(event.getY());
            List<Node> children = getChildren();
            
            if (index <= children.size()) {
                // Very first time
                if (currentDropIndex == -1) {
                    double newHeight = computePrefHeight(children.size() + 1);
                    setPrefHeight(newHeight);
                    children.add(index, LabelPaneFactory.createDropHintPane());
                    currentDropIndex = index;
                }
                else if (index != currentDropIndex && index != currentDropIndex + 1 ){
                    children.add(index, LabelPaneFactory.createDropHintPane());
                    children.remove(index < currentDropIndex ? currentDropIndex + 1 : currentDropIndex);
                    
                    currentDropIndex = index > currentDropIndex ? index - 1 : index;
                }
            }
        }
    }

    @Override
    public void handleDragEnteredEvent(DragEvent event) {
        List<Node> children = getChildren();
        
        // Empty drop pane
        if (children.get(0) instanceof PlaceholderPane) {
            children.set(0, LabelPaneFactory.createDropHintPane());
            isEmpty = true;
        }
    }

    @Override
    public void handleDragExitedEvent(DragEvent event) {
        List<Node> children = getChildren();
        
        // Empty drop pane
        if (isEmpty) {
            children.set(0, LabelPaneFactory.createPlaceholderPane(getPlaceholderText()));
        }
    }

    @Override
    public void handleDragDroppedEvent(DragEvent event, UIData transferData) {
	
	// Drop logic
	// 1. If drop a column, create a default attribute or metric
	if (transferData.getType() == COLUMN) {
	    QueryBlockPane queryBlockPane = dropZonePane.getQueryBlockPane();
	    QueryBlockObjectData queryBlockObjectData = queryBlockPane.getQueryBlockObjectData();
	    ColumnObjectData columnObjectData = (ColumnObjectData) transferData;
	    if (getType() == ATTRIBUTE) {
		AttributeObjectData attributeObjectData = new AttributeObjectData();
		attributeObjectData.setName(columnObjectData.getName());
		ExpressionObjectData expressionObjectData = new ExpressionObjectData();
		expressionObjectData.setFormula(columnObjectData.getName());
		expressionObjectData.addColumnObjectData(columnObjectData);
		attributeObjectData.addExpressionObjectData(expressionObjectData);
		attributeObjectData.setSelectedExpressionObjectData(expressionObjectData);
		
	        // Link data
		queryBlockObjectData.setDatasourceObjectData(columnObjectData.getTableObjectData().getDatasourceObjectData());
                
		// Add attribute
		queryBlockObjectData.addAttributeObjectData(attributeObjectData);
		
		// Add table
		queryBlockObjectData.addTableObjectData(attributeObjectData, columnObjectData.getTableObjectData());		
	    }
	    else if (getType() == METRIC) {
		MetricObjectData metricObjectData = new MetricObjectData();
		metricObjectData.setName(columnObjectData.getName());
		ExpressionObjectData expressionObjectData = new ExpressionObjectData();
		expressionObjectData.setFormula("sum(" + columnObjectData.getName() + ")");
		expressionObjectData.addColumnObjectData(columnObjectData);
		metricObjectData.addExpressionObjectData(expressionObjectData);
		metricObjectData.setSelectedExpressionObjectData(expressionObjectData);
		
	        // Link data            
		queryBlockObjectData.setDatasourceObjectData(columnObjectData.getTableObjectData().getDatasourceObjectData());
                
                // Add metric
		queryBlockObjectData.addMetricObjectData(metricObjectData);
		
		// Add table		
		queryBlockObjectData.addTableObjectData(metricObjectData, columnObjectData.getTableObjectData());
	    }
	    
	    updateQuery();
//	    // Generate SQL
//	    Report report = ServiceManager.getQueryEngineService().generateReportMetadata(queryBlockObjectData);
//	    Block block = report.getBlocks().get(0);
//	    // Show SQL in query block
//	    queryBlockPane.setSQLString(block.getSqlString());
//	    
//	    // Set up table alias
//            Map<String, String> tableAliasMap = block.getTableMap();
//	    for (TableObjectData tableObjectData : queryBlockObjectData.getTableObjectDataList()) {
//	        String alias = tableAliasMap.get(tableObjectData.getId());	        
//	        tableObjectData.setAlias(alias);
//	    }
//	    
//	    // Set up selected expression and table
//	    Map<String, String> attributeExpressionMap = block.getAttributeMap();
//	    Map<String, String> expressionTableMap = block.getExpressionMap();
//	    for (AttributeObjectData attributeObjectData : queryBlockObjectData.getAttributeObjectDataList()) {
//		String expressionId = attributeExpressionMap.get(attributeObjectData.getId().toLowerCase());
//		ExpressionObjectData selectedExpression = attributeObjectData.getExpressionObjectDataById(expressionId);
//		attributeObjectData.setSelectedExpressionObjectData(selectedExpression);
//		
//		String tableId = expressionTableMap.get(expressionId);
//		TableObjectData selectedTable = selectedExpression.getTableObjectDataById(tableId);
//		selectedExpression.setSelectedTableObjectData(selectedTable);
//	    }
//	    
//	    Map<String, String> metricExpressionMap = block.getMetricMap();
//	    for (MetricObjectData metricObjectData : queryBlockObjectData.getMetricObjectDataList()) {
//		String expressionId = metricExpressionMap.get(metricObjectData.getId().toLowerCase());
//		ExpressionObjectData selectedExpression = metricObjectData.getExpressionObjectDataById(expressionId);
//		metricObjectData.setSelectedExpressionObjectData(selectedExpression);
//		
//		String tableId = expressionTableMap.get(expressionId);
//		TableObjectData selectedTable = selectedExpression.getTableObjectDataById(tableId);
//		selectedExpression.setSelectedTableObjectData(selectedTable);
//	    }
//	    
//	    // Update table joins
//	    List<JoinDefinition> joinDefs = block.getJoinDefList();
//	    for (JoinDefinition joinDef : joinDefs) {
//	        boolean isNew = false;
//		TableJoinObjectData tableJoinObjectData = queryBlockObjectData.getTableJoinObjectDataById(joinDef.getUUIDStr());
//		if (tableJoinObjectData == null) {
//		    tableJoinObjectData = new TableJoinObjectData();
//		    isNew = true;
//		}
//		TableObjectData rightTableObjectData = queryBlockObjectData.getTableObjectDataById(joinDef.getTRightStr());
//		tableJoinObjectData.setRightTable(rightTableObjectData);
//		TableObjectData leftTableObjectData = queryBlockObjectData.getTableObjectDataById(joinDef.getTLeftStr());
//		tableJoinObjectData.setLeftTable(leftTableObjectData);
//		tableJoinObjectData.setTableJoinType(TableJoinType.convertType(joinDef.getType()));
//		
//		if (isNew) {
//		    queryBlockObjectData.addTableJoinObjectData(tableJoinObjectData);
//		}
//	    }
	}	
    }
        
    public String getPlaceholderText() {
        String text = "";
        switch (getType()) {
            case ATTRIBUTE:
                text = ATTRIBUTE_PLACEHOLDER_HINT;
                break;
            case METRIC:
                text = METRIC_PLACEHOLDER_HINT;
                break;
            case TABLE:
                text = TABLE_PLACEHOLDER_HINT;
                break;
            case TABLE_JOIN:
                text = TABLE_JOIN_PLACEHOLDER_HINT;
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", getType());
        }
        
        return text;
    }
    
    private int computeDropIndex(double y) {
        double spacing = getSpacing();
        double topPadding = getPadding().getTop();        
        return (int) Math.round((y - topPadding) / (DEFAULT_LABEL_PANE_HEIGHT + spacing));
    }
    
    private double computePrefHeight(int size) {
        double spacing = getSpacing();
        double padding = getPadding().getTop() + getPadding().getBottom();
        
        return padding + (DEFAULT_LABEL_PANE_HEIGHT * size) + (spacing * (size - 1));
    }
    
    public void addDropZoneObjectPane(DropZoneObjectPane<? extends UIData> dropZoneObjectPane) {
	if (isEmpty) {
            getChildren().set(0, dropZoneObjectPane);
        }
        else if (currentDropIndex == -1) {
            // Not a drop event, add to the end
            double prefHeight = computePrefHeight(getChildren().size() + 1);
            setPrefHeight(prefHeight);            
            getChildren().add(dropZoneObjectPane);
        }
        else if (nondropCase) {
            double prefHeight = computePrefHeight(getChildren().size() + 1);
            setPrefHeight(prefHeight);            
            getChildren().add(currentDropIndex, dropZoneObjectPane);
            nondropCase = false;            
        }
        else {
            getChildren().set(currentDropIndex, dropZoneObjectPane);
        }
	
	if (dropZoneObjectPane.getData() instanceof PositionableObjectData) {
	    PositionableObjectData positionableObjectData = (PositionableObjectData) dropZoneObjectPane.getData();
	    // Update object pane position
	    if (isEmpty) {	        
	        // at the position 0
	        positionableObjectData.setPosition(0);
	    }
	    else if (currentDropIndex == -1) {
	        positionableObjectData.setPosition(getChildren().size() - 1);
	    }
	    else {
	        positionableObjectData.setPosition(currentDropIndex);
	        for (int i = currentDropIndex + 1; i < getChildren().size(); i++) {
	            ((PositionableObjectData)((DropZoneObjectPane<?>)getChildren().get(i)).getData()).setPosition(i);
	        }
	    }
	}
        
        isEmpty = false;
        currentDropIndex = -1;
    }    
    
    public void removeDropZoneObjectPane(DropZoneObjectPane<? extends UIData> dropZoneObjectPane) {
	removeDropZoneObjectPane(dropZoneObjectPane.getData().getId());
    }
    
    public void removeDropZoneObjectPane(String id) {
	DropZoneObjectPane<? extends UIData> selectedDropZoneObjectPane = null;
	boolean found = false;
	for (int i = 0; i < getChildren().size(); i++) {
	    @SuppressWarnings("unchecked")
            DropZoneObjectPane<? extends UIData> dropZoneObjectPane = (DropZoneObjectPane<? extends UIData>) getChildren().get(i);
	    
	    if (found) {
	        ((PositionableObjectData)dropZoneObjectPane.getData()).setPosition(i + 1);
	    }
	    else if (id.equalsIgnoreCase(dropZoneObjectPane.getData().getId())) {
		selectedDropZoneObjectPane = dropZoneObjectPane;
		if (dropZoneObjectPane.getData() instanceof PositionableObjectData) {
		    found = true;
		}
		else {
		    break;
		}
	    }
	}
	
	if (selectedDropZoneObjectPane != null) {	    
	    getChildren().remove(selectedDropZoneObjectPane);
	    double prefHeight = computePrefHeight(getChildren().size());
	    setPrefHeight(prefHeight);
	}
    }
    
    @Override
    public void onChanged(ListChangeListener.Change<? extends UIData> change) {        
        while (change.next()) {            
            if (change.wasAdded()) {
                UIData data = change.getAddedSubList().get(0);
                if (!contains(data.getId())) {
                    DropZoneObjectPane<? extends UIData> dropZoneObjectPane = LabelPaneFactory.createDropZoneObjectPane(reportPane, data); 
                    addDropZoneObjectPane(dropZoneObjectPane);
                    
                    if (dropZoneObjectPane.getType() == ATTRIBUTE || dropZoneObjectPane.getType() == METRIC) {
                        dropZoneObjectPane.setOnMouseClicked(this);
                    }
                }
            }
            else if (change.wasRemoved()) {
                UIData data = change.getRemoved().get(0);                
                removeDropZoneObjectPane(data.getId());                
            }
        }
    }
    
    private boolean contains(String id) {
        for (Node child : getChildren()) {
            if (child instanceof DropZoneObjectPane) {
                if (id.equalsIgnoreCase(((DropZoneObjectPane<?>)child).getData().getId())) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public void handle(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 1) {
            final PositionableObjectData data = (PositionableObjectData) ((DropZoneObjectPane<?>)e.getSource()).getData();
            
            ContextMenu contextMenu = new ContextMenu();
            
            if (data.getPosition() > 0) {
                MenuItem moveUpMenuItem = new MenuItem("Move Up");
                moveUpMenuItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        QueryBlockObjectData queryBlockObjectData = dropZonePane.getQueryBlockPane().getQueryBlockObjectData();
                        if (data.getType() == ATTRIBUTE) {
                            queryBlockObjectData.removeAttributeObjectData((AttributeObjectData) data);
                            currentDropIndex = data.getPosition() - 1;
                            nondropCase = true;
                            queryBlockObjectData.addAttributeObjectData((AttributeObjectData) data);
                        }
                        else if (data.getType() == METRIC) {
                            queryBlockObjectData.removeMetricObjectData((MetricObjectData) data);
                            currentDropIndex = data.getPosition() - 1;
                            nondropCase = true;
                            queryBlockObjectData.addMetricObjectData((MetricObjectData) data);
                        }
                        
                        updateQuery();
                    }
                    
                });
                
                contextMenu.getItems().add(moveUpMenuItem);
            }
            
            if (data.getPosition() < getChildren().size() - 1) {
                MenuItem moveDownMenuItem = new MenuItem("Move Down");
                moveDownMenuItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        QueryBlockObjectData queryBlockObjectData = dropZonePane.getQueryBlockPane().getQueryBlockObjectData();
                        if (data.getType() == ATTRIBUTE) {
                            queryBlockObjectData.removeAttributeObjectData((AttributeObjectData) data);
                            currentDropIndex = data.getPosition() + 1;
                            nondropCase = true;
                            queryBlockObjectData.addAttributeObjectData((AttributeObjectData) data);
                        }
                        else if (data.getType() == METRIC) {
                            queryBlockObjectData.removeMetricObjectData((MetricObjectData) data);
                            currentDropIndex = data.getPosition() + 1;
                            nondropCase = true;
                            queryBlockObjectData.addMetricObjectData((MetricObjectData) data);
                        }
                        
                        updateQuery();
                    }
                    
                });
                
                contextMenu.getItems().add(moveDownMenuItem);
            }
            
            contextMenu.show((Node) e.getSource(), Side.BOTTOM, 0, 0);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void updateQuery() {
        QueryBlockPane queryBlockPane = dropZonePane.getQueryBlockPane();
        QueryBlockObjectData queryBlockObjectData = queryBlockPane.getQueryBlockObjectData();
        
        // Generate SQL
        Report report = ServiceManager.getQueryEngineService().generateReportMetadata(queryBlockObjectData);
        Block block = report.getBlocks().get(0);
        // Show SQL in query block
        queryBlockPane.setSQLString(block.getSqlString());
        
        // Set up table alias
        Map<String, String> tableAliasMap = block.getTableMap();
        for (TableObjectData tableObjectData : queryBlockObjectData.getTableObjectDataList()) {
            String alias = tableAliasMap.get(tableObjectData.getId());              
            tableObjectData.setAlias(alias);
        }
        
        // Set up selected expression and table
        Map<String, String> attributeExpressionMap = block.getAttributeMap();
        Map<String, String> expressionTableMap = block.getExpressionMap();
        for (AttributeObjectData attributeObjectData : queryBlockObjectData.getAttributeObjectDataList()) {
            String expressionId = attributeExpressionMap.get(attributeObjectData.getId().toLowerCase());
            ExpressionObjectData selectedExpression = attributeObjectData.getExpressionObjectDataById(expressionId);
            attributeObjectData.setSelectedExpressionObjectData(selectedExpression);
            
            String tableId = expressionTableMap.get(expressionId);
            TableObjectData selectedTable = selectedExpression.getTableObjectDataById(tableId);
            selectedExpression.setSelectedTableObjectData(selectedTable);
        }
        
        Map<String, String> metricExpressionMap = block.getMetricMap();
        for (MetricObjectData metricObjectData : queryBlockObjectData.getMetricObjectDataList()) {
            String expressionId = metricExpressionMap.get(metricObjectData.getId().toLowerCase());
            ExpressionObjectData selectedExpression = metricObjectData.getExpressionObjectDataById(expressionId);
            metricObjectData.setSelectedExpressionObjectData(selectedExpression);
            
            String tableId = expressionTableMap.get(expressionId);
            TableObjectData selectedTable = selectedExpression.getTableObjectDataById(tableId);
            selectedExpression.setSelectedTableObjectData(selectedTable);
        }
        
        // Update table joins
        List<JoinDefinition> joinDefs = block.getJoinDefList();
        for (JoinDefinition joinDef : joinDefs) {
            boolean isNew = false;
            TableJoinObjectData tableJoinObjectData = queryBlockObjectData.getTableJoinObjectDataById(joinDef.getUUIDStr());
            if (tableJoinObjectData == null) {
                tableJoinObjectData = new TableJoinObjectData();
                isNew = true;
            }
            TableObjectData rightTableObjectData = queryBlockObjectData.getTableObjectDataById(joinDef.getTRightStr());
            tableJoinObjectData.setRightTable(rightTableObjectData);
            TableObjectData leftTableObjectData = queryBlockObjectData.getTableObjectDataById(joinDef.getTLeftStr());
            tableJoinObjectData.setLeftTable(leftTableObjectData);
            tableJoinObjectData.setTableJoinType(TableJoinType.convertType(joinDef.getType()));
            
            if (isNew) {
                queryBlockObjectData.addTableJoinObjectData(tableJoinObjectData);
            }
        }
    }
}
