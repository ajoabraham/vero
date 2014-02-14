package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_CONTAINER_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SECTION_TITLE;
import static com.vero.ui.constants.UIConstants.OBJECT_CONTAINER_PANE_HEIGHT;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.model.MetricObjectData;

/**
 * 
 * @author Tai Hu
 *
 */
public class MetricEditorPane extends EditorPane<MetricObjectData> {
    private TextField formulaTextField = null;
    
    public MetricEditorPane(MetricObjectData data) {
        super(data);
        buildUI();
    }
    
    private void buildUI() {        
        EditorTableLabelPane editorTableLabelPane = LabelPaneFactory.createEditorTablePane(data.getSelectedExpressionObjectData().getColumnObjectDataList().get(0).getTableObjectData());
        tableContainer.getChildren().add(editorTableLabelPane);
        
        VBox contentPane = new VBox();
        contentPane.getStyleClass().add(CLASS_CONTENT_PANE);
        
        Label formulaLabel = new Label("FORMULA");
        contentPane.getChildren().add(formulaLabel);
        formulaTextField = new TextField();
        formulaTextField.textProperty().bindBidirectional(data.getSelectedExpressionObjectData().formula());
        formulaTextField.setPrefHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        formulaTextField.setMaxHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        contentPane.getChildren().add(formulaTextField);
        
        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(2);
        VBox.setVgrow(tilePane, Priority.ALWAYS);
        contentPane.getChildren().add(tilePane);
        
        BorderPane hintPane = new BorderPane();
        Label hintLabel = new Label("HINTS/SUGGESTIONS");
        hintLabel.getStyleClass().add(CLASS_SECTION_TITLE);
        hintPane.setTop(hintLabel);
        tilePane.getChildren().add(hintPane);
        
        VBox parameterPane = new VBox();
        Label partitionByLabel = new Label("PARTITION BY");
        parameterPane.getChildren().add(partitionByLabel);
        HBox partitionByPane = new HBox();
        partitionByPane.getStyleClass().add(CLASS_OBJECT_CONTAINER_PANE);
        partitionByPane.setPrefHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        partitionByPane.setMaxHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        parameterPane.getChildren().add(partitionByPane);
        
        Label orderByLabel = new Label("ORDER BY");
        parameterPane.getChildren().add(orderByLabel);
        HBox orderByPane = new HBox();
        orderByPane.getStyleClass().add(CLASS_OBJECT_CONTAINER_PANE);
        orderByPane.setPrefHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        orderByPane.setMaxHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        parameterPane.getChildren().add(orderByPane);
        tilePane.getChildren().add(parameterPane);
        
        setCenter(contentPane);
    }
}