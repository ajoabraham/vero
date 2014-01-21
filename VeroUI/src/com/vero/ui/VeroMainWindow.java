/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui;

import static com.vero.ui.constants.CSSConstants.ID_ROOT_PANE;
import static com.vero.ui.constants.UIConstants.NAVIGATION_PANE_WIDTH;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import com.vero.ui.common.UIManager;
import com.vero.ui.menu.VeroMenuBar;
import com.vero.ui.menu.VeroToolBar;
import com.vero.ui.model.ReportData;
import com.vero.ui.report.ReportTabManager;

/**
 *
 * @author Tai Hu
 */
public class VeroMainWindow extends BorderPane {
    private Pane veroToolBar = null;
    private UIManager uiManager = null;
    private ReportTabManager reportTabManager = null;
    
    public VeroMainWindow() {
        uiManager = UIManager.getInstance();
        reportTabManager = ReportTabManager.getInstance();
        
        buildUI();
    }
    
    private void buildUI() {
        setId(ID_ROOT_PANE);
  
        // TH 01/21/2014, Revisit after alpha
//        MenuBar systemMenuBar = new SystemMenuBar();
//        systemMenuBar.setUseSystemMenuBar(true);
//        setTop(systemMenuBar);
        setLeft(getLeftPane());
        setCenter(getCenterPane());
    }
    
    /**
     * Build tool bar and navigation pane
     * 
     * @return pane contains both tool bar and navigation pane 
     */
    private Pane getLeftPane() {
        GridPane leftPane = new GridPane();
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100);
        leftPane.getRowConstraints().add(rc);
        
        veroToolBar = new VeroToolBar();
        leftPane.getColumnConstraints().add(new ColumnConstraints(40));
        leftPane.add(veroToolBar, 0, 0);
        
        Pane navigationPane = new StackPane();
        
        Pane datasourceNavigationPane = uiManager.getDatasourceNavigationPane();
        Pane reportNavigationPane = uiManager.getReportNavigationPane();
        navigationPane.getChildren().setAll(reportNavigationPane, datasourceNavigationPane);
        
        leftPane.getColumnConstraints().add(new ColumnConstraints(NAVIGATION_PANE_WIDTH));
        leftPane.add(navigationPane, 1, 0);
        
        return leftPane;
    }
    
    private Pane getCenterPane() {
        BorderPane centerPane = new BorderPane();
        centerPane.setTop(new VeroMenuBar());
        centerPane.setCenter(reportTabManager.getReportTabPane());
        // create an empty report
        ReportData reportData = new ReportData();
        reportData.setName("New Report");
        reportTabManager.createReportTab(reportData);
        
        return centerPane;
    }
}
