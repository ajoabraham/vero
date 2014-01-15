/**
 * 
 */
package com.vero.ui.wizard.datasource;

import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.wizard.WizardData;

/**
 * @author Tai Hu
 *
 */
public class DatasourceWizardData extends WizardData {
    private DatasourceObjectData data = null;
    
    public DatasourceWizardData() {
        data = new DatasourceObjectData();
    }

    public DatasourceObjectData getData() {
        return data;
    }

    public void setData(DatasourceObjectData data) {
        this.data = data;
    }   
}
