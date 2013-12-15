/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui;

import static com.vero.ui.common.CSSConstants.CLASS_LABEL_PANE;
import static com.vero.ui.common.UIConstants.DEFAULT_LABEL_PANE_HEIGHT;
import javafx.scene.layout.HBox;

/**
 *
 * @author Tai Hu
 */
public abstract class LabelPane extends HBox {
    public LabelPane() {
        getStyleClass().add(CLASS_LABEL_PANE);
        setPrefHeight(DEFAULT_LABEL_PANE_HEIGHT);
    }
}
