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
public class ReportEvent extends Event {
    public static final EventType<ReportEvent> REPORT_SAVED = new EventType<ReportEvent>();
    
    private Object value = null;

    public ReportEvent(EventType<? extends Event> eventType) {
        this(eventType, null);
    }

    public ReportEvent(EventType<? extends Event> eventType, Object value) {
        super(eventType);
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
