/**
 * 
 */
package com.vero.ui.common;

import javafx.scene.control.ContextMenu;
import static javafx.scene.effect.BlurType.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import com.sun.javafx.scene.control.skin.ContextMenuSkin;

/**
 * @author Tai Hu
 *
 */
public class VeroContextMenuSkin extends ContextMenuSkin {

    /**
     * @param contextMenu
     */
    public VeroContextMenuSkin(ContextMenu contextMenu) {
        super(contextMenu);
    
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(GAUSSIAN);
        dropShadow.setColor(Color.BLACK);
        dropShadow.setSpread(0.1);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        dropShadow.setRadius(3);
        
        getNode().setEffect(dropShadow);
    }
}
