/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vero.ui;

import com.vero.ui.common.ImageList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;

/**
 *
 * @author Tai Hu
 */
public class VeroMenuBar extends MenuBar {

    private Menu newMenu = null;
    private Menu saveMenu = null;
    private Menu openMenu = null;
    private Menu runMenu = null;
    private Menu addBlockMenu = null;
    private Menu deleteMenu = null;

    public VeroMenuBar() {
        buildUI();
    }

    private void buildUI() {
        newMenu = new Menu("NEW", new ImageView(ImageList.IMAGE_NEW));
        saveMenu = new Menu("SAVE", new ImageView(ImageList.IMAGE_SAVE));
        openMenu = new Menu("OPEN", new ImageView(ImageList.IMAGE_OPEN));
        runMenu = new Menu("RUN", new ImageView(ImageList.IMAGE_RUN));
        addBlockMenu = new Menu("ADD BLOCK", new ImageView(ImageList.IMAGE_ADD_BLOCK));
        deleteMenu = new Menu("DELETE", new ImageView(ImageList.IMAGE_DELETE));
        
        getMenus().addAll(newMenu, saveMenu, openMenu, runMenu, addBlockMenu, deleteMenu);
    }
}
