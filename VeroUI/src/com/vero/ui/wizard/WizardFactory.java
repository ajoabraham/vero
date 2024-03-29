/**
 * 
 */
package com.vero.ui.wizard;

import com.vero.ui.common.PopupDialog;
import com.vero.ui.wizard.datasource.DBParamsWizardPagePane;
import com.vero.ui.wizard.datasource.DatasourceWizardData;
import com.vero.ui.wizard.datasource.SelectDBTypeWizardPagePane;
import com.vero.ui.wizard.datasource.SelectTablesWizardPagePane;
import com.vero.ui.wizard.datasource.UpdateTableStatsWizardPagePane;

import static com.vero.ui.constants.UIConstants.*;
import static com.vero.ui.constants.WizardPageIds.*;

/**
 * @author Tai Hu
 *
 */
public final class WizardFactory {
    private static WizardFactory INSTANCE = null;
    
    private WizardFactory() {
    }

    public static WizardFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WizardFactory();
        }
        
        return INSTANCE;
    }
    
    public PopupDialog createDatasourceWizard(DatasourceWizardData wizardData) throws WizardException {
        return WizardBuilder.create(DatasourceWizardData.class)
                            .wizardPage(new SelectDBTypeWizardPagePane(wizardData))
                            .wizardPage(new DBParamsWizardPagePane(wizardData))
                            .wizardPage(new SelectTablesWizardPagePane(wizardData))
                            .wizardPage(new UpdateTableStatsWizardPagePane(wizardData))
                            .firstPage(ID_SELECT_DB_TYPE)
                            .width(DATASOURCE_WIZARD_WIDTH)
                            .height(DATASOURCE_WIZARD_HEIGHT)
                            .create();
    }
}
