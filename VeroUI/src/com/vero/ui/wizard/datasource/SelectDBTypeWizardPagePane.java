/**
 * 
 */
package com.vero.ui.wizard.datasource;

import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardPagePane;

import static com.vero.ui.constants.WizardPageIds.*;

/**
 * @author Tai Hu
 *
 */
public class SelectDBTypeWizardPagePane extends WizardPagePane<DatasourceWizardData> {

    public SelectDBTypeWizardPagePane(DatasourceWizardData wizardData) {
        super(wizardData);
    }

    @Override
    public String getWizardId() {
        return ID_SELECT_DB_TYPE;
    }

    @Override
    public String next() throws WizardException {
        return ID_DB_PARAMS;
    }

    @Override
    public String back() throws WizardException {
        return null;
    }

    @Override
    public void finish() throws WizardException {
        
    }

    @Override
    public void cancel() {        
    }

    @Override
    public boolean isFirst() {
        return true;
    }

    @Override
    public boolean canFinish() {
        return false;
    }

}
