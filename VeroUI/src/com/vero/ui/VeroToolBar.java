/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui;

import com.vero.ui.common.UIManager;
import static com.vero.ui.common.UIConstants.DATASOURCES_TOOL_BAR_BTN_HEIGHT;
import static com.vero.ui.common.UIConstants.DATASOURCES_TOOL_BAR_BTN_WIDTH;
import static com.vero.ui.common.UIConstants.REPORTS_TOOL_BAR_BTN_HEIGHT;
import static com.vero.ui.common.UIConstants.REPORTS_TOOL_BAR_BTN_WIDTH;
import static com.vero.ui.common.CSSConstants.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public class VeroToolBar extends VBox implements EventHandler<ActionEvent> {
    private Button datasourcesToolBarButton = null;
    private Button reportsToolBarButton = null;
    
    public VeroToolBar() {
        buildUI();
    }

    private void buildUI() {
        setId(ID_VERO_TOOL_BAR);

        datasourcesToolBarButton = new Button();
        datasourcesToolBarButton.setId(ID_DATASOURCES_TOOL_BAR_BUTTON);
        datasourcesToolBarButton.setPrefSize(DATASOURCES_TOOL_BAR_BTN_WIDTH, DATASOURCES_TOOL_BAR_BTN_HEIGHT);
        datasourcesToolBarButton.setMinSize(DATASOURCES_TOOL_BAR_BTN_WIDTH, DATASOURCES_TOOL_BAR_BTN_HEIGHT);
        datasourcesToolBarButton.setTooltip(new Tooltip("Browse datasources"));
        datasourcesToolBarButton.setOnAction(this);
        reportsToolBarButton = new Button();
        reportsToolBarButton.setId(ID_REPORTS_TOOL_BAR_BUTTON);
        reportsToolBarButton.setPrefSize(REPORTS_TOOL_BAR_BTN_WIDTH, REPORTS_TOOL_BAR_BTN_HEIGHT);
        reportsToolBarButton.setMinSize(REPORTS_TOOL_BAR_BTN_WIDTH, REPORTS_TOOL_BAR_BTN_HEIGHT);
        reportsToolBarButton.setTooltip(new Tooltip("Browse reports"));
        reportsToolBarButton.setOnAction(this);

        getChildren().addAll(datasourcesToolBarButton, reportsToolBarButton);
    }

    @Override
    public void handle(ActionEvent e) {
        if (e.getSource() == datasourcesToolBarButton) {
            UIManager.getInstance().showDatasourceNavigationPane();
        }
        else if (e.getSource() == reportsToolBarButton) {
            UIManager.getInstance().showReportNavigationPane();
        }
    }
}
