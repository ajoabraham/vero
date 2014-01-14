/**
 * 
 */
package com.vero.ui.wizard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * @author Tai Hu
 *
 */
public class WizardPane extends BorderPane implements EventHandler<ActionEvent> {
    private Button backButton = null;
    private Button nextButton = null;
    private Button finishButton = null;
    private Button cancelButton = null;
    
    public WizardPane() {
	
    }

    @Override
    public void handle(ActionEvent e) {	
    }
}
