/**
 * 
 */
package com.vero.ui.common;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.vero.ui.util.UIUtils;

/**
 * @author Tai Hu
 *
 */
public class PopupDialog extends Stage {
    
    public PopupDialog(Stage owner) {
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UNDECORATED);
    }
    
    public void createScene(Pane rootPane, double width, double height) {
        Scene scene = new Scene(rootPane, width, height);
        scene.getStylesheets().add(UIUtils.getVeroCSSStyleSheet());
        setScene(scene);
    }
}
