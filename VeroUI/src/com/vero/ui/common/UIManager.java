/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.common;

import com.vero.ui.navigation.DatasourceNavigationPane;
import com.vero.ui.navigation.ReportNavigationPane;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * All UI components are registered with UIManager once created. Singleton
 * pattern allow access to UI component anyway in the application.
 * 
 * @author Tai Hu
 */
public final class UIManager {
    private static UIManager INSTANCE = null;

    private Pane veroRootPane = null;

    private Pane datasourceNavigationPane = null;
    private Pane reportNavigationPane = null;
    
    private Stage primaryStage = null;

    private UIManager() {

    }

    public static UIManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UIManager();
        }

        return INSTANCE;
    }

    public Pane getDatasourceNavigationPane() {
        if (datasourceNavigationPane == null) {
            datasourceNavigationPane = new DatasourceNavigationPane();
        }

        return datasourceNavigationPane;
    }

    public Pane getReportNavigationPane() {
        if (reportNavigationPane == null) {
            reportNavigationPane = new ReportNavigationPane();
        }

        return reportNavigationPane;
    }

    public void showDatasourceNavigationPane() {
        if (datasourceNavigationPane != null) {
            datasourceNavigationPane.toFront();
        }
    }

    public void showReportNavigationPane() {
        if (reportNavigationPane != null) {
            reportNavigationPane.toFront();
        }
    }

    public Pane getVeroRootPane() {
        if (veroRootPane == null) {
            veroRootPane = new StackPane();
        }

        return veroRootPane;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
