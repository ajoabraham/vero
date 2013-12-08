/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import static com.vero.ui.common.ObjectType.DATASOURCE;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.test.TestDataGenerator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * @author Tai Hu
 */
public class ObjectTreeView extends TreeView<ObjectPane> {
    public ObjectTreeView() {
        buildUI();
    }
    
    private void buildUI() {
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setEditable(false);
        setCellFactory(new ObjectTreeCellFactory());
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        DatasourceObjectData rootData = TestDataGenerator.generateDatasource("DS");
        TreeItem<ObjectPane> root = new ObjectTreeItem<>(rootData, 
                ObjectPaneFactory.getInstance().createObjectPane(DATASOURCE, rootData));
        setShowRoot(true);
        setRoot(root);
        root.setExpanded(false);
    }
}
