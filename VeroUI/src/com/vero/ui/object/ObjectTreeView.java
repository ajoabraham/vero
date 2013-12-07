/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.model.UIData;
import javafx.scene.control.TreeView;

/**
 *
 * @author Tai Hu
 * @param <T>
 */
public class ObjectTreeView<T extends UIData> extends TreeView<T> {
    public ObjectTreeView() {
        buildUI();
    }
    
    private void buildUI() {
        
    }
}
