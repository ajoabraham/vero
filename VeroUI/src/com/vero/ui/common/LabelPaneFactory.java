/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.common;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.constants.TableJoinType;
import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.TableJoinObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.model.UIData;
import com.vero.ui.navigation.AttributeObjectPane;
import com.vero.ui.navigation.ColumnObjectPane;
import com.vero.ui.navigation.DatasourceObjectPane;
import com.vero.ui.navigation.MetricObjectPane;
import com.vero.ui.navigation.ObjectPane;
import com.vero.ui.navigation.TableJoinObjectPane;
import com.vero.ui.navigation.TableObjectPane;
import com.vero.ui.report.dropzone.DropHintPane;
import com.vero.ui.report.dropzone.PlaceholderPane;
import com.vero.ui.report.dropzone.ReportNameEditablePane;
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
    
    public LabelPane createTableJoinPane(String leftTableName, TableJoinType tableJoinType, String rightTableName) {
        return new TableJoinObjectPane(new TableJoinObjectData(leftTableName, tableJoinType, rightTableName));
    }
}
