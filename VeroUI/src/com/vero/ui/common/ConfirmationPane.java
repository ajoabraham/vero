package com.vero.ui.common;

import static com.vero.ui.constants.ConfirmationType.OK;
import static com.vero.ui.constants.ConfirmationType.OK_CANCEL;
import static com.vero.ui.constants.ConfirmationType.YES_NO;
import static com.vero.ui.constants.ConfirmationType.YES_NO_CANCEL;
import static com.vero.ui.constants.CSSConstants.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.vero.ui.constants.ConfirmationType;
import com.vero.ui.util.UIUtils;

public class ConfirmationPane extends BorderPane implements EventHandler<ActionEvent> {
    private static final Logger logger = Logger.getLogger(ConfirmationPane.class.getName());
    
    private Stage ownerStage = null;
    private String message = null;
    private ConfirmationType confirmationType = OK;
    private ConfirmationHandler confirmationHandler = null;
    
    private Button okButton = null;
    private Button cancelButton = null;
    private Button yesButton = null;
    private Button noButton = null;
    
    public ConfirmationPane(Stage ownerStage, String title, String message, ConfirmationType confirmationType, ConfirmationHandler confirmationHandler) {	
	this.ownerStage = ownerStage;
	this.message = message;
	this.confirmationType = confirmationType;
	this.confirmationHandler = confirmationHandler;
	
	buildUI();
    }
    
    private void buildUI() {
	getStyleClass().add(CLASS_CONFIRMATION_PANE);
	Label messageLabel = new Label(message);
	messageLabel.getStyleClass().add(CLASS_SECTION_TITLE);
	BorderPane.setAlignment(messageLabel, Pos.CENTER);
	setCenter(messageLabel);
	
	HBox buttonBox = UIUtils.createDefaultButtonPane();
	
	if (confirmationType == OK || confirmationType == OK_CANCEL) {
	    okButton = UIUtils.createDefaultButton("Ok");
	    okButton.setOnAction(this);
	    
	    buttonBox.getChildren().add(okButton);
	}
	else if (confirmationType == YES_NO || confirmationType == YES_NO_CANCEL){
	    yesButton = UIUtils.createDefaultButton("Yes");
	    yesButton.setOnAction(this);
	    buttonBox.getChildren().add(yesButton);
	    
	    noButton = UIUtils.createDefaultButton("No");
	    noButton.setOnAction(this);
	    buttonBox.getChildren().add(noButton);
	}
	else {
	    logger.log(Level.SEVERE, "Invalid option: {0}", confirmationType);
	}
	
	if (confirmationType == OK_CANCEL || confirmationType == YES_NO_CANCEL) {
	    cancelButton = UIUtils.createDefaultButton("CANCEL");
	    cancelButton.setOnAction(this);
	    buttonBox.getChildren().add(cancelButton);
	}
	
	setBottom(buttonBox);
    }

    @Override
    public void handle(ActionEvent event) {
	if (event.getSource() == okButton) {
	    confirmationHandler.handleOkAction();
	}
	else if (event.getSource() == yesButton) {
	    confirmationHandler.handleYesAction();
	}
	else if (event.getSource() == noButton) {
	    confirmationHandler.handleNoAction();
	}
	else if (event.getSource() == cancelButton) {
	    confirmationHandler.handleCancelAction();
	}
	
	ownerStage.close();
    }
}
