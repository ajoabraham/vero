/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.navigation;

import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_LABEL;
import static com.vero.ui.constants.ObjectType.METRIC;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.UIData;

/**
 *
 * @author Tai Hu
 */
public class MetricObjectPane extends ObjectPane {
    private MetricObjectData data = null;
    
    public MetricObjectPane(MetricObjectData data) {
        this.data = data;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(getType().getStyleClass());

        Label label = new Label(data.getName());
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);
    }
    
    @Override
    public ObjectType getType() {
        return METRIC;
    }

    @Override
    public UIData getTransferData() {
        return data;
    }    
}
