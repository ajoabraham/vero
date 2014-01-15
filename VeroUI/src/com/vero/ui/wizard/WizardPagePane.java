/**
 * 
 */
package com.vero.ui.wizard;

import static com.vero.ui.constants.CSSConstants.CLASS_WIZARD_PAGE_PANE;
import javafx.scene.layout.BorderPane;

/**
 * @author Tai Hu
 *
 */
public abstract class WizardPagePane<T extends WizardData> extends BorderPane {
    protected T wizardData = null;
    
    public WizardPagePane(T wizardData) {
	this.wizardData = wizardData;
	getStyleClass().add(CLASS_WIZARD_PAGE_PANE);
    }
    
    public T getWizardData() {
	return wizardData;
    }
    
    public abstract String getWizardId();
    public abstract String next() throws WizardException;
    public abstract String back() throws WizardException;
    public abstract void finish() throws WizardException;
    public abstract void cancel();
    public abstract boolean isFirst();
    public abstract boolean canFinish();
}
