/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.common.ObjectType;
import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.model.UIData;

/**
 *
 * @author Tai Hu
 */
public final class ObjectPaneFactory {
    public static ObjectPane createObjectPane(ObjectType type, UIData data) {
        ObjectPane objectPane = null;
        
        switch (type) {
            case DATASOURCE:
                objectPane = new DatasourceObjectPane((DatasourceObjectData) data);
                break;
            case TABLE:
                objectPane = new TableObjectPane((TableObjectData) data);
                break;
            case COLUMN:
                objectPane = new ColumnObjectPane((ColumnObjectData) data);
                break;
            case ATTRIBUTE:
                objectPane = new AttributeObjectPane((AttributeObjectData) data);
                break;
            case METRIC:
                objectPane = new MetricObjectPane((MetricObjectData) data);
        }
        
        return objectPane;
    }
}
