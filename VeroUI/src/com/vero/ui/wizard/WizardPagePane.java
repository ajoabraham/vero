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
    
    public WizardPagePane() {
	
    }
    
    public T getWizardData() {
	return wizardData;
    }
    
    public abstract String getWizardId();
    public abstract void init(T wizardData);
    public abstract String next();
    public abstract String back();
    public abstract void finish();
    public abstract void cancel();
    public abstract boolean isFirst();
    public abstract boolean canFinish();
}
