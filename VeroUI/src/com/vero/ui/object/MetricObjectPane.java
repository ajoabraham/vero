/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.METRIC;
import com.vero.ui.model.MetricObjectData;

/**
 *
 * @author Tai Hu
 */
public class MetricObjectPane extends ObjectPane {
    private MetricObjectData data = null;
    
    public MetricObjectPane(MetricObjectData data) {
        this.data = data;
    }
    
    @Override
    public ObjectType getType() {
        return METRIC;
    }
    
}
