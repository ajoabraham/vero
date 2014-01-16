/**
 * 
 */
package com.vero.ui.common;

import javafx.stage.Stage;

import com.vero.ui.constants.ConfirmationType;

/**
 * @author Tai Hu
 *
 */
public final class ConfirmationDialogBuilder {
    private Stage ownerStage = null;
    private String title = null;
    private String message = null;
    private ConfirmationType confirmationType;
    private ConfirmationHandler confirmationHandler;
    private double width;
    private double height;
    
    private ConfirmationDialogBuilder() {
	
    }
    
    public static ConfirmationDialogBuilder create() {
	return new ConfirmationDialogBuilder();
    }
    
    public ConfirmationDialogBuilder title(String title) {
	this.title = title;
	return this;
    }
    
    public ConfirmationDialogBuilder message(String message) {
	this.message = message;
	return this;
    }
    
    public ConfirmationDialogBuilder confirmationType(ConfirmationType confirmationType) {
	this.confirmationType = confirmationType;
	return this;
    }
    
    public ConfirmationDialogBuilder confirmationHandler(ConfirmationHandler confirmationHandler) {
	this.confirmationHandler = confirmationHandler;
	return this;
    }
    
    public ConfirmationDialogBuilder ownerStage(Stage ownerStage) {
	this.ownerStage = ownerStage;
	return this;
    }
    
    public ConfirmationDialogBuilder width(double width) {
	this.width = width;
	return this;
    }
    
    public ConfirmationDialogBuilder height(double height) {
	this.height = height;
	return this;
    }
    
    public PopupDialog build() {
	PopupDialog popupDialog = new PopupDialog(ownerStage);
	
	ConfirmationPane confirmationPane = new ConfirmationPane(popupDialog, title, message, confirmationType, confirmationHandler);
	popupDialog.createScene(confirmationPane, width, height);
	
	return popupDialog;
    }
}
