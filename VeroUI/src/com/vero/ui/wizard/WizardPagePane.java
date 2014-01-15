/**
 * 
 */
package com.vero.ui.wizard;

import javafx.scene.layout.Pane;

/**
 * @author Tai Hu
 *
 */
public abstract class WizardPagePane<T extends WizardData> extends Pane {
    T wizardData = null;
    
    public WizardPagePane(T wizardData) {
	this.wizardData = wizardData;
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
