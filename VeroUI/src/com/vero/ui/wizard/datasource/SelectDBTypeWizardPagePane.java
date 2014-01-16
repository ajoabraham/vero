/**
 * 
 */
package com.vero.ui.wizard.datasource;

import static com.vero.ui.constants.CSSConstants.*;
import static com.vero.ui.constants.WizardPageIds.ID_DB_PARAMS;
import static com.vero.ui.constants.WizardPageIds.ID_SELECT_DB_TYPE;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import com.vero.ui.common.LabelPane;
import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.constants.DBType;
import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardPagePane;

/**
 * @author Tai Hu
 * 
 */
public class SelectDBTypeWizardPagePane extends WizardPagePane<DatasourceWizardData> implements EventHandler<MouseEvent> {
    private static final String HIGHLIGHTED_STYLE = "-fx-border-color: -fx-button-hover-border-dark-color;"
	    + "-fx-border-radius: 3;";
    private static final String SELECTED_STYLE = "-fx-border-color: -fx-button-hover-border-dark-color;"
	    + "-fx-border-radius: 3;" + "-fx-border-size: 3";

    private DBTypeLabelPane selectedDBTypeLabelPane = null;

    public SelectDBTypeWizardPagePane(DatasourceWizardData wizardData) {
	super(wizardData);
	buildUI();
    }

    private void buildUI() {
	// Instruction
	Label instructionLabel = new Label("Select a database type");
	BorderPane.setAlignment(instructionLabel, Pos.CENTER);
	instructionLabel.setPrefHeight(100);
	instructionLabel.getStyleClass().add(CLASS_INSTRUCTION_TEXT);
	setTop(instructionLabel);

	VBox contentPane = new VBox();
	contentPane.getStyleClass().add(CLASS_DB_TYPE_CONTENT_PANE);

	for (DBType dbType : DBType.values()) {
	    LabelPane labelPane = LabelPaneFactory.getInstance().createDBTypeLabelPane(dbType);
	    labelPane.setOnMouseClicked(this);
	    labelPane.setOnMouseEntered(this);
	    labelPane.setOnMouseExited(this);
	    contentPane.getChildren().add(labelPane);
	}

	setCenter(contentPane);
    }

    @Override
    public String getWizardId() {
	return ID_SELECT_DB_TYPE;
    }

    @Override
    public String next() throws WizardException {
	if (selectedDBTypeLabelPane == null) {
	    throw new WizardException("Please select a database type.");
	}
	
	wizardData.getData().setDatabaseType(selectedDBTypeLabelPane.getType());
	
	return ID_DB_PARAMS;
    }

    @Override
    public String back() throws WizardException {
	return null;
    }

    @Override
    public void finish() throws WizardException {

    }

    @Override
    public void cancel() {
    }

    @Override
    public boolean isFirst() {
	return true;
    }

    @Override
    public boolean canFinish() {
	return false;
    }

    @Override
    public void handle(MouseEvent e) {
	if (selectedDBTypeLabelPane != e.getSource()) {
	    if (e.getEventType() == MouseEvent.MOUSE_CLICKED && e.getButton() == MouseButton.PRIMARY
		    && e.getClickCount() == 1) {
		handleMouseClickedEvent((DBTypeLabelPane) e.getSource());
	    }
	    else if (e.getEventType() == MouseEvent.MOUSE_ENTERED) {
		handleMouseEnteredEvent((DBTypeLabelPane) e.getSource());
	    }
	    else if (e.getEventType() == MouseEvent.MOUSE_EXITED) {
		handleMouseExitedEvent((DBTypeLabelPane) e.getSource());
	    }
	}
    }

    /**
     * @param source
     */
    private void handleMouseExitedEvent(DBTypeLabelPane source) {
	source.setStyle(null);
    }

    /**
     * @param source
     */
    private void handleMouseEnteredEvent(DBTypeLabelPane source) {
	source.setStyle(HIGHLIGHTED_STYLE);
    }

    /**
     * @param source
     */
    private void handleMouseClickedEvent(DBTypeLabelPane source) {
	source.setStyle(SELECTED_STYLE);

	if (selectedDBTypeLabelPane != null) {
	    selectedDBTypeLabelPane.setStyle(null);
	}

	selectedDBTypeLabelPane = source;
    }
}
