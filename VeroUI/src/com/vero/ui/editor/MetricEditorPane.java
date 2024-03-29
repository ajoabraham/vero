package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_CONTAINER_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SECTION_TITLE;
import static com.vero.ui.constants.UIConstants.OBJECT_CONTAINER_PANE_HEIGHT;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import com.vero.metadata.JoinDefinition;
import com.vero.report.Block;
import com.vero.report.Report;
import com.vero.ui.common.ConfirmationDialogs;
import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.constants.TableJoinType;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.ExpressionObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.QueryBlockObjectData;
import com.vero.ui.model.TableJoinObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.report.querypane.QueryBlockPane;
import com.vero.ui.service.MetadataPersistentService;
import com.vero.ui.service.ServiceManager;
import com.vero.ui.util.ParserUtils;

import frmw.model.Formula;
import frmw.model.fun.FunctionSpec;
import frmw.parser.Hints;

/**
 * 
 * @author Tai Hu
 * 
 */
public class MetricEditorPane extends EditorPane<MetricObjectData> implements ChangeListener<String> {
    private static final Logger logger = Logger.getLogger(MetricEditorPane.class.getName());

    private QueryBlockPane queryBlockPane = null;
    private QueryBlockObjectData queryBlockObjectData = null;
    private EditorTableLabelPane editorTableLabelPane = null;
    private TableObjectData originalTableObjectData = null;
    private String originalFormula = null;
    private TextField formulaTextField = null;

    public MetricEditorPane(QueryBlockPane queryBlockPane, MetricObjectData data) {
        super(data);
        this.queryBlockPane = queryBlockPane;
        this.queryBlockObjectData = queryBlockPane.getQueryBlockObjectData();
        buildUI();
    }

