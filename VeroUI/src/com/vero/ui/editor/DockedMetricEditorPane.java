package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_CONTAINER_PANE;
import static com.vero.ui.constants.ObjectType.METRIC;
import static com.vero.ui.constants.UIConstants.OBJECT_CONTAINER_PANE_HEIGHT;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.vero.ui.constants.ObjectType;

/**
 * 
 * @author Tai Hu
 *
 */
public class DockedMetricEditorPane extends DockedEditorPane {
    private TextField formulaTextField = null;
    
    public DockedMetricEditorPane() {
        super();
        buildUI();
    }
    
    private void buildUI() {
        VBox contentPane = new VBox();
        contentPane.getStyleClass().add(CLASS_CONTENT_PANE);
        
        Label formulaLabel = new Label("FORMULA");
        contentPane.getChildren().add(formulaLabel);
        formulaTextField = new TextField();
        formulaTextField.setPrefHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        formulaTextField.setMaxHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        contentPane.getChildren().add(formulaTextField);
        
        Label partitionByLabel = new Label("PARTITION BY");
        contentPane.getChildren().add(partitionByLabel);
        HBox partitionByPane = new HBox();
        partitionByPane.getStyleClass().add(CLASS_OBJECT_CONTAINER_PANE);
        partitionByPane.setPrefHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        partitionByPane.setMaxHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        contentPane.getChildren().add(partitionByPane);
        
//        AttributeObjectData data = new AttributeObjectData();
//        data.setName("Test Attribute");
//        ObjectPane attributeObjectPane = LabelPaneFactory.getInstance().createObjectPane(ATTRIBUTE, data, false);
//        partitionByPane.getChildren().add(attributeObjectPane);
        
        
        Label orderByLabel = new Label("ORDER BY");
        contentPane.getChildren().add(orderByLabel);
        HBox orderByPane = new HBox();
        orderByPane.getStyleClass().add(CLASS_OBJECT_CONTAINER_PANE);
        orderByPane.setPrefHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        orderByPane.setMaxHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        contentPane.getChildren().add(orderByPane);
        
        setCenter(contentPane);
    }

    @Override
    public ObjectType getType() {
        return METRIC;
    }
}
