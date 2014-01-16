/**
 * 
 */
package com.vero.ui.wizard.datasource;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardPagePane;

import static com.vero.ui.constants.CSSConstants.CLASS_INSTRUCTION_TEXT;
import static com.vero.ui.constants.WizardPageIds.*;

/**
 * @author Tai Hu
 *
 */
public class SelectTablesWizardPagePane extends WizardPagePane<DatasourceWizardData> {

    public SelectTablesWizardPagePane(DatasourceWizardData wizardData) {
        super(wizardData);
        buildUI();
    }
    
    private void buildUI() {
     // Instruction
        Label instructionLabel = new Label("Select import tables");
        BorderPane.setAlignment(instructionLabel, Pos.CENTER);
        instructionLabel.setPrefHeight(100);
        instructionLabel.getStyleClass().add(CLASS_INSTRUCTION_TEXT);
        setTop(instructionLabel);
    }

    @Override
    public String getWizardId() {
        return ID_SELECT_TABLES;
    }

    @Override
    public String next() throws WizardException {
        return null;
    }

    @Override
    public String back() throws WizardException {
        return ID_DB_PARAMS;
    }

    @Override
    public void finish() throws WizardException {        
    }

    @Override
    public void cancel() {        
    }

    @Override
    public boolean isFirst() {
        return false;
    }
    
    @Override
    public boolean canFinish() {
        return true;
    }

    /* (non-Javadoc)
     * @see com.vero.ui.wizard.WizardPagePane#init()
     */
    @Override
    public void init() throws WizardException {
        // TODO Auto-generated method stub
        
    }
}