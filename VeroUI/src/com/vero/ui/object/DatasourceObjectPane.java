/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.DATASOURCE;
import static com.vero.ui.common.UIConstants.OBJECT_PANE_HEIGHT;
import static com.vero.ui.common.CSSConstants.*;
import com.vero.ui.common.ImageList;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.util.UIUtils;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author Tai Hu
 */
public class DatasourceObjectPane extends ObjectPane {
    private DatasourceObjectData data = null;
    private ImageView statusImageView = null;
    
    public DatasourceObjectPane(DatasourceObjectData data) {
        this.data = data;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().addAll(CLASS_OBJECT_PANE, UIUtils.getObjectSytleClass(getType()));
        setPrefHeight(OBJECT_PANE_HEIGHT);

        ImageView datasourceImageView = new ImageView(ImageList.IMAGE_DATASOURCE_OBJECT);
        getChildren().add(datasourceImageView);
       
        Label label = new Label(data.getName());
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);

        statusImageView = new ImageView(ImageList.IMAGE_INACTIVE_CIRCLE);
        getChildren().add(statusImageView);
    }
    
    @Override
    public ObjectType getType() {
        return DATASOURCE;
    }    
}
