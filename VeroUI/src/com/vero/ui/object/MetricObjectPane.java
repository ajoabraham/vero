/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import static com.vero.ui.common.CSSConstants.CLASS_OBJECT_LABEL;
import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.METRIC;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.UIData;
import com.vero.ui.util.UIUtils;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

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
        getStyleClass().add(UIUtils.getObjectSytleClass(getType()));

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
