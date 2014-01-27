/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.navigation;

import static com.vero.ui.constants.CSSConstants.ID_OBJECT_TREE_VIEW;
import com.vero.ui.model.ProjectObjectData;
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
        setId(ID_OBJECT_TREE_VIEW);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setEditable(false);
        setCellFactory(new ObjectTreeCellFactory());
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        ProjectObjectData rootData = new ProjectObjectData();
        rootData.setDatasourceObjectDataList(TestDataGenerator.generateDatasourceList("DS"));
        TreeItem<ObjectPane> root = new ObjectTreeItem<>(rootData, null);
        setShowRoot(false);
        setRoot(root);
        root.setExpanded(true);
    }
}
