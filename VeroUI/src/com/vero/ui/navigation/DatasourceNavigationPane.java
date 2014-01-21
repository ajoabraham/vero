/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui.navigation;

import static com.vero.ui.constants.CSSConstants.CLASS_SECTION_TITLE;
import static com.vero.ui.constants.CSSConstants.ID_ADD_DATASOURCE_BUTTON_PANE;
import static com.vero.ui.constants.CSSConstants.ID_DATASOURCE_NAVIGATION_PANE;
import static com.vero.ui.constants.CSSConstants.ID_OBJECTS_PANE;
import static com.vero.ui.constants.CSSConstants.ID_OBJECT_SEARCH_TEXT_FIELD;
import static com.vero.ui.constants.UIConstants.DEFAULT_LABEL_PANE_HEIGHT;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import com.vero.ui.common.ConfirmationDialogs;
import com.vero.ui.common.PopupDialog;
import com.vero.ui.common.UIManager;
import com.vero.ui.util.UIUtils;
import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardFactory;
import com.vero.ui.wizard.datasource.DatasourceWizardData;

/**
 *
 * @author Tai Hu
 */
public class DatasourceNavigationPane extends BorderPane implements EventHandler<ActionEvent> {
    private Button addDatasourceButton = null;
    
    public DatasourceNavigationPane() {
        buildUI();
    }

    private void buildUI() {
        setId(ID_DATASOURCE_NAVIGATION_PANE);

        HBox addDatasourceButtonPane = new HBox();
        addDatasourceButtonPane.setId(ID_ADD_DATASOURCE_BUTTON_PANE);
                
        Label datasourcesLabel = new Label("DATASOURCES");
        datasourcesLabel.getStyleClass().add(CLASS_SECTION_TITLE);
        addDatasourceButton = new Button();
        addDatasourceButton.setOnAction(this);
        addDatasourceButton.setTooltip(new Tooltip("New Datasource"));
        addDatasourceButtonPane.getChildren().addAll(datasourcesLabel, addDatasourceButton);
        
        setTop(addDatasourceButtonPane);

        VBox objectsPane = new VBox();
        objectsPane.setFillWidth(true);
        objectsPane.setId(ID_OBJECTS_PANE);

        // Build search box first.
        TextField searchField = new TextField();
        searchField.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        searchField.setPromptText("Search...");
        searchField.setId(ID_OBJECT_SEARCH_TEXT_FIELD);

        objectsPane.getChildren().add(searchField);
        objectsPane.getChildren().add(UIUtils.createVerticalSpaceFiller(10));

        TreeView<ObjectPane> treeView = new ObjectTreeView();
        VBox.setVgrow(treeView, Priority.ALWAYS);
        objectsPane.getChildren().add(treeView);

        setCenter(objectsPane);
    }

    @Override
    public void handle(ActionEvent e) {
	if (e.getSource() == addDatasourceButton) {
	    handleNewDatasourceAction();
	}
    }
    
    private void handleNewDatasourceAction() {
	try {
	    DatasourceWizardData wizardData = new DatasourceWizardData();
	    PopupDialog popupDialog = WizardFactory.getInstance().createDatasourceWizard(wizardData);
	    popupDialog.show();
	}
	catch (WizardException e) {
	    ConfirmationDialogs.createErrorConfirmation(UIManager.getInstance().getPrimaryStage(), e.getMessage())
		    .show();
	}
    }
}
