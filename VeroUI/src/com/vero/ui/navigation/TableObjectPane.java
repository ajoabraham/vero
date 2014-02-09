/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.navigation;

import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_LABEL;
import static com.vero.ui.constants.ObjectType.TABLE;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.ImageList;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.model.UIData;

/**
 *
 * @author Tai Hu
 */
public class TableObjectPane extends ObjectPane {
    private TableObjectData data = null;
    
    public TableObjectPane(TableObjectData data) {
        this.data = data;        
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(getType().getStyleClass());
        
        ImageView tableImageView = new ImageView(data.getTableType().getImage());
        getChildren().add(tableImageView);

        Label label = new Label(data.getName());
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);
    }
    
    @Override
    public ObjectType getType() {
        return TABLE;
    }
    
    @Override
    public UIData getTransferData() {
        return data;
    }
}
