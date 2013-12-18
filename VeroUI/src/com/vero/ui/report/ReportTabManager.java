/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import com.vero.ui.model.ReportData;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * This class handles all report tab panes in the application.
 * 
 * @author Tai Hu
 */
public final class ReportTabManager {
    private static ReportTabManager INSTANCE = null;
    
    private TabPane reportTabPane = null;
    
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
        }
        
        return reportTabPane;
    }
    
    public Tab createReportTab(ReportData reportData) {
        Tab reportTab = new Tab(reportData.getName());
        
        reportTab.setContent(new ReportPane());
        reportTabPane.getTabs().add(reportTab);
        
        return reportTab;
    }
}
