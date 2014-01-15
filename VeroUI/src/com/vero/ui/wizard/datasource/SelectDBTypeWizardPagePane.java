/**
 * 
 */
package com.vero.ui.wizard.datasource;

import static com.vero.ui.constants.CSSConstants.CLASS_SECTION_TITLE;
import static com.vero.ui.constants.WizardPageIds.ID_DB_PARAMS;
import static com.vero.ui.constants.WizardPageIds.ID_SELECT_DB_TYPE;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
public class SelectDBTypeWizardPagePane extends WizardPagePane<DatasourceWizardData> {

    public SelectDBTypeWizardPagePane(DatasourceWizardData wizardData) {
        super(wizardData);
        buildUI();
    }
    
    private void buildUI() {	
	// Instruction
	Label instructionLabel = new Label("Please select a database type");
	BorderPane.setAlignment(instructionLabel, Pos.CENTER);
	instructionLabel.setPrefHeight(300);
	instructionLabel.getStyleClass().add(CLASS_SECTION_TITLE);
	setTop(instructionLabel);
	
	VBox dbTypesPane = new VBox();
	
	for (DBType dbType : DBType.values()) {
	    LabelPane labelPane = LabelPaneFactory.getInstance().createDBTypeLabelPane(dbType);
	    dbTypesPane.getChildren().add(labelPane);
	}
	
	setCenter(dbTypesPane);
    }

    @Override
    public String getWizardId() {
        return ID_SELECT_DB_TYPE;
    }

    @Override
    public String next() throws WizardException {
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

}
