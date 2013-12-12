/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.METRIC;
import javafx.scene.input.DragEvent;

/**
 *
 * @author Tai Hu
 */
public class MetricDropPane extends DropPane {
    public MetricDropPane() {
        
    }
    
    @Override
    public ObjectType getType() {
        return METRIC;
    }
}
