/**
 * 
 */
package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.*;
import static com.vero.ui.constants.UIConstants.OBJECT_CONTAINER_PANE_HEIGHT;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.model.AttributeObjectData;

/**
 * @author Tai Hu
 *
 */
public class AttributeEditorPane extends EditorPane<AttributeObjectData> {
    /**
     * @param data
     */
    public AttributeEditorPane(AttributeObjectData data) {
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
        TextField formulaTextField = new TextField();
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
        tilePane.getChildren().add(parameterPane);
        
        setCenter(contentPane);
    }
}
