/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import javafx.scene.layout.HBox;

/**
 *
 * @author Tai Hu
 */
public abstract class ObjectPane extends HBox {
    public ObjectPane() {
    }
    
    public abstract ObjectType getType();
}
