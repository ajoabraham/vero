/**
 * 
 */
package com.vero.ui.event;

import javafx.event.EventType;

/**
 * @author Tai Hu
 *
 */
public final class EventFactory {

    private EventFactory() {
    }
    
    public static DatasourceEvent createDatasourceEvent(EventType<DatasourceEvent> eventType, Object value) {
        return new DatasourceEvent(eventType, value);
    }
    
    public static ReportEvent createReportEvent(EventType<ReportEvent> eventType, Object value) {
        return new ReportEvent(eventType, value);
    }
}
