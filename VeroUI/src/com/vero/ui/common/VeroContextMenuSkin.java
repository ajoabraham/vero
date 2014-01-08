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
        dropShadow.setBlurType(THREE_PASS_BOX);
        dropShadow.setColor(Color.GREY);
        dropShadow.setSpread(0.5);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        
        getNode().setEffect(dropShadow);
    }
}
