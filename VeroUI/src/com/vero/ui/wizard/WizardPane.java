/**
 * 
 */
package com.vero.ui.wizard;

import static com.vero.ui.constants.CSSConstants.CLASS_WIZARD_PANE;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Tai Hu
 *
 */
public class WizardPane<T extends WizardData> extends BorderPane implements EventHandler<ActionEvent> {
    private Stage ownerStage = null;
    
    private Button backButton = null;
    private Button nextButton = null;
    private Button finishButton = null;
    private Button cancelButton = null;
    
    private Map<String, WizardPagePane<T>> wizardPages = null;
    private WizardPagePane<T> firstPage = null;
    private WizardPagePane<T> currentPage = null;
    
    public WizardPane(Stage ownerStage, Map<String, WizardPagePane<T>> wizardPages, String firstPageId) {
	this.ownerStage = ownerStage;
	getStyleClass().add(CLASS_WIZARD_PANE);
	this.wizardPages = wizardPages;
	firstPage = wizardPages.get(firstPageId);
	currentPage = firstPage;
	
	buildUI();
    }
    
    private void buildUI() {
        setCenter(getWizardPageContainerPane());
        setBottom(getButtonPane());
    }
    
    private Pane getWizardPageContainerPane() {
        StackPane container = new StackPane();
        container.getChildren().setAll(wizardPages.values());        
        
        return container;
    }
    
    private Pane getButtonPane() {
        HBox buttonPane = new HBox();
        buttonPane.setAlignment(Pos.CENTER_RIGHT);
        
        backButton = new Button("BACK");
        backButton.setOnAction(this);
        
        nextButton = new Button("NEXT");
        nextButton.setOnAction(this);
        
        finishButton = new Button("FINISH");
        finishButton.setOnAction(this);
        
        cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(this);
        
        buttonPane.getChildren().setAll(backButton, nextButton, finishButton, cancelButton);
        
        return buttonPane;
    }
    
    private void showPage(WizardPagePane<T> wizardPage) {
        backButton.setDisable(wizardPage.isFirst());
        nextButton.setDisable(wizardPage.canFinish());
        finishButton.setDisable(!wizardPage.canFinish());
        
        currentPage = wizardPage;
        currentPage.toFront();
    }

    @Override
    public void handle(ActionEvent e) {
        if (e.getSource() == backButton) {
            handleBackEvent();
        }
        else if (e.getSource() == nextButton) {
            handleNextEvent();
        }
        else if (e.getSource() == finishButton) {
            handleFinishEvent();
        }
        else if (e.getSource() == cancelButton) {
            handleCancelEvent();
        }
    }
    
    private void handleBackEvent() {
        try {
            String wizardId = currentPage.back();
            WizardPagePane<T> previousPage = wizardPages.get(wizardId);
            showPage(previousPage);
        }
        catch (WizardException e) {
            // TODO Show error popup
        }
    }
    
    private void handleNextEvent() {
        try {
            String wizardId = currentPage.next();
            WizardPagePane<T> nextPage = wizardPages.get(wizardId);
            showPage(nextPage);
        }
        catch (WizardException e) {
            // TODO show error popup
        }
    }
    
    private void handleFinishEvent() {
        try {
            currentPage.finish();
            ownerStage.close();
        }
        catch (WizardException e) {
            // TODO Show error popup
        }
    }
    
    private void handleCancelEvent() {
        ownerStage.close();
    }
}
