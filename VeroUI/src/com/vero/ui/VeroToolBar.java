/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui;

import static com.vero.ui.common.UIConstants.DATASOURCES_TOOL_BAR_BTN_HEIGHT;
import static com.vero.ui.common.UIConstants.DATASOURCES_TOOL_BAR_BTN_WIDTH;
import static com.vero.ui.common.UIConstants.REPORTS_TOOL_BAR_BTN_HEIGHT;
import static com.vero.ui.common.UIConstants.REPORTS_TOOL_BAR_BTN_WIDTH;
import static com.vero.ui.common.CSSConstants.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public class VeroToolBar extends VBox {
    
    
    public VeroToolBar() {
        buildUI();
    }

    private void buildUI() {
        setId(ID_VERO_TOOL_BAR);

        Button datasourceNavPaneButton = new Button();
        datasourceNavPaneButton.setId(ID_DATASOURCES_TOOL_BAR_BUTTON);
        datasourceNavPaneButton.setPrefSize(DATASOURCES_TOOL_BAR_BTN_WIDTH, DATASOURCES_TOOL_BAR_BTN_HEIGHT);
        datasourceNavPaneButton.setMinSize(DATASOURCES_TOOL_BAR_BTN_WIDTH, DATASOURCES_TOOL_BAR_BTN_HEIGHT);
        Button reportsNavPaneButton = new Button();
        reportsNavPaneButton.setId(ID_REPORTS_TOOL_BAR_BUTTON);
        reportsNavPaneButton.setPrefSize(REPORTS_TOOL_BAR_BTN_WIDTH, REPORTS_TOOL_BAR_BTN_HEIGHT);
        reportsNavPaneButton.setMinSize(REPORTS_TOOL_BAR_BTN_WIDTH, REPORTS_TOOL_BAR_BTN_HEIGHT);

        getChildren().addAll(datasourceNavPaneButton, reportsNavPaneButton);
    }
}
