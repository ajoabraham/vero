/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.querypane;

import static com.vero.ui.constants.CSSConstants.CLASS_QUERY_PANE;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tai Hu
 */
public class QueryPane extends VBox {
    public QueryPane() {
        buildUI();
    }
    
    private void buildUI() {
	getStyleClass().add(CLASS_QUERY_PANE);
	        
	getChildren().addAll(new GlobalFilterPane(), new CommentBlockPane(), new ReportBlockPane());
    }
}
