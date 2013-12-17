/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.navigation;

import static com.vero.ui.constants.ObjectType.ATTRIBUTE;
import static com.vero.ui.constants.ObjectType.COLUMN;
import static com.vero.ui.constants.ObjectType.METRIC;
import com.vero.ui.util.UIUtils;
import javafx.scene.control.TreeCell;

/**
 *
 * @author Tai Hu
 */
public class ObjectTreeCell extends TreeCell<ObjectPane> {
    public ObjectTreeCell() {
        // In UI, expand/collapse arrow is hidden. However, empty space still
        // exists in front of tree cell. Replace node holds arrow with an empty
        // group to remove space.      
        setDisclosureNode(UIUtils.createHorizontalSpaceFiller(0));
    }
    
    @Override
    protected void updateItem(ObjectPane item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty) {
            setText(null);
            setGraphic(item);
            
            // In UI, for leaf nodes with unknown reason, there are extra
            // space in front of tree cell. Reduced indent here to minimize
            // extra space.
            if (item.getType() == ATTRIBUTE || item.getType() == METRIC
                    || item.getType() == COLUMN) {
               setStyle("-fx-indent: -5;");
            }
        }
    }
}
