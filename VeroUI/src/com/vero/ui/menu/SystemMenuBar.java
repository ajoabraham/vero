/**
 * 
 */
package com.vero.ui.menu;

import static com.vero.ui.constants.CSSConstants.ID_SYSTEM_MENU_BAR;

import com.vero.ui.common.ConfirmationDialogs;
import com.vero.ui.common.PopupDialog;
import com.vero.ui.common.UIManager;
import com.vero.ui.constants.ImageList;
import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardFactory;
import com.vero.ui.wizard.datasource.DatasourceWizardData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

/**
 * @author Tai Hu
 *
 */
public class SystemMenuBar extends MenuBar implements EventHandler<ActionEvent> {
    private Menu fileMenu = null;
    private MenuItem newDatasourceMenuItem = null;
    
    public SystemMenuBar() {
	buildUI();
    }
    
    private void buildUI() {
        setId(ID_SYSTEM_MENU_BAR);
        
	fileMenu = new Menu("File");
	newDatasourceMenuItem = new MenuItem("New Datasource", new ImageView(ImageList.IMAGE_DATASOURCE_OBJECT));
	newDatasourceMenuItem.setOnAction(this);
	fileMenu.getItems().add(newDatasourceMenuItem);
	getMenus().add(fileMenu);
    }
    
    @Override
    public void handle(ActionEvent e) {
	if (e.getSource() == newDatasourceMenuItem) {
	    handleNewDatasourceAction();
	}
    }
    
    private void handleNewDatasourceAction() {
	try {
	    DatasourceWizardData wizardData = new DatasourceWizardData();
	    PopupDialog popupDialog = WizardFactory.getInstance().createDatasourceWizard(wizardData);
	    popupDialog.show();
	}
	catch (WizardException e) {
	    ConfirmationDialogs.createErrorConfirmation(UIManager.getInstance().getPrimaryStage(), e.getMessage())
		    .show();
	}
    }

}
