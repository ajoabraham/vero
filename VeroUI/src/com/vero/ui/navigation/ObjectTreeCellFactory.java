/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.navigation;

import static com.vero.ui.constants.ObjectType.DATASOURCE;
import static com.vero.ui.constants.ObjectType.TABLE;
import com.vero.ui.model.UIData;
import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author Tai Hu
 */
public class ObjectTreeCellFactory implements Callback<TreeView<ObjectPane>, TreeCell<ObjectPane>> {

    @Override
    public TreeCell<ObjectPane> call(TreeView<ObjectPane> param) {
        TreeCell<ObjectPane> treeCell = new ObjectTreeCell();   
        
        treeCell.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                ObjectTreeItem<? extends UIData> treeItem = (ObjectTreeItem<? extends UIData>) ((TreeCell<ObjectPane>) event.getSource()).getTreeItem();                                
                if ((treeItem.getObjectData().getType() == DATASOURCE 
                        || treeItem.getObjectData().getType() == TABLE)
                        && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    treeItem.setExpanded(!treeItem.isExpanded());
                }
            }
            
        });
        return treeCell;
    }
}
