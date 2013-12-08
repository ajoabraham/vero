/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import static com.vero.ui.common.ObjectType.ATTRIBUTE;
import static com.vero.ui.common.ObjectType.COLUMN;
import static com.vero.ui.common.ObjectType.DATASOURCE;
import static com.vero.ui.common.ObjectType.METRIC;
import static com.vero.ui.common.ObjectType.TABLE;
import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.RootObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.model.UIData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Tai Hu
 * @param <T>
 */
public class ObjectTreeItem<T extends UIData> extends TreeItem<ObjectPane> {
    private static final Logger logger = Logger.getLogger(ObjectTreeItem.class.getName());
    
    private T value = null;
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;
    private boolean isLeaf = false;
    
    public ObjectTreeItem(T value, ObjectPane objectPane) {
        super(objectPane);
        this.value = value;
    }
    
    @Override
    public ObservableList<TreeItem<ObjectPane>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            
            ObjectPaneFactory objectPaneFactory = ObjectPaneFactory.getInstance();
            
            switch(value.getType()) {
                case ROOT:
                    List<DatasourceObjectData> datasourceObjectDataList = ((RootObjectData) value).getDatasourceObjectDataList();
                    List<TreeItem<ObjectPane>> datasourceObjectTreeItemList = new ArrayList<>();
                    
                    for (DatasourceObjectData datasourceObjectData : datasourceObjectDataList) {
                        TreeItem<ObjectPane> treeItem = new ObjectTreeItem(datasourceObjectData, 
                                objectPaneFactory.createObjectPane(DATASOURCE, datasourceObjectData));
                        datasourceObjectTreeItemList.add(treeItem);
                    }
                    
                    super.getChildren().setAll(datasourceObjectTreeItemList);                    

                    break;
                case DATASOURCE:
                    List<TableObjectData> tableObjectDataList = ((DatasourceObjectData) value).getTableObjectDataList();
                    List<TreeItem<ObjectPane>> tableObjectTreeItemList = new ArrayList<>();
                    
                    for (TableObjectData tableObjectData : tableObjectDataList) {
                        TreeItem<ObjectPane> treeItem = new ObjectTreeItem(tableObjectData, 
                                objectPaneFactory.createObjectPane(TABLE, tableObjectData));
                        tableObjectTreeItemList.add(treeItem);
                    }
                    
                    super.getChildren().setAll(tableObjectTreeItemList);                    
                    break;
                case TABLE:
                    List<AttributeObjectData> attributeObjectDataList = ((TableObjectData) value).getAttributeObjectDataList();
                    List<TreeItem<ObjectPane>> treeItemList = new ArrayList<>();
                    
                    for (AttributeObjectData attributeObjectData : attributeObjectDataList) {
                        TreeItem<ObjectPane> treeItem = new ObjectTreeItem(attributeObjectData, 
                                objectPaneFactory.createObjectPane(ATTRIBUTE, attributeObjectData));
                        treeItemList.add(treeItem);
                    }
                    
                    List<MetricObjectData> metricObjectDataList = ((TableObjectData) value).getMetricObjectDataList();
                    
                    for (MetricObjectData metricObjectData : metricObjectDataList) {
                        TreeItem<ObjectPane> treeItem = new ObjectTreeItem(metricObjectData, 
                                objectPaneFactory.createObjectPane(METRIC, metricObjectData));
                        treeItemList.add(treeItem);
                    }
                    
                    List<ColumnObjectData> columnObjectDataList = ((TableObjectData) value).getColumnObjectDataList();
                    
                    for (ColumnObjectData columnObjectData : columnObjectDataList) {
                        TreeItem<ObjectPane> treeItem = new ObjectTreeItem(columnObjectData, 
                                objectPaneFactory.createObjectPane(COLUMN, columnObjectData));
                        treeItemList.add(treeItem);
                    }
                    
                    super.getChildren().setAll(treeItemList);
                    
                    break;
                default:
                    logger.log(Level.SEVERE, "Invalid object type - {0}", value.getType());
            }
        }
        
        return super.getChildren();
    }
    
    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            
            isLeaf = (value.getType() == ATTRIBUTE
                    || value.getType() == COLUMN
                    || value.getType() == METRIC);
        }
        
        return isLeaf;
    }
}
