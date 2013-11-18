/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sourcetable.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import static com.sourcetable.ui.common.UIConstants.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author Tai Hu
 */
public class SourceTableMainWindow extends BorderPane {
    public SourceTableMainWindow() {
        buildUI();
    }
    
    private void buildUI() {
        setId("root-pane");
        
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
        leftPane.getColumnConstraints().add(new ColumnConstraints(NAVIGATION_PANE_WIDTH));
        leftPane.add(navigationPane, 1, 0);
        
        return leftPane;
    }
    
    private Pane getCenterPane() {
        BorderPane centerPane = new BorderPane();
        
        centerPane.setTop(getMenuBar());
        centerPane.setCenter(getTabPane());
        
        return centerPane;
    }
    
    private Pane getMenuBar() {
        return null;
    }
    
    private Pane getTabPane() {
        return null;
    }
    
    private Pane getNavButtonPane() {
        VBox navButtonPane = new VBox();
        navButtonPane.setId("nav-button-pane");
        
        Button datasourceNavPaneButton = new Button();
        datasourceNavPaneButton.setId("datasource-nav-pane-button");
        datasourceNavPaneButton.setPrefSize(DATASOURCE_NAV_PANE_BTN_WIDTH, DATASOURCE_NAV_PANE_BTN_HEIGHT);
        datasourceNavPaneButton.setMinSize(DATASOURCE_NAV_PANE_BTN_WIDTH, DATASOURCE_NAV_PANE_BTN_HEIGHT);
        Button datasourceObjButton = new Button();
        datasourceObjButton.setId("datasource-obj-button");
        datasourceObjButton.setPrefSize(DATASOURCE_OBJ_BTN_WIDTH, DATASOURCE_OBJ_BTN_HEIGHT);
        datasourceObjButton.setMinSize(DATASOURCE_OBJ_BTN_WIDTH, DATASOURCE_OBJ_BTN_HEIGHT);
        
        navButtonPane.getChildren().addAll(datasourceNavPaneButton, datasourceObjButton);
        
        return navButtonPane;
    }
    
    private Pane getNavigationPane() {
        BorderPane navigationPane = new BorderPane();
        navigationPane.setId("navigation-pane");
        
        Label datasourcesLabel = new Label("DATASOURCES");
        datasourcesLabel.getStyleClass().add("section-title");
        navigationPane.setTop(datasourcesLabel);
        
        VBox objectsPane = new VBox();
        objectsPane.setId("objects-pane");
        
        objectsPane.getChildren().add(buildObjectPane("Teradata", null, null));
        objectsPane.getChildren().add(buildObjectPane("LessionsFact", null, null));
        objectsPane.getChildren().add(buildObjectPane("Professors", null, null));
        objectsPane.getChildren().add(buildObjectPane("Professor ID", null, null));
        objectsPane.getChildren().add(buildObjectPane("Professor Name", null, null));
        objectsPane.getChildren().add(buildObjectPane("# Professors", null, null));
        objectsPane.getChildren().add(buildObjectPane("Students", null, null));
        objectsPane.getChildren().add(buildObjectPane("Departments", null, null));
        objectsPane.getChildren().add(buildObjectPane("Oracle-Billing", null, null));
        objectsPane.getChildren().add(buildObjectPane("Hive-Prod", null, null));
        
        navigationPane.setCenter(objectsPane);
        
        return navigationPane;
    }
    
    private Pane buildObjectPane(String labelText, ImageView frontImage, ImageView backImage) {
        BorderPane objectPane = new BorderPane();
        objectPane.getStyleClass().add("object-pane");
        objectPane.setPrefSize(OBJECT_PANE_WIDTH, OBJECT_PANE_HEIGHT);
        objectPane.setMinSize(OBJECT_PANE_WIDTH, OBJECT_PANE_HEIGHT);
        
        if (frontImage != null) {
            objectPane.setLeft(frontImage);
        }
        
        Label label = new Label(labelText);
        label.setPrefSize(OBJECT_PANE_WIDTH, OBJECT_PANE_HEIGHT);
        label.setMinSize(OBJECT_PANE_WIDTH, OBJECT_PANE_HEIGHT);
        label.getStyleClass().add("object-label");
        objectPane.setCenter(label);
        
        if (backImage != null) {
            objectPane.setRight(backImage);
        }
        
        return objectPane;
    }
}
