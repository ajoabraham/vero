/**
 * 
 */
package com.vero.ui.wizard.datasource;

import static com.vero.ui.constants.CSSConstants.CLASS_DB_PARAM_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_INSTRUCTION_TEXT;
import static com.vero.ui.constants.UIConstants.DEFAULT_FORM_INPUT_HEIGHT;
import static com.vero.ui.constants.WizardPageIds.ID_DB_PARAMS;
import static com.vero.ui.constants.WizardPageIds.ID_SELECT_DB_TYPE;
import static com.vero.ui.constants.WizardPageIds.ID_SELECT_TABLES;
import static com.vero.ui.constants.DBType.*;

import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javax.validation.ConstraintViolation;

import com.vero.ui.common.ConfirmationDialogs;
import com.vero.ui.constants.DBType;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.service.DatasourceImportService;
import com.vero.ui.service.ServiceException;
import com.vero.ui.service.ServiceManager;
import com.vero.ui.util.UIUtils;
import com.vero.ui.util.ValidationUtils;
import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardPagePane;

/**
 * @author Tai Hu
 * 
 */
public class DBParamsWizardPagePane extends WizardPagePane<DatasourceWizardData> implements EventHandler<ActionEvent> {
    private Button getDatabaseNamesButton = null;
    private Button testConnectionButton = null;
    
    private ComboBox<String> databaseNameComboBox = null;
    
    private DBType currentDBType = null;
    
    public DBParamsWizardPagePane(DatasourceWizardData wizardData) {
        super(wizardData);
    }
    
    @Override
    public void init() throws WizardException {
        if (currentDBType != wizardData.getData().getDatabaseType()) {
            getChildren().clear();
            buildUI();
        }       
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
        contentPane.add(nameTextField, 1, rowIndex++, 2, 1);
        nameTextField.textProperty().bindBidirectional(wizardData.getData().nameProperty());

        // User Name
        Label userNameLabel = UIUtils.createDefaultFormLabel("User Name:");
        contentPane.add(userNameLabel, 0, rowIndex);
        TextField userNameTextField = UIUtils.createDefaultFormTextField();
        contentPane.add(userNameTextField, 1, rowIndex++, 2, 1);
        userNameTextField.textProperty().bindBidirectional(wizardData.getData().userNameProperty());

        // Password
        Label passwordLabel = UIUtils.createDefaultFormLabel("Password:");
        contentPane.add(passwordLabel, 0, rowIndex);
        TextField passwordTextField = UIUtils.createDefaultFormPasswordField();
        contentPane.add(passwordTextField, 1, rowIndex++, 2, 1);
        passwordTextField.textProperty().bindBidirectional(wizardData.getData().passwordProperty());

        // Hostname
        Label hostnameLabel = UIUtils.createDefaultFormLabel("Hostname:");
        contentPane.add(hostnameLabel, 0, rowIndex);
        TextField hostnameTextField = UIUtils.createDefaultFormTextField();
        contentPane.add(hostnameTextField, 1, rowIndex++, 2, 1);
        hostnameTextField.textProperty().bindBidirectional(wizardData.getData().hostnameProperty());

        // Database name
        Label databaseNameLabel = UIUtils.createDefaultFormLabel("Database Name:");
        contentPane.add(databaseNameLabel, 0, rowIndex);
       
        if (wizardData.getData().getDatabaseType() == POSTGRE_SQL) {
            TextField databaseNameTextField = UIUtils.createDefaultFormTextField();
            contentPane.add(databaseNameTextField, 1, rowIndex++, 2, 1);
            databaseNameTextField.textProperty().bindBidirectional(wizardData.getData().databaseNameProperty());
        }
        else {
            databaseNameComboBox = new ComboBox<String>();
            databaseNameComboBox.setPrefSize(225, DEFAULT_FORM_INPUT_HEIGHT);
            getDatabaseNamesButton = UIUtils.createDefaultButton("GET DB NAMES");
            getDatabaseNamesButton.setPrefSize(120, DEFAULT_FORM_INPUT_HEIGHT);
            contentPane.add(databaseNameComboBox, 1, rowIndex);
            contentPane.add(getDatabaseNamesButton, 2, rowIndex++);
            getDatabaseNamesButton.setOnAction(this);
        }

        // Test connection
        testConnectionButton = UIUtils.createDefaultButton("TEST CONNECTION");
        testConnectionButton.setPrefSize(150, 35);
        GridPane.setHalignment(testConnectionButton, HPos.RIGHT);
        contentPane.add(testConnectionButton, 1, rowIndex++);
        testConnectionButton.setOnAction(this);

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

    @Override
    public void handle(ActionEvent e) {
	if (e.getSource() == getDatabaseNamesButton) {
	    handleGetDatabaseNamesEvent();
	}
	else if (e.getSource() == testConnectionButton) {
	    handleTestConnectionEvent();
	}
    }

    private void handleTestConnectionEvent() {
        // Validate
        Set<ConstraintViolation<DatasourceObjectData>> violations = ValidationUtils.validate(wizardData.getData(), "userName", "password", "hostname",
                "databaseName");

        if (violations.isEmpty()) {
            DatasourceImportService service = ServiceManager.getDatasourceImportService();

            try {
                if (service.testConnection(wizardData.getData())) {
                    ConfirmationDialogs.createInfoConfirmation(null, "Successfully Connected").show();
                }
                else {
                    ConfirmationDialogs.createErrorConfirmation(null, "Failed to connect").show();
                }
            }
            catch (ServiceException e) {
                ConfirmationDialogs.createErrorConfirmation(null, e.getMessage()).show();
            }
        }
        else {
            ConfirmationDialogs.createErrorConfirmation(null, violations.iterator().next().getMessage()).show();
        }
    }
    
    private void handleGetDatabaseNamesEvent() {
        Set<ConstraintViolation<DatasourceObjectData>> violations = ValidationUtils.validate(wizardData.getData(), "userName", "password", "hostname");
        
        if (violations.isEmpty()) {
            DatasourceImportService service = ServiceManager.getDatasourceImportService();
            
            try {
                List<String> databaseNames = service.getDatabaseNames(wizardData.getData());                
                databaseNameComboBox.getItems().setAll(databaseNames);
            }
            catch (ServiceException e) {
                ConfirmationDialogs.createErrorConfirmation(null, e.getMessage()).show();
            }
        }
        else {
            ConfirmationDialogs.createErrorConfirmation(null, violations.iterator().next().getMessage()).show();
        }
    }
}