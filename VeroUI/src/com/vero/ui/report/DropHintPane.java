/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import com.vero.ui.LabelPane;
import static com.vero.ui.common.CSSConstants.CLASS_DROP_HINT_PANE;

/**
 *
 * @author Tai Hu
 */
public class DropHintPane extends LabelPane {
    public DropHintPane() {
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_DROP_HINT_PANE);
    }
}
