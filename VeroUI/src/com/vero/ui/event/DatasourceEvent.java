/**
 * 
 */
package com.vero.ui.event;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * @author Tai Hu
 *
 */
public class DatasourceEvent extends Event {
    public static final EventType<DatasourceEvent> DATASOURCE_ADDED = new EventType<DatasourceEvent>();
    
    private Object value = null;

    public DatasourceEvent(EventType<? extends Event> eventType) {
        this(eventType, null);
    }

    public DatasourceEvent(EventType<? extends Event> eventType, Object value) {
        super(eventType);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
