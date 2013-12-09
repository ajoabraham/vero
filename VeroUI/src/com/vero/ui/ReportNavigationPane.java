/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui;

import static com.vero.ui.common.CSSConstants.CLASS_SECTION_TITLE;
import static com.vero.ui.common.CSSConstants.ID_REPORT_NAVIGATION_PANE;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public class ReportNavigationPane extends BorderPane {
    public ReportNavigationPane() {
        buildUI();
    }
    
    private void buildUI() {
        setId(ID_REPORT_NAVIGATION_PANE);

        Label reportsLabel = new Label("REPORTS");
        reportsLabel.getStyleClass().add(CLASS_SECTION_TITLE);
        setTop(reportsLabel);

        VBox reportsPane = new VBox();
        reportsPane.setFillWidth(true);
        
        setCenter(reportsPane);
    }
}
