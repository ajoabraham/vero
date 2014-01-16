/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui.menu;

import com.vero.ui.common.ConfirmationDialogs;
import com.vero.ui.common.PopupDialog;
import com.vero.ui.common.UIManager;
import com.vero.ui.constants.ImageList;
import com.vero.ui.model.ReportData;
import com.vero.ui.report.ReportTabManager;
import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardFactory;
import com.vero.ui.wizard.datasource.DatasourceWizardData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

/**
 *
 * @author Tai Hu
 */
public class VeroMenuBar extends MenuBar implements EventHandler<ActionEvent> {

    private Menu newMenu = null;
    private MenuItem newReportMenuItem = null;
    private MenuItem newDatasourceMenuItem = null;
    private Menu saveMenu = null;
    private Menu openMenu = null;
    private Menu runMenu = null;
    private Menu addBlockMenu = null;
    private MenuItem queryBlockMenuItem = null;
    private MenuItem commentBlockMenuItem = null;
    private Menu deleteMenu = null;

    public VeroMenuBar() {
        buildUI();
    }

    private void buildUI() {
        newMenu = new ActionMenu("NEW", new ImageView(ImageList.IMAGE_NEW)); 
//        ((ActionMenu) newMenu).setOnMenuAction(this);
        newDatasourceMenuItem = new MenuItem("Datasource", new ImageView(ImageList.IMAGE_DATASOURCE_OBJECT));
        newDatasourceMenuItem.setOnAction(this);
        newReportMenuItem = new MenuItem("Report", new ImageView(ImageList.IMAGE_ACTIVE_CIRCLE));
        newReportMenuItem.setOnAction(this);
        newMenu.getItems().addAll(newDatasourceMenuItem, newReportMenuItem);
        
        saveMenu = new Menu("SAVE", new ImageView(ImageList.IMAGE_SAVE));
        openMenu = new Menu("OPEN", new ImageView(ImageList.IMAGE_OPEN));
        runMenu = new Menu("RUN", new ImageView(ImageList.IMAGE_RUN));
        
        addBlockMenu = new Menu("ADD BLOCK", new ImageView(ImageList.IMAGE_ADD_BLOCK));
        commentBlockMenuItem = new MenuItem("Comment Block", new ImageView(ImageList.IMAGE_COMMENT));
        queryBlockMenuItem = new MenuItem("Query Block", new ImageView(ImageList.IMAGE_ACTIVE_CIRCLE));
        addBlockMenu.getItems().addAll(commentBlockMenuItem, queryBlockMenuItem);
        
        deleteMenu = new Menu("DELETE", new ImageView(ImageList.IMAGE_DELETE));
        
        getMenus().addAll(newMenu, saveMenu, openMenu, runMenu, addBlockMenu, deleteMenu);
    }

    @Override
    public void handle(ActionEvent event) {
	if (event.getSource() == newDatasourceMenuItem) {
	    handleNewDatasourceAction();
	}
	else if (event.getSource() == newReportMenuItem) {
	    handleNewReportAction();
	}
    }
    
    private void handleNewDatasourceAction() {
        try {
            DatasourceWizardData wizardData = new DatasourceWizardData();
            PopupDialog popupDialog = WizardFactory.getInstance().createDatasourceWizard(wizardData);
            popupDialog.show();
        }
        catch (WizardException e) {
            ConfirmationDialogs.createErrorConfirmation(UIManager.getInstance().getPrimaryStage(), e.getMessage()).show();
        }
    }
    
    private void handleNewReportAction() {
        ReportData reportData = new ReportData();
        reportData.setName("New Report");
        ReportTabManager.getInstance().createReportTab(reportData);
    }
}
