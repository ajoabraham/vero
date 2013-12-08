/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import javafx.scene.control.TreeCell;

/**
 *
 * @author Tai Hu
 */
public class ObjectTreeCell extends TreeCell<ObjectPane> {
    public ObjectTreeCell() {
        
    }
    
    @Override
    protected void updateItem(ObjectPane item, boolean empty) {
        super.updateItem(item, empty);
        
        if (!empty) {
            setText(null);
            setGraphic(item);
        }
    }
}
