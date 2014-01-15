/**
 * 
 */
package com.vero.ui.wizard;

import static com.vero.ui.constants.CSSConstants.CLASS_WIZARD_PANE;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.vero.ui.common.ConfirmationFactory;
import com.vero.ui.util.UIUtils;

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

    private ConfirmationFactory confirmationFactory = null;
    
    public WizardPane(Stage ownerStage, Map<String, WizardPagePane<T>> wizardPages, String firstPageId) {
	this.ownerStage = ownerStage;
	getStyleClass().add(CLASS_WIZARD_PANE);
	this.wizardPages = wizardPages;
	firstPage = wizardPages.get(firstPageId);
	currentPage = firstPage;
	confirmationFactory = ConfirmationFactory.getInstance();
	
	buildUI();
	showPage(firstPage);
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
	HBox buttonPane = UIUtils.createDefaultButtonPane();

	backButton = UIUtils.createDefaultButton("BACK");
	backButton.setOnAction(this);

	nextButton = UIUtils.createDefaultButton("NEXT");
	nextButton.setOnAction(this);

	finishButton = UIUtils.createDefaultButton("FINISH");
	finishButton.setOnAction(this);

	cancelButton = UIUtils.createDefaultButton("CANCEL");
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
	    confirmationFactory.createErrorConfirmation(ownerStage, e.getMessage()).show();
	}
    }

    private void handleNextEvent() {
	try {
	    String wizardId = currentPage.next();
	    WizardPagePane<T> nextPage = wizardPages.get(wizardId);
	    showPage(nextPage);
	}
	catch (WizardException e) {
	    confirmationFactory.createErrorConfirmation(ownerStage, e.getMessage()).show();
	}
    }

    private void handleFinishEvent() {
	try {
	    currentPage.finish();
	    ownerStage.close();
	}
	catch (WizardException e) {
	    confirmationFactory.createErrorConfirmation(ownerStage, e.getMessage()).show();
	}
    }

    private void handleCancelEvent() {
	ownerStage.close();
    }
}
