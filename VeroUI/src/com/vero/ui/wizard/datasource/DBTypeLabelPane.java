/**
 * 
 */
package com.vero.ui.wizard.datasource;

import static com.vero.ui.constants.UIConstants.DB_TYPE_LABEL_PANE_WIDTH;
import static com.vero.ui.constants.CSSConstants.*;

import javafx.scene.control.Label;

import com.vero.ui.common.LabelPane;
import com.vero.ui.constants.DBType;

/**
 * @author Tai Hu
 *
 */
public class DBTypeLabelPane extends LabelPane {
    private DBType dbType = null;
    
    public DBTypeLabelPane(DBType dbType) {
	this.dbType = dbType;
	
	buildUI();
    }
    
    private void buildUI() {
	getStyleClass().add(CLASS_DB_TYPE_LABEL_PANE);
	setPrefWidth(DB_TYPE_LABEL_PANE_WIDTH);
	
	Label label = new Label(dbType.getName());
	label.getStyleClass().add(CLASS_SECTION_TITLE);
	getChildren().add(label);
    }
}
