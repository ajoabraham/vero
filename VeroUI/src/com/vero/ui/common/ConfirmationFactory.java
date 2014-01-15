/**
 * 
 */
package com.vero.ui.common;

import static com.vero.ui.constants.ConfirmationType.*;
import static com.vero.ui.constants.UIConstants.*;

import javafx.stage.Stage;

/**
 * @author Tai Hu
 *
 */
public final class ConfirmationFactory {
    private static ConfirmationFactory INSTANCE = null;
    
    private ConfirmationFactory() {
	
    }
    
    public static ConfirmationFactory getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new ConfirmationFactory();
	}
	
	return INSTANCE;
    }
    
    public PopupDialog createErrorConfirmation(Stage ownerStage, String errorMessage) {
	return ConfirmationBuilder.create()
		                  .ownerStage(ownerStage)
		                  .title("Error")
		                  .message(errorMessage)
		                  .confirmationType(OK)
		                  .confirmationHandler(new ConfirmationHandlerAdapter())
		                  .width(DEFAULT_CONFIRMATION_WIDTH)
		                  .height(DEFAULT_CONFIRMATION_HEIGHT)
		                  .build();
		                  
    }
}
