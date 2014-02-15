package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.*;
import static com.vero.ui.constants.UIConstants.*;

import java.util.Set;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import com.vero.ui.common.ConfirmationDialogs;
import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.report.querypane.QueryBlockPane;
import com.vero.ui.service.ServiceManager;

import frmw.model.Formula;
import frmw.model.exception.ParsingException;
import frmw.parser.Parsing;

/**
 * @author Tai Hu
 *
 */
public class AttributeEditorPane extends EditorPane<AttributeObjectData> {
    private QueryBlockPane queryBlockPane = null;
    
    public AttributeEditorPane(QueryBlockPane queryBlockPane, AttributeObjectData data) {
        super(data);
        this.queryBlockPane = queryBlockPane;
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

    @Override
    protected void handleApplyAction() {
	try {
        	Formula f = new Parsing().parse(data.getSelectedExpressionObjectData().getFormula());
        	Set<String> entityNames = f.entityNames();
        	String sqlString = ServiceManager.getQueryEngineService().generateSQL(queryBlockPane.getQueryBlockObjectData());
        	queryBlockPane.setSQLString(sqlString);
	}
	catch (Exception e) {
	    ConfirmationDialogs.createErrorConfirmation(null, e.getMessage()).show();
	}
    }
}
