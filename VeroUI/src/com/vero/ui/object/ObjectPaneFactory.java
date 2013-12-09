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
import java.util.logging.Logger;

/**
 *
 * @author Tai Hu
 */
public final class ObjectPaneFactory {
    private static final Logger logger = Logger.getLogger(ObjectPaneFactory.class.getName());
    
    private static ObjectPaneFactory INSTANCE = null;
    
    private ObjectPaneFactory() {
        
    }
    
    public static ObjectPaneFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObjectPaneFactory();
        }
        
        return INSTANCE;
    }
    
    public ObjectPane createObjectPane(ObjectType type, UIData data) {
        ObjectPane objectPane = null;
        boolean isDraggable = false;
        boolean isDroppable = false;
        
        switch (type) {
            case DATASOURCE:
                objectPane = new DatasourceObjectPane((DatasourceObjectData) data);
                break;
            case TABLE:
                objectPane = new TableObjectPane((TableObjectData) data);                
                break;
            case COLUMN:
                objectPane = new ColumnObjectPane((ColumnObjectData) data);
                isDraggable = true;
                break;
            case ATTRIBUTE:
                objectPane = new AttributeObjectPane((AttributeObjectData) data);
                isDraggable = true;
                break;
            case METRIC:
                objectPane = new MetricObjectPane((MetricObjectData) data);               
                isDraggable = true;
                break;
            default:
                logger.severe("Invalid object type - " + type);
        }
        
        if (isDraggable) {
            DragManager.newInstance(objectPane);
        }
        
        if (isDroppable) {
            DropManager.newInstance(objectPane);
        }
        
        return objectPane;
    }
}
