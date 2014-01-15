/**
 * 
 */
package com.vero.ui.wizard.datasource;

import static com.vero.ui.constants.CSSConstants.CLASS_DB_PARAM_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_INSTRUCTION_TEXT;
import static com.vero.ui.constants.WizardPageIds.ID_DB_PARAMS;
import static com.vero.ui.constants.WizardPageIds.ID_SELECT_DB_TYPE;
import static com.vero.ui.constants.WizardPageIds.ID_SELECT_TABLES;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import com.vero.ui.util.UIUtils;
import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardPagePane;

/**
 * @author Tai Hu
 *
 */
public class DBParamsWizardPagePane extends WizardPagePane<DatasourceWizardData> {
    
    public DBParamsWizardPagePane(DatasourceWizardData wizardData) {
        super(wizardData);
        
        buildUI();
    }
    
    private void buildUI() {
        Label instructionLabel = new Label("Enter database connection parameters");
        BorderPane.setAlignment(instructionLabel, Pos.CENTER);
        instructionLabel.setPrefHeight(100);
        instructionLabel.getStyleClass().add(CLASS_INSTRUCTION_TEXT);
        setTop(instructionLabel);
        
        GridPane contentPane = new GridPane();
        contentPane.getStyleClass().add(CLASS_DB_PARAM_CONTENT_PANE);
        
        int rowIndex = 0;
        
        // Name
        Label nameLabel = UIUtils.createDefaultFormLabel("Name:");
        contentPane.add(nameLabel, 0, rowIndex);
        TextField nameTextField = UIUtils.createDefaultFormTextField();
        contentPane.add(nameTextField, 1, rowIndex++);
        nameTextField.textProperty().bindBidirectional(wizardData.getData().nameProperty());
    
        // User Name
        Label userNameLabel = UIUtils.createDefaultFormLabel("User Name:");
        contentPane.add(userNameLabel, 0, rowIndex);
        TextField userNameTextField = UIUtils.createDefaultFormTextField();
        contentPane.add(userNameTextField, 1, rowIndex++);
        userNameTextField.textProperty().bindBidirectional(wizardData.getData().userNameProperty());
        
        // Password
        Label passwordLabel = UIUtils.createDefaultFormLabel("Password:");
        contentPane.add(passwordLabel, 0, rowIndex);
        TextField passwordTextField = UIUtils.createDefaultFormTextField();
        contentPane.add(passwordTextField, 1, rowIndex++);
        passwordTextField.textProperty().bindBidirectional(wizardData.getData().passwordProperty());
        
        // Hostname
        Label hostnameLabel = UIUtils.createDefaultFormLabel("Hostname:");
        contentPane.add(hostnameLabel, 0, rowIndex);
        TextField hostnameTextField = UIUtils.createDefaultFormTextField();
        contentPane.add(hostnameTextField, 1, rowIndex++);
        hostnameTextField.textProperty().bindBidirectional(wizardData.getData().hostnameProperty());
        
        // Database name
        Label databaseNameLabel = UIUtils.createDefaultFormLabel("Database Name:");
        contentPane.add(databaseNameLabel, 0, rowIndex);
        TextField databaseNameTextField = UIUtils.createDefaultFormTextField();
        contentPane.add(databaseNameTextField, 1, rowIndex++);
        databaseNameTextField.textProperty().bindBidirectional(wizardData.getData().databaseNameProperty());
        
        // Test connection
        Button testConnectionButton = UIUtils.createDefaultButton("TEST CONNECTION");
        testConnectionButton.setPrefSize(150, 35);
        GridPane.setHalignment(testConnectionButton, HPos.RIGHT);
        contentPane.add(testConnectionButton, 1, rowIndex++);
        
        setCenter(contentPane);        
    }

    @Override
    public String getWizardId() {
        return ID_DB_PARAMS;
    }

    @Override
    public String next() throws WizardException {
        return ID_SELECT_TABLES;
    }

    @Override
    public String back() throws WizardException {
        return ID_SELECT_DB_TYPE;
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
        return false;
    }
}