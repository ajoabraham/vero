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
public final class ConfirmationDialogs {
    private ConfirmationDialogs() {
	
    }
    
    public static PopupDialog createErrorConfirmation(Stage ownerStage, String errorMessage) {
	return ConfirmationDialogBuilder.create()
		                  .ownerStage(ownerStage)
		                  .title("Error")
		                  .message(errorMessage)
		                  .confirmationType(OK)
		                  .confirmationHandler(new ConfirmationHandlerAdapter())
		                  .width(DEFAULT_CONFIRMATION_WIDTH)
		                  .height(DEFAULT_CONFIRMATION_HEIGHT)
		                  .build();
		                  
    }
    
    public static PopupDialog createInfoConfirmation(Stage ownerStage, String infoMessage) {
	return ConfirmationDialogBuilder.create()
		                  .ownerStage(ownerStage)
		                  .title("Info")
		                  .message(infoMessage)
		                  .confirmationType(OK)
		                  .confirmationHandler(new ConfirmationHandlerAdapter())
		                  .width(DEFAULT_CONFIRMATION_WIDTH)
		                  .height(DEFAULT_CONFIRMATION_HEIGHT)
		                  .build();
		                  
    }
}
