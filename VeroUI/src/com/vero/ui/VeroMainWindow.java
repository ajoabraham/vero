/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import static com.vero.ui.common.UIConstants.*;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;

/**
 *
 * @author Tai Hu
 */
public class VeroMainWindow extends BorderPane {
    public VeroMainWindow() {
        buildUI();
    }
    
    private void buildUI() {
        setId("root-pane");
        
        setLeft(getLeftPane());
        setCenter(getCenterPane());
    }
    
    private Pane getLeftPane() {
        GridPane leftPane = new GridPane();
//leftPane.setStyle("-fx-border-color: yellow; -fx-border-width: 1;");        
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
//centerPane.setStyle("-fx-border-color: red; -fx-border-width: 1;");
        centerPane.setTop(getMenuBar());
        centerPane.setCenter(getTabPane());
        
        return centerPane;
    }
    
    private Parent getMenuBar() {
        MenuBar menuBar = new MenuBar();
        
        Menu saveMenu = new Menu("SAVE");
        Menu openMenu = new Menu("OPEN");
        Menu runMenu = new Menu("RUN");
        Menu addBlockMenu = new Menu("ADD BLOCK");
        Menu deleteMenu = new Menu("DELETE");
        
        menuBar.getMenus().addAll(saveMenu, openMenu, runMenu, addBlockMenu, deleteMenu);
        
        return menuBar;
    }
    
    private Parent getTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setId("main-tab-pane");
        
        Tab studentTab = new Tab("#Students by Department");
        tabPane.getTabs().add(studentTab);
        
        Tab otherReportTab = new Tab("Some Other Report");
        tabPane.getTabs().add(otherReportTab);
        
        return tabPane;
    }
    
    private Pane getNavButtonPane() {
        VBox navButtonPane = new VBox();
        navButtonPane.setId("nav-button-pane");
        
        Button datasourceNavPaneButton = new Button();
        datasourceNavPaneButton.setId("datasource-nav-pane-button");
        datasourceNavPaneButton.setPrefSize(DATASOURCE_NAV_PANE_BTN_WIDTH, DATASOURCE_NAV_PANE_BTN_HEIGHT);
        datasourceNavPaneButton.setMinSize(DATASOURCE_NAV_PANE_BTN_WIDTH, DATASOURCE_NAV_PANE_BTN_HEIGHT);
        Button reportsNavPaneButton = new Button();
        reportsNavPaneButton.setId("reports-nav-pane-button");
        reportsNavPaneButton.setPrefSize(REPORTS_NAV_PANE_BTN_WIDTH, REPORTS_NAV_PANE_BTN_HEIGHT);
        reportsNavPaneButton.setMinSize(REPORTS_NAV_PANE_BTN_WIDTH, REPORTS_NAV_PANE_BTN_HEIGHT);
        
        navButtonPane.getChildren().addAll(datasourceNavPaneButton, reportsNavPaneButton);
        
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
