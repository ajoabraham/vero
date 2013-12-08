/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 *
 * @author Tai Hu
 */
public class ObjectTreeCellFactory implements Callback<TreeView<ObjectPane>, TreeCell<ObjectPane>> {

    @Override
    public TreeCell<ObjectPane> call(TreeView<ObjectPane> param) {
        TreeCell<ObjectPane> treeCell = new ObjectTreeCell();
        
        return treeCell;
    }
}
