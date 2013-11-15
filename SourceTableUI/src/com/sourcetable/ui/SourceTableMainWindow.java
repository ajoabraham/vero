/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sourcetable.ui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public class SourceTableMainWindow extends BorderPane {
    public SourceTableMainWindow() {
        buildUI();
    }
    
    private void buildUI() {
        setId("rootPane");
        
        setLeft(getLeftPane());
        setCenter(getCenterPane());
    }
    
    private Pane getLeftPane() {
        GridPane leftPane = new GridPane();
        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100);
        leftPane.getRowConstraints().add(rc);
        
        Pane navButtonPane = getNavButtonPane();
        leftPane.getColumnConstraints().add(new ColumnConstraints(40));
        leftPane.add(navButtonPane, 0, 0);
        
        Pane navigationPane = getNavigationPane();
        leftPane.getColumnConstraints().add(new ColumnConstraints(250));
        leftPane.add(navigationPane, 1, 0);
        
        return leftPane;
    }
    
    private Pane getCenterPane() {
        BorderPane centerPane = new BorderPane();
        
        return centerPane;
    }
    
    private Pane getNavButtonPane() {
        VBox navButtonPane = new VBox();
        navButtonPane.setId("navButtonPane");
        
        Button datasourceNavPaneButton = new Button();
        datasourceNavPaneButton.setId("datasourceNavPaneButton");
        Button datasourceObjButton = new Button();
        datasourceObjButton.setId("datasourceObjButton");
        
        navButtonPane.getChildren().addAll(datasourceNavPaneButton, datasourceObjButton);
        
        return navButtonPane;
    }
    
    private Pane getNavigationPane() {
        VBox navigationPane = new VBox();
        navigationPane.setId("navigationPane");
        
        return navigationPane;
    }
}
