/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import com.vero.ui.common.UIManager;
import com.vero.ui.model.ReportObjectData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * This class handles all report tab panes in the application.
 * 
 * @author Tai Hu
 */
public final class ReportTabManager implements ChangeListener<Tab> {
    private static ReportTabManager INSTANCE = null;

    private TabPane reportTabPane = null;
    private Tab selectedTab = null;

    private ReportTabManager() {

    }

    public static ReportTabManager getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new ReportTabManager();
	}

	return INSTANCE;
    }

    public TabPane getReportTabPane() {
	if (reportTabPane == null) {
	    reportTabPane = new TabPane();
	    reportTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
	    reportTabPane.getSelectionModel().selectedItemProperty().addListener(this);
	}

	return reportTabPane;
    }

    public Tab createReportTab(ReportObjectData reportObjectData) {
	Tab reportTab = new Tab(reportObjectData.getName());

	reportTab.setContent(new ReportPane(reportObjectData));
	reportTabPane.getTabs().add(reportTab);
	reportTabPane.getSelectionModel().select(reportTab);
	selectedTab = reportTab;

	return reportTab;
    }

    @Override
    public void changed(ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
	UIManager.getInstance().setPrimaryStageTitle(newTab == null ? "" : newTab.getText());
	selectedTab = newTab;
    }
    
    public Tab getSelectedTab() {
	return selectedTab;
    }
}
