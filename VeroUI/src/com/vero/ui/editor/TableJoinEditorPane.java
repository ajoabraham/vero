/**
 * 
 */
package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_CONTAINER_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SECTION_TITLE;
import static com.vero.ui.constants.UIConstants.OBJECT_CONTAINER_PANE_HEIGHT;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import com.vero.report.Block;
import com.vero.report.Report;
import com.vero.ui.common.ConfirmationDialogs;
import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.model.QueryBlockObjectData;
import com.vero.ui.model.TableJoinObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.report.querypane.QueryBlockPane;
import com.vero.ui.service.ServiceManager;
import com.vero.ui.util.ParserUtils;

import frmw.model.Join;
import frmw.model.fun.FunctionSpec;
import frmw.parser.Hints;

/**
 * @author Tai Hu
 * 
 */
public class TableJoinEditorPane extends EditorPane<TableJoinObjectData> implements ChangeListener<String> {
    private static final Logger logger = Logger.getLogger(TableJoinEditorPane.class.getName());

    private QueryBlockPane queryBlockPane = null;
    private QueryBlockObjectData queryBlockObjectData = null;
    private EditorTableLabelPane leftTablePane = null;
    private EditorTableLabelPane rightTablePane = null;
    private ImageView joinTypeImageView = null;
    private TextField formulaTextField = null;

    public TableJoinEditorPane(QueryBlockPane queryBlockPane, TableJoinObjectData data) {
	super(data);
	this.queryBlockPane = queryBlockPane;
	this.queryBlockObjectData = queryBlockPane.getQueryBlockObjectData();
	buildUI();
    }

    private void buildUI() {
	joinTypeImageView = new ImageView();
	joinTypeImageView.imageProperty().bind(new ObjectBinding<Image>() {
	    {
		super.bind(data.tableJoinTypeProperty());
	    }

	    @Override
	    protected Image computeValue() {
		return data.getTableJoinType().getImage();
	    }

	});
	joinTypeImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
		    data.setTableJoinType(data.getTableJoinType().next());
		}
	    }
	});

	leftTablePane = LabelPaneFactory.createEditorTablePane(data.getLeftTable(), true);
	leftTablePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
		if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
		    ContextMenu contextMenu = new ContextMenu();

		    for (TableObjectData tableObjectData : queryBlockObjectData.getTableObjectDataList()) {
			final MenuItem menuItem = new MenuItem(tableObjectData.getName() + " "
			        + tableObjectData.getAlias(), new ImageView(tableObjectData.getTableType().getImage()));
			menuItem.setUserData(tableObjectData);
			menuItem.setOnAction(new EventHandler<ActionEvent>() {

			    @Override
			    public void handle(ActionEvent evnet) {
				leftTablePane.setData((TableObjectData) menuItem.getUserData());
			    }

			});
			contextMenu.getItems().add(menuItem);
		    }

		    contextMenu.show((Node) event.getSource(), Side.BOTTOM, 0, 0);
		}
	    }
	});
	rightTablePane = LabelPaneFactory.createEditorTablePane(data.getRightTable(), true);
	rightTablePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
		if (event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
		    ContextMenu contextMenu = new ContextMenu();

		    for (TableObjectData tableObjectData : queryBlockObjectData.getTableObjectDataList()) {
			final MenuItem menuItem = new MenuItem(tableObjectData.getName() + " "
			        + tableObjectData.getAlias(), new ImageView(tableObjectData.getTableType().getImage()));
			menuItem.setUserData(tableObjectData);
			menuItem.setOnAction(new EventHandler<ActionEvent>() {

			    @Override
			    public void handle(ActionEvent evnet) {
				rightTablePane.setData((TableObjectData) menuItem.getUserData());
			    }

			});
			contextMenu.getItems().add(menuItem);
		    }

		    contextMenu.show((Node) event.getSource(), Side.BOTTOM, 0, 0);
		}
	    }
	});
	tableContainer.setPrefWidth(420);
	tableContainer.setSpacing(10);
	tableContainer.getChildren().addAll(leftTablePane, joinTypeImageView, rightTablePane);

	VBox contentPane = new VBox();
	contentPane.getStyleClass().add(CLASS_CONTENT_PANE);

	Label formulaLabel = new Label("JOIN DEFINITION");
	contentPane.getChildren().add(formulaLabel);
	formulaTextField = new TextField();
	formulaTextField.setPromptText("Enter a join definition");
	formulaTextField.textProperty().bindBidirectional(data.formulaProperty());
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
		    int caretPosition = formulaTextField.getCaretPosition() > formulaTextField.getText().length() ? formulaTextField
			    .getText().length() : formulaTextField.getCaretPosition();
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
	// Validate join expression
	try {
	    Join join = ParserUtils.parseJoin(formulaTextField.getText());
	    join.validate(ParserUtils.GENERIC_SQL);
	}
	catch (Exception e) {
	    logger.log(Level.INFO, e.getMessage(), e);
	    ConfirmationDialogs.createErrorConfirmation(null, e.getMessage()).show();
	}

	data.setLeftTable(leftTablePane.getData());
	data.setRightTable(rightTablePane.getData());

	Report report = ServiceManager.getQueryEngineService().generateReportMetadata(queryBlockObjectData);
	Block block = report.getBlocks().get(0);
	queryBlockPane.setSQLString(block.getSqlString());

	// Set up table alias
	Map<String, String> tableAliasMap = block.getTableMap();
	for (TableObjectData tableObjectData : queryBlockPane.getQueryBlockObjectData().getTableObjectDataList()) {
	    String alias = tableAliasMap.get(tableObjectData.getId());
	    tableObjectData.setAlias(alias);
	}
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	try {
	    Join join = ParserUtils.parseJoin(newValue);
	    join.validate(ParserUtils.GENERIC_SQL);
	    formulaTextField.setStyle(null);
	}
	catch (Exception e) {
	    formulaTextField.setStyle("-fx-text-fill: red;");
	}
    }
}
