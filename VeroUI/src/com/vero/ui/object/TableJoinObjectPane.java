/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import static com.vero.ui.common.CSSConstants.CLASS_OBJECT_PANE;
import com.vero.ui.common.ImageList;
import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.TABLE_JOIN;
import com.vero.ui.common.TableJoinType;
import com.vero.ui.model.TableJoinObjectData;
import com.vero.ui.util.UIUtils;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author Tai Hu
 */
public class TableJoinObjectPane extends ObjectPane implements EventHandler<MouseEvent> {
    private TableJoinObjectData data = null;

    private ImageView tableJoinImageView = null;
    
    public TableJoinObjectPane(TableJoinObjectData data) {
        this.data = data;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(UIUtils.getObjectSytleClass(getType()));
        // Table join pane should not have a border.
        getStyleClass().remove(CLASS_OBJECT_PANE);
        
        Label leftTableLabel = new Label(data.getLeftTableName(), new ImageView(ImageList.IMAGE_UNKNOWN_TABLE));
        leftTableLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(leftTableLabel, Priority.ALWAYS);
        getChildren().add(leftTableLabel);

        tableJoinImageView = new ImageView(ImageList.IMAGE_INNER_JOIN);
        tableJoinImageView.setOnMouseClicked(this);
        getChildren().add(tableJoinImageView);

        Label rightTableLabel = new Label(data.getRightTableName(), new ImageView(ImageList.IMAGE_UNKNOWN_TABLE));
        rightTableLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(rightTableLabel, Priority.ALWAYS);
        getChildren().add(rightTableLabel);
    }
    
    @Override
    public ObjectType getType() {
        return TABLE_JOIN;
    }   

    @Override
    public void handle(MouseEvent event) {
        if (event.getClickCount() == 1) {
            TableJoinType nextType = data.getTableJoinType().next();
            tableJoinImageView.setImage(UIUtils.getTableJoinImage(nextType));
            data.setTableJoinType(nextType);
        }
    }
}