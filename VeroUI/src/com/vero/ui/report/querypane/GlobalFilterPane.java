package com.vero.ui.report.querypane;

import static com.vero.ui.constants.UIConstants.DEFAULT_LABEL_PANE_HEIGHT;
import static com.vero.ui.constants.CSSConstants.CLASS_GLOBAL_FILTER_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_SUBSECTION_TITLE;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class GlobalFilterPane extends VBox {
    public GlobalFilterPane() {
	buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_GLOBAL_FILTER_PANE);
        
        Label globalFilterLabel = new Label("GLOBAL FILTERS");
        globalFilterLabel.setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
        globalFilterLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        getChildren().add(globalFilterLabel);
        
        TextField filterTextField = new TextField();
        filterTextField.setPromptText("Type a column, attribute, metric, or table name to start...");
        getChildren().add(filterTextField);
    }
}
