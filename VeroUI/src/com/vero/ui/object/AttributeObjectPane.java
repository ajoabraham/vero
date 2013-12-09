/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import static com.vero.ui.common.CSSConstants.CLASS_OBJECT_LABEL;
import static com.vero.ui.common.CSSConstants.CLASS_OBJECT_PANE;
import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.ATTRIBUTE;
import static com.vero.ui.common.UIConstants.OBJECT_PANE_HEIGHT;
import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.util.UIUtils;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author Tai Hu
 */
public class AttributeObjectPane extends DraggableObjectPane {
    private AttributeObjectData data = null;
    
    public AttributeObjectPane(AttributeObjectData data) {
        this.data = data;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().addAll(CLASS_OBJECT_PANE, UIUtils.getObjectSytleClass(getType()));
        setPrefHeight(OBJECT_PANE_HEIGHT);

        Label label = new Label(data.getName());
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);
    }
    
    @Override
    public ObjectType getType() {
        return ATTRIBUTE;
    }
}
