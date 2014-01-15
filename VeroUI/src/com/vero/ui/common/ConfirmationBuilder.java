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
public final class ConfirmationBuilder {
    private Stage ownerStage = null;
    private String title = null;
    private String message = null;
    private ConfirmationType confirmationType;
    private ConfirmationHandler confirmationHandler;
    private double width;
    private double height;
    
    private ConfirmationBuilder() {
	
    }
    
    public static ConfirmationBuilder create() {
	return new ConfirmationBuilder();
    }
    
    public ConfirmationBuilder title(String title) {
	this.title = title;
	return this;
    }
    
    public ConfirmationBuilder message(String message) {
	this.message = message;
	return this;
    }
    
    public ConfirmationBuilder confirmationType(ConfirmationType confirmationType) {
	this.confirmationType = confirmationType;
	return this;
    }
    
    public ConfirmationBuilder confirmationHandler(ConfirmationHandler confirmationHandler) {
	this.confirmationHandler = confirmationHandler;
	return this;
    }
    
    public ConfirmationBuilder ownerStage(Stage ownerStage) {
	this.ownerStage = ownerStage;
	return this;
    }
    
    public ConfirmationBuilder width(double width) {
	this.width = width;
	return this;
    }
    
    public ConfirmationBuilder height(double height) {
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
