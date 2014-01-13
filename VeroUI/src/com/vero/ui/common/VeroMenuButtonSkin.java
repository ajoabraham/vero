/**
 * 
 */
package com.vero.ui.common;

import javafx.scene.control.MenuButton;

import com.sun.javafx.scene.control.skin.MenuButtonSkin;
import static com.vero.ui.constants.CSSConstants.*;


/**
 * @author Tai Hu
 *
 */
public class VeroMenuButtonSkin extends MenuButtonSkin {

    /**
     * @param menuButton
     */
    public VeroMenuButtonSkin(MenuButton menuButton) {
	super(menuButton);
	
	if (menuButton.getItems().size() > 0
		&& !menuButton.getStyleClass().contains(CLASS_PARENT_MENU_BUTTON)) {
	    menuButton.getStyleClass().add(CLASS_PARENT_MENU_BUTTON);
	}
    }
}