    private void buildUI() {
        originalTableObjectData = data.getSelectedExpressionObjectData().getSelectedTableObjectData();
        editorTableLabelPane = LabelPaneFactory.createEditorTablePane(originalTableObjectData);
        editorTableLabelPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
                    try {
                        Formula f = ParserUtils.parse(formulaTextField.getText());
                        MetadataPersistentService service = ServiceManager.getMetadataPersistentService();
                        TableObjectData selectedTableObjectData = editorTableLabelPane.getData();
                        List<TableObjectData> tableObjectDataList = service.findTableObjectDataListByColumnNames(selectedTableObjectData
                                .getDatasourceObjectData().getId(), f.entityNames());

                        ContextMenu contextMenu = new ContextMenu();

                        for (TableObjectData tableObjectData : tableObjectDataList) {
                            MenuItem menuItem = new MenuItem(tableObjectData.getName(), new ImageView(tableObjectData.getTableType().getImage()));
                            menuItem.setUserData(tableObjectData);
                            menuItem.setOnAction(MetricEditorPane.this);
                            contextMenu.getItems().add(menuItem);
                        }

                        contextMenu.show((Node) event.getSource(), Side.BOTTOM, 0, 0);
                    }
                    catch (Exception e) {
                        // Invalid formula
                    }
                }
            }
        });
        tableContainer.getChildren().add(editorTableLabelPane);

        VBox contentPane = new VBox();
        contentPane.getStyleClass().add(CLASS_CONTENT_PANE);

        Label formulaLabel = new Label("FORMULA");
        contentPane.getChildren().add(formulaLabel);
        formulaTextField = new TextField();
        formulaTextField.setPromptText("Enter an expression");
        formulaTextField.textProperty().bindBidirectional(data.getSelectedExpressionObjectData().formula());
        originalFormula = data.getSelectedExpressionObjectData().getFormula();
        formulaTextField.setPrefHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        formulaTextField.setMaxHeight(OBJECT_CONTAINER_PANE_HEIGHT);
        formulaTextField.textProperty().addListener(this);

        contentPane.getChildren().add(formulaTextField);

        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(2);
        VBox.setVgrow(tilePane, Priority.ALWAYS);
        contentPane.getChildren().add(tilePane);

        BorderPane hintPane = new BorderPane();
        hintPane.prefWidthProperty().bind(tilePane.widthProperty().divide(2.5));
        Label hintTitleLabel = new Label("HINTS/SUGGESTIONS");
        hintTitleLabel.getStyleClass().add(CLASS_SECTION_TITLE);
        hintPane.setTop(hintTitleLabel);
        Label hintLabel = new Label();
        hintLabel.textProperty().bind(new StringBinding() {
            {
                super.bind(formulaTextField.textProperty());
            }

            protected String computeValue() {
                StringBuffer hintText = new StringBuffer();
                if (formulaTextField.getText() != null) {
                    int caretPosition = formulaTextField.getCaretPosition() > formulaTextField.getText().length() ? formulaTextField.getText().length()
                            : formulaTextField.getCaretPosition();
                    Hints hints = Hints.select(formulaTextField.getText(), caretPosition, ParserUtils.PARSER);
                    for (FunctionSpec functionSpec : hints.functions()) {
                        hintText.append(functionSpec.name()).append("\n");
                    }
                }

                return hintText.toString();
            }

        });
        hintPane.setCenter(hintLabel);
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

    @SuppressWarnings("unchecked")
    @Override
    protected void handleApplyAction() {
        try {
            // Attribute/Metric Validation Rules
            // 1. Validate the syntax of formula. If valid, go to next step,
            // otherwise prompt user and end process.
            // 2. Check if all columns in formula existed in selected table. If
            // valid, go to next step, otherwise
            // prompt user and end process.
            // 3. If formula is not changed, go to next step, otherwise go to
            // step 4.
            // a. If selected table is also not changed, successfully end
            // process. Otherwise go to next step.
            // b. If selected table changed, add newly selected table into
            // current expression. Then add original
            // table into hard hint black list. Successfully end process.
            // 4. If formula is changed and original table is same as selected
            // table. Update current expression's
            // formula and successfully end process. Otherwise go to next step.
            // 5. If formula is changed and formula already exists in current
            // attribute/metric, go to next step.
            // Otherwise, go to step 6.
            // a. If selected table is different from table in existing
            // expression, add this new table into
            // existing expression. Then set existing expression as selected
            // expression for current
            // attribute/metric and add this selected table into hard hint white
            // list. Successfully end
            // process.
            // b. If selected table is same as table in existing expression,
            // then set existing expression
            // as selected expression and add this table into hard hint white
            // list. Successfully end process.
            // 6. If formula is changed and formula doesn't exist in current
            // attribute/metric.
            // a. If selected table is already used in a different expression in
            // the same attribute/metric,
            // prompt user and end process.
            // b. Create a new expression with selected table.
            // c. Set this new expression as selected and add selected table
            // onto hard hint white list.

            logger.finest("Start apply attribute editing...");
            Formula f = ParserUtils.parse(data.getSelectedExpressionObjectData().getFormula());
            Set<String> entityNames = f.entityNames();

            TableObjectData selectedTableObjectData = editorTableLabelPane.getData();
            logger.finest("Selected table - " + selectedTableObjectData.getName() + "(" + selectedTableObjectData.getId() + ")");

            if (!selectedTableObjectData.containsColumns(entityNames)) {
                throw new Exception("Not all columns exist in selected table.");
            }

            String formula = data.getSelectedExpressionObjectData().getFormula();

            if (originalFormula.equals(formula)) {
                logger.finest("Formula is not changed - " + formula);
                if (!originalTableObjectData.equals(selectedTableObjectData)) {
                    logger.finest("Original table and selected table are different.");
                    List<ColumnObjectData> columns = selectedTableObjectData.getColumnsByNames(entityNames);
                    logger.finest("Columns used in formula - " + columns.size());
                    data.getSelectedExpressionObjectData().addAllColumnObjectData(columns);
                    data.getSelectedExpressionObjectData().setSelectedTableObjectData(selectedTableObjectData);
                    
                    data.addWhiteHardHint(selectedTableObjectData);
                    
                    data.addWhiteHardHint(selectedTableObjectData);
                    queryBlockObjectData.addTableObjectData(data, selectedTableObjectData);
                    queryBlockObjectData.removeTableObjectData(data, originalTableObjectData);
                }
            }
            else if (originalTableObjectData != selectedTableObjectData) {
                logger.finest("Original formula is different from current formula - " + originalFormula + " " + formula);
                ExpressionObjectData existingExpression = data.getExpressionByFormula(formula);
                if (existingExpression != null) {
                    logger.finest("There is an existing expression with same formula - " + existingExpression.getId());
                    if (existingExpression.containsTableObjectData(selectedTableObjectData)) {
                        logger.finest("Existing formula has the same table as selected table - " + selectedTableObjectData.getName());
                        existingExpression.setSelectedTableObjectData(selectedTableObjectData);
                        data.setSelectedExpressionObjectData(existingExpression);
                    }
                    else {
                        logger.finest("Existing expression has different table.");
                        List<ColumnObjectData> columns = selectedTableObjectData.getColumnsByNames(entityNames);
                        existingExpression.addAllColumnObjectData(columns);
                        existingExpression.setSelectedTableObjectData(selectedTableObjectData);
                        data.setSelectedExpressionObjectData(existingExpression);
                    }
                    
                    data.addWhiteHardHint(selectedTableObjectData);
                    
                    queryBlockObjectData.addTableObjectData(data, selectedTableObjectData);
                    queryBlockObjectData.removeTableObjectData(data, originalTableObjectData);
                }
                else {
                    logger.finest("There is no existing expression contains same formula.");
                    if (data.usedTableObjectData(selectedTableObjectData)) {
                        throw new Exception("Selected table is already used in other expression under this attribute.");
                    }

                    ExpressionObjectData expressionObjectData = new ExpressionObjectData();
                    List<ColumnObjectData> columns = selectedTableObjectData.getColumnsByNames(entityNames);
                    expressionObjectData.addAllColumnObjectData(columns);
                    data.addExpressionObjectData(expressionObjectData);
                    data.setSelectedExpressionObjectData(expressionObjectData);

                    data.addWhiteHardHint(selectedTableObjectData);
                    
                    queryBlockObjectData.addTableObjectData(data, selectedTableObjectData);
                    queryBlockObjectData.removeTableObjectData(data, originalTableObjectData);
                }
            }

            Report report = ServiceManager.getQueryEngineService().generateReportMetadata(queryBlockObjectData);
            Block block = report.getBlocks().get(0);
            queryBlockPane.setSQLString(block.getSqlString());
            
	    // Set up table alias
            Map<String, String> tableAliasMap = block.getTableMap();
	    for (TableObjectData tableObjectData : queryBlockObjectData.getTableObjectDataList()) {
	        String alias = tableAliasMap.get(tableObjectData.getId());	        
	        tableObjectData.setAlias(alias);
	    }
	    
            // Update table join
            List<JoinDefinition> joinDefs = block.getJoinDefList();
            for (JoinDefinition joinDef : joinDefs) {
                TableJoinObjectData tableJoinObjectData = queryBlockObjectData.getTableJoinObjectDataById(joinDef.getUUIDStr());
                if (tableJoinObjectData == null) {
                    tableJoinObjectData = new TableJoinObjectData();
                    TableObjectData rightTableObjectData = queryBlockObjectData.getTableObjectDataById(joinDef.getTRightStr());
                    tableJoinObjectData.setRightTable(rightTableObjectData);
                    TableObjectData leftTableObjectData = queryBlockObjectData.getTableObjectDataById(joinDef.getTLeftStr());
                    tableJoinObjectData.setLeftTable(leftTableObjectData);
                    tableJoinObjectData.setTableJoinType(TableJoinType.convertType(joinDef.getType()));

                    queryBlockObjectData.addTableJoinObjectData(tableJoinObjectData);
                }
                else {                    
                    tableJoinObjectData.setTableJoinType(TableJoinType.convertType(joinDef.getType()));                    
                }
            }
                
	    setVisible(false);
        }
        catch (Exception e) {
            logger.log(Level.INFO, e.getMessage(), e);
            ConfirmationDialogs.createErrorConfirmation(null, e.getMessage()).show();
        }
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            ParserUtils.parse(newValue);
            formulaTextField.setStyle(null);
        }
        catch (Exception e) {
            formulaTextField.setStyle("-fx-text-fill: red;");
        }
    }

    @Override
    protected void handleChangeTableEvent(TableObjectData data) {
        editorTableLabelPane.setData(data);
    }
}