/**
 * 
 */
package com.vero.ui.wizard.datasource;

import java.util.ArrayList;
import java.util.List;

import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.wizard.WizardData;

/**
 * @author Tai Hu
 *
 */
public class DatasourceWizardData extends WizardData {
    private DatasourceObjectData data = null;
    private List<TableObjectData> allTableObjectData = new ArrayList<TableObjectData>();
    private List<TableObjectData> selectedTableObjectData = new ArrayList<TableObjectData>();
    
    public DatasourceWizardData() {
        data = new DatasourceObjectData();
    }

    public DatasourceObjectData getData() {
        return data;
    }

    public void setData(DatasourceObjectData data) {
        this.data = data;
    }
    
    public List<TableObjectData> getAllTableObjectData() {
	return allTableObjectData;
    }
    
    public void setAllTableObjectData(List<TableObjectData> allTableObjectData) {
	this.allTableObjectData = allTableObjectData;
    }

    public List<TableObjectData> getSelectedTableObjectData() {
        return selectedTableObjectData;
    }

    public void setSelectedTableObjectData(List<TableObjectData> selectedTableObjectData) {
        this.selectedTableObjectData = selectedTableObjectData;
    }
}
