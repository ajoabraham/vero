/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vero.ui.constants.DBType;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.editor.EditorTableLabelPane;
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
import com.vero.ui.navigation.TableObjectPane;
import com.vero.ui.report.ReportPane;
import com.vero.ui.report.dropzone.AttributeDropZoneObjectPane;
import com.vero.ui.report.dropzone.DropHintPane;
import com.vero.ui.report.dropzone.DropZoneObjectPane;
import com.vero.ui.report.dropzone.MetricDropZoneObjectPane;
import com.vero.ui.report.dropzone.PlaceholderPane;
import com.vero.ui.report.dropzone.ReportNameEditablePane;
import com.vero.ui.report.dropzone.TableDropZoneObjectPane;
import com.vero.ui.report.dropzone.TableJoinDropZoneObjectPane;
import com.vero.ui.wizard.datasource.DBTypeLabelPane;
import com.vero.ui.wizard.datasource.ListedTableLabelPane;

/**
 *
 * @author Tai Hu
 */
public final class LabelPaneFactory { 
    private static final Logger logger = Logger.getLogger(LabelPaneFactory.class.getName());
    
    private LabelPaneFactory() {
        
    }
   
    public static ObjectPane createObjectPane(ObjectType type, UIData data, boolean isDraggable) {
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
    
    public static DropZoneObjectPane<? extends UIData> createDropZoneObjectPane(ReportPane reportPane, UIData data) {
       DropZoneObjectPane<? extends UIData> dropZoneObjectPane = null;
        
        switch (data.getType()) {
            case TABLE:
                dropZoneObjectPane = new TableDropZoneObjectPane(reportPane, (TableObjectData) data);                
                break;
            case ATTRIBUTE:
                dropZoneObjectPane = new AttributeDropZoneObjectPane(reportPane, (AttributeObjectData) data);
                break;
            case METRIC:
                dropZoneObjectPane = new MetricDropZoneObjectPane(reportPane, (MetricObjectData) data);               
                break;
            case TABLE_JOIN:
                dropZoneObjectPane = new TableJoinDropZoneObjectPane(reportPane, (TableJoinObjectData) data);               
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", data.getType());
        }
        
        return dropZoneObjectPane;
    }
    
    public static LabelPane createPlaceholderPane(String text) {
        return new PlaceholderPane(text);
    }
    
    public static LabelPane createDropHintPane() {
        return new DropHintPane();
    }
    
    public static LabelPane createReportNameEditablePane(String text) {
        return new ReportNameEditablePane(text);
    }
    
    public static LabelPane createTableJoinPane(ReportPane reportPane, TableJoinObjectData tableJoinObjectData) {
        return new TableJoinDropZoneObjectPane(reportPane, tableJoinObjectData);
    }
    
    public static LabelPane createDBTypeLabelPane(DBType dbType) {
	return new DBTypeLabelPane(dbType);
    }
    
    public static ListedTableLabelPane createListedTableLabelPane(TableObjectData tableData, boolean showStats) {
        return new ListedTableLabelPane(tableData, showStats);
    }
    
    public static EditorTableLabelPane createEditorTablePane(TableObjectData tableObjectData) {
        return new EditorTableLabelPane(tableObjectData);
    }
}
