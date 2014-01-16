/**
 * 
 */
package com.vero.ui.wizard;

import java.util.HashMap;
import java.util.Map;

import com.vero.ui.common.PopupDialog;
import com.vero.ui.common.UIManager;

/**
 * @author Tai Hu
 *
 */
public final class WizardBuilder<T extends WizardData> {
    private Map<String, WizardPagePane<T>> wizardPages = null;
    private String firstPage = null;
    private double width;
    private double height;
    
    private WizardBuilder() {
        wizardPages = new HashMap<String, WizardPagePane<T>>();
    }

    public static <T extends WizardData> WizardBuilder<T> create(Class<T> wizardDataClass) {
        return new WizardBuilder<T>();
    }    
    
    public WizardBuilder<T> wizardPage(WizardPagePane<T> wizardPage) {
        wizardPages.put(wizardPage.getWizardId(), wizardPage);
        return this;
    }
    
    public WizardBuilder<T> firstPage(String wizardPageId) {
        this.firstPage = wizardPageId;
        return this;
    }
    
    public WizardBuilder<T> width(double width) {
        this.width = width;
        return this;
    }
    
    public WizardBuilder<T> height(double height) {
        this.height = height;
        return this;
    }
    
    public PopupDialog create() throws WizardException {
        PopupDialog popupDialog = new PopupDialog(UIManager.getInstance().getPrimaryStage());
        
        WizardPane<T> wizardPane = new WizardPane<T>(popupDialog, wizardPages, firstPage);
        popupDialog.createScene(wizardPane, width, height);
        
        return popupDialog;
    }
}
