/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import com.vero.ui.LabelPane;
import static com.vero.ui.common.CSSConstants.CLASS_PLACEHOLDER_PANE;
import javafx.scene.control.Label;

/**
 *
 * @author Tai Hu
 */
public class PlaceholderPane extends LabelPane {
    private String text = null;
    
    public PlaceholderPane(String text) {
        this.text = text;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_PLACEHOLDER_PANE);
        
        Label label = new Label(text);
        getChildren().add(label);
    }
}
