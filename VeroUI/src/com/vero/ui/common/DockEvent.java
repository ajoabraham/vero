/**
 * 
 */
package com.vero.ui.common;

import com.vero.ui.constants.DockEventType;
import com.vero.ui.model.UIData;


/**
 * @author Tai Hu
 *
 */
public class DockEvent {
    private DockEventType type = null;
    private UIData data = null;
    
    public DockEvent(DockEventType type, UIData data) {
        this.type = type;
        this.data = data;
    }

    /**
     * @return the type
     */
    public DockEventType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(DockEventType type) {
        this.type = type;
    }

    /**
     * @return the data
     */
    public UIData getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(UIData data) {
        this.data = data;
    }
}
