/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import static com.vero.ui.common.ObjectType.ATTRIBUTE;
import static com.vero.ui.common.ObjectType.COLUMN;
import static com.vero.ui.common.ObjectType.METRIC;
import static com.vero.ui.common.ObjectType.TABLE;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.model.UIData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Tai Hu
 * @param <T>
 */
public class ObjectTreeItem<T extends UIData> extends TreeItem<T> {
    private static final Logger logger = Logger.getLogger(ObjectTreeItem.class.getName());
    
    private T value = null;
    private ObjectPane objectPane = null;
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;
    private boolean isLeaf = false;
    
    public ObjectTreeItem(T value, ObjectPane objectPane) {
        super(value, objectPane);
    
        this.value = value;
        this.objectPane = objectPane;
    }
    
    @Override
    public ObservableList<TreeItem<T>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            
            ObjectPaneFactory objectPaneFactory = ObjectPaneFactory.getInstance();
            
            switch(value.getType()) {
                case DATASOURCE:
                    List<TableObjectData> tableObjectDataList = ((DatasourceObjectData) value).getTableObjectDataList();
                    List<TreeItem<TableObjectData>> treeItemList = new ArrayList<TreeItem<TableObjectData>>();
                    
                    for (TableObjectData tableObjectData : tableObjectDataList) {
                        TreeItem<TableObjectData> treeItem = new ObjectTreeItem(tableObjectData, 
                                objectPaneFactory.createObjectPane(TABLE, tableObjectData));
                        treeItemList.add(treeItem);
                    }
                    
//                    super.getChildren().setAll(treeItemList);
                    
                    break;
                case TABLE:
                    break;
                default:
                    logger.severe("Invalid object type - " + value.getType());
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
