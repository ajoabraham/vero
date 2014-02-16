package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SECTION_TITLE;
import static com.vero.ui.constants.UIConstants.OBJECT_CONTAINER_PANE_HEIGHT;

import java.util.List;
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
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.ExpressionObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.report.querypane.QueryBlockPane;
import com.vero.ui.service.ServiceManager;
import com.vero.ui.util.ParserUtils;

import frmw.model.Formula;

/**
 * @author Tai Hu
 *
 */
public class AttributeEditorPane extends EditorPane<AttributeObjectData> {
    private QueryBlockPane queryBlockPane = null;
    private EditorTableLabelPane editorTableLabelPane = null;
    private TableObjectData originalTableObjectData = null;
    private String originalFormula = null;
    
    public AttributeEditorPane(QueryBlockPane queryBlockPane, AttributeObjectData data) {
        super(data);
        this.queryBlockPane = queryBlockPane;
        buildUI();
    }
    
    private void buildUI() {
        originalTableObjectData = data.getSelectedExpressionObjectData().getSelectedTableObjectData();
        editorTableLabelPane = LabelPaneFactory.createEditorTablePane(originalTableObjectData);
        tableContainer.getChildren().add(editorTableLabelPane);
        
        VBox contentPane = new VBox();
        contentPane.getStyleClass().add(CLASS_CONTENT_PANE);
        
        Label formulaLabel = new Label("FORMULA");
        contentPane.getChildren().add(formulaLabel);
        TextField formulaTextField = new TextField();
        formulaTextField.textProperty().bindBidirectional(data.getSelectedExpressionObjectData().formula());
        originalFormula = data.getSelectedExpressionObjectData().getFormula();
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
	    // Attribute/Metric Validation Rules
	    // 1. Validate the syntax of formula. If valid, go to next step, otherwise prompt user and end process.
	    // 2. Check if all columns in formula existed in selected table. If valid, go to next step, otherwise
	    //    prompt user and end process.
	    // 3. If formula is not changed, go to next step, otherwise go to step 4.
	    //    a. If selected table is also not changed, successfully end process. Otherwise go to next step.
	    //    b. If selected table changed, add newly selected table into current expression. Then add original 
	    //       table into hard hint black list. Successfully end process.
	    // 4. If formula is changed and original table is same as selected table. Update current expression's
	    //    formula and successfully end process. Otherwise go to next step.
	    // 5. If formula is changed and formula already exists in current attribute/metric, go to next step.
	    //    Otherwise, go to step 6.
	    //    a. If selected table is different from table in existing expression, add this new table into 
	    //       existing expression. Then set existing expression as selected expression for current 
	    //       attribute/metric and add this selected table into hard hint white list. Successfully end 
	    //       process.
	    //    b. If selected table is same as table in existing expression, then set existing expression
	    //       as selected expression and add this table into hard hint white list. Successfully end process.
	    // 6. If formula is changed and formula doesn't exist in current attribute/metric.
	    //    a. If selected table is already used in a different expression in the same attribute/metric,
	    //       prompt user and end process.
	    //    b. Create a new expression with selected table.
	    //    c. Set this new expression as selected and add selected table onto hard hint white list.
	    
	    Formula f = ParserUtils.parse(data.getSelectedExpressionObjectData().getFormula());
	    Set<String> entityNames = f.entityNames();

	    TableObjectData selectedTableObjectData = data.getSelectedExpressionObjectData().getSelectedTableObjectData();

	    if (!selectedTableObjectData.containsColumns(entityNames)) {
		throw new Exception("Not all columns exist in selected table.");
	    }

	    String formula = data.getSelectedExpressionObjectData().getFormula();

	    if (originalFormula.equals(formula)) {
		if (originalTableObjectData != selectedTableObjectData) {
		    List<ColumnObjectData> columns = selectedTableObjectData.getColumnsByNames(entityNames);
		    data.getSelectedExpressionObjectData().AddAllColumnObjectData(columns);
		    data.getSelectedExpressionObjectData().setSelectedTableObjectData(selectedTableObjectData);
		    // FIXME add original table into hard hint black list
		}
	    }
	    else if (originalTableObjectData != selectedTableObjectData) {
		ExpressionObjectData existingExpression = data.getExpressionByFormula(formula);
		if (existingExpression != null) {
		    if (existingExpression.containsTableObjectData(selectedTableObjectData)) {
			existingExpression.setSelectedTableObjectData(selectedTableObjectData);
			data.setSelectedExpressionObjectData(existingExpression);
		    }
		    else {
			List<ColumnObjectData> columns = selectedTableObjectData.getColumnsByNames(entityNames);
			existingExpression.AddAllColumnObjectData(columns);
			existingExpression.setSelectedTableObjectData(selectedTableObjectData);
			data.setSelectedExpressionObjectData(existingExpression);
			// FIXME add selected table into hard hint white list
		    }
		}
		else {
		    if (data.usedTableObjectData(selectedTableObjectData)) {
			throw new Exception("Selected table is already used in other expression under this attribute.");
		    }
		    
		    ExpressionObjectData expressionObjectData = new ExpressionObjectData();		
		    List<ColumnObjectData> columns = selectedTableObjectData.getColumnsByNames(entityNames);
		    expressionObjectData.AddAllColumnObjectData(columns);
		    data.addExpressionObjectData(expressionObjectData);
		    data.setSelectedExpressionObjectData(expressionObjectData);
		    // FIXME add selected table into hard hint white list.
		}
	    }

	    String sqlString = ServiceManager.getQueryEngineService().generateSQL(
		    queryBlockPane.getQueryBlockObjectData());
	    queryBlockPane.setSQLString(sqlString);
	}
	catch (Exception e) {
	    ConfirmationDialogs.createErrorConfirmation(null, e.getMessage()).show();
	}
    }
}
