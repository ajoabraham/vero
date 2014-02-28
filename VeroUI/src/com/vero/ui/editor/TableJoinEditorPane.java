/**
 * 
 */
package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_CONTAINER_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SECTION_TITLE;
import static com.vero.ui.constants.UIConstants.OBJECT_CONTAINER_PANE_HEIGHT;

import java.util.logging.Logger;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
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

import com.vero.ui.model.TableJoinObjectData;
import com.vero.ui.report.querypane.QueryBlockPane;
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
    private String originalFormula = null;
    private ImageView joinTypeImageView = null;
    private TextField formulaTextField = null;

    public TableJoinEditorPane(QueryBlockPane queryBlockPane, TableJoinObjectData data) {
        super(data);
        this.queryBlockPane = queryBlockPane;
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
        tableContainer.getChildren().add(joinTypeImageView);

        VBox contentPane = new VBox();
        contentPane.getStyleClass().add(CLASS_CONTENT_PANE);

        Label formulaLabel = new Label("JOIN DEFINITION");
        contentPane.getChildren().add(formulaLabel);
        formulaTextField = new TextField();
        formulaTextField.setPromptText("Enter a join definition");
        formulaTextField.textProperty().bindBidirectional(data.formulaProperty());
        originalFormula = data.getFormula();
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
            	int caretPosition = formulaTextField.getCaretPosition() > formulaTextField.getText().length() ? 
            		formulaTextField.getText().length() : formulaTextField.getCaretPosition();
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

    @Override
    protected void handleApplyAction() {
        
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
