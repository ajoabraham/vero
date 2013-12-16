/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui;

import com.vero.ui.common.ObjectType;
import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.model.UIData;
import com.vero.ui.object.AttributeObjectPane;
import com.vero.ui.object.ColumnObjectPane;
import com.vero.ui.object.DatasourceObjectPane;
import com.vero.ui.object.DragManager;
import com.vero.ui.object.MetricObjectPane;
import com.vero.ui.object.ObjectPane;
import com.vero.ui.object.TableObjectPane;
import com.vero.ui.report.DropHintPane;
import com.vero.ui.report.PlaceholderPane;
import com.vero.ui.report.ReportNameEditablePane;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tai Hu
 */
public final class LabelPaneFactory {
    private static final Logger logger = Logger.getLogger(LabelPaneFactory.class.getName());
    
    private static LabelPaneFactory INSTANCE = null;
    
    private LabelPaneFactory() {
        
    }
    
    public static LabelPaneFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LabelPaneFactory();       
        }
        
        return INSTANCE;
    }
    
    public ObjectPane createObjectPane(ObjectType type, UIData data, boolean isDraggable) {
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
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", type);
        }
        
        if (isDraggable) {
            DragManager.newInstance(objectPane);
        }
        
        return objectPane;
    }
    
    public LabelPane createPlaceholderPane(String text) {
        return new PlaceholderPane(text);
    }
    
    public LabelPane createDropHintPane() {
        return new DropHintPane();
    }
    
    public LabelPane createReportNameEditablePane(String text) {
        return new ReportNameEditablePane(text);
    }
}
