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
public class DockEventBuilder {
    private DockEventType type = null;
    private UIData data = null;
    
    private DockEventBuilder() {
        
    }
    
    public static DockEventBuilder create() {
        return new DockEventBuilder();
    }
    
    public DockEventBuilder type(DockEventType type) {
        this.type = type;
        return this;
    }
    
    public DockEventBuilder data(UIData data) {
        this.data = data;
        return this;
    }
    
    public DockEvent build() {
        return new DockEvent(type, data);
    }
}
