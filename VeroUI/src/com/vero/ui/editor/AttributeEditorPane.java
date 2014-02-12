/**
 * 
 */
package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_CONTENT_PANE;
import static com.vero.ui.constants.UIConstants.OBJECT_CONTAINER_PANE_HEIGHT;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        
        setCenter(contentPane);
    }
}
