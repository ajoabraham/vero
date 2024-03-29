/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.navigation;

import static com.vero.ui.constants.CSSConstants.ID_OBJECT_TREE_VIEW;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import com.vero.ui.common.UIDataManager;
import com.vero.ui.model.ProjectObjectData;
import com.vero.ui.service.ServiceException;

/**
 *
 * @author Tai Hu
 */
public class ObjectTreeView extends TreeView<ObjectPane> implements Observer {
    private static final Logger logger = Logger.getLogger(ObjectTreeView.class.getName());
    
    public ObjectTreeView() {        
        try {
            buildUI();
        }
        catch (ServiceException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    private void buildUI() throws ServiceException {
        setId(ID_OBJECT_TREE_VIEW);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setEditable(false);
        setCellFactory(new ObjectTreeCellFactory());
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
//        ProjectObjectData rootData = new ProjectObjectData();
//        rootData.setDatasourceObjectDataList(TestDataGenerator.generateDatasourceList("DS"));
        buildTree();
    }

    private void buildTree() throws ServiceException {
        ProjectObjectData projectObjectData = UIDataManager.getInstance().getProjectObjectData();        
        TreeItem<ObjectPane> root = new ObjectTreeItem<>(projectObjectData, null);
        setShowRoot(false);
        setRoot(root);
        root.setExpanded(true);
    }

    @Override
    public void update(Observable o, Object arg) {     
        // FIXME TH 01/30/2014, in beta, a better handling of event is needed instead of
        // refreshing the whole tree.
        try {            
            buildTree();
        }
        catch (ServiceException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
