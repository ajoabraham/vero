/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui.menu;

//import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;

import com.vero.ui.common.ConfirmationDialogs;
import com.vero.ui.common.UIDataManager;
import com.vero.ui.constants.ImageList;
import com.vero.ui.model.CommentBlockObjectData;
import com.vero.ui.model.GlobalFilterObjectData;
import com.vero.ui.model.ReportBlockObjectData;
import com.vero.ui.model.ReportObjectData;
import com.vero.ui.report.ReportPane;
import com.vero.ui.report.ReportTabManager;
import com.vero.ui.service.ServiceException;

/**
 *
 * @author Tai Hu
 */
public class VeroMenuBar extends MenuBar implements EventHandler<ActionEvent> {
//    private static final Logger logger = Logger.getLogger(VeroMenuBar.class.getName());
    
    private Menu newMenu = null;
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
        ((ActionMenu) newMenu).setOnMenuAction(this);
        
        saveMenu = new ActionMenu("SAVE", new ImageView(ImageList.IMAGE_SAVE));
        ((ActionMenu) saveMenu).setOnMenuAction(this);
        openMenu = new Menu("OPEN", new ImageView(ImageList.IMAGE_OPEN));
        runMenu = new Menu("RUN", new ImageView(ImageList.IMAGE_RUN));
        
        addBlockMenu = new Menu("ADD BLOCK", new ImageView(ImageList.IMAGE_ADD_BLOCK));
        commentBlockMenuItem = new MenuItem("Comment Block", new ImageView(ImageList.IMAGE_COMMENT));
        commentBlockMenuItem.setOnAction(this);
        queryBlockMenuItem = new MenuItem("Query Block", new ImageView(ImageList.IMAGE_ACTIVE_CIRCLE));
        queryBlockMenuItem.setOnAction(this);
        addBlockMenu.getItems().addAll(commentBlockMenuItem, queryBlockMenuItem);
        
        deleteMenu = new Menu("DELETE", new ImageView(ImageList.IMAGE_DELETE));
        
        getMenus().addAll(newMenu, saveMenu, openMenu, runMenu, addBlockMenu, deleteMenu);
    }

    @Override
    public void handle(ActionEvent event) {
	if (event.getSource() == newMenu) {
	    handleNewReportAction();
	}
	else if (event.getSource() == saveMenu) {
	    handleSaveAction();
	}
	else if (event.getSource() == commentBlockMenuItem) {
	    handleAddCommentBlockAction();
	}
	else if (event.getSource() == queryBlockMenuItem) {
	    handleAddQueryBlockAction();
	}
    }
        
    private void handleSaveAction() {
	Tab selectedReportTab = ReportTabManager.getInstance().getSelectedTab();
        ReportPane reportPane = (ReportPane) selectedReportTab.getContent();
        ReportObjectData reportObjectData = reportPane.getReportObjectData();

        try {
	    UIDataManager.getInstance().persistReportObjectData(reportObjectData);
	    ConfirmationDialogs.createInfoConfirmation(null, "Report is saved successfully.").show();
        }
        catch (ServiceException e) {
            ConfirmationDialogs.createErrorConfirmation(null, e.getMessage()).show();
        }        
    }

    private void handleAddQueryBlockAction() {
        Tab selectedReportTab = ReportTabManager.getInstance().getSelectedTab();
        ReportPane reportPane = (ReportPane) selectedReportTab.getContent();
        reportPane.addNewQueryBlockPane();
    }

    private void handleAddCommentBlockAction() {
        Tab selectedReportTab = ReportTabManager.getInstance().getSelectedTab();
        ReportPane reportPane = (ReportPane) selectedReportTab.getContent();
        reportPane.addNewCommentBlockPane();
    }

    private void handleNewReportAction() {
        ReportObjectData reportObjectData = new ReportObjectData();
        GlobalFilterObjectData globalFilterObjectData = new GlobalFilterObjectData();
        reportObjectData.setGlobalFilterObjectData(globalFilterObjectData);
        CommentBlockObjectData commentBlockObjectData = new CommentBlockObjectData();
        commentBlockObjectData.setPosition(0);
        reportObjectData.addBlockObjectData(commentBlockObjectData);
        ReportBlockObjectData reportBlockObjectData = new ReportBlockObjectData();
        reportObjectData.setReportBlockObjectData(reportBlockObjectData);        
        reportObjectData.setName("New Report");
        
        ReportTabManager.getInstance().createReportTab(reportObjectData);
    }
}
