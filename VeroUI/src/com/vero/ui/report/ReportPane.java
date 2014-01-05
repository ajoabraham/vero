/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.layout.BorderPane;

import com.vero.ui.common.DockEvent;
import com.vero.ui.editor.DockHandler;
import com.vero.ui.model.UIData;
import com.vero.ui.report.dropzone.DropZonePane;
import com.vero.ui.report.querypane.QueryPane;

/**
 *
 * @author Tai Hu
 */
public class ReportPane extends BorderPane implements DockHandler {
    private static final Logger logger = Logger.getLogger(ReportPane.class.getName());
    
    public ReportPane() {
        buildUI();
    }
    
    private void buildUI() {
        setLeft(new DropZonePane(this));
        setCenter(new QueryPane());
    }

    @Override
    public void handle(DockEvent dockEvent) {
        switch (dockEvent.getType()) {
            case DOCK:
                handleDockEvent(dockEvent.getData());
                break;
            case UNDOCK:
                handleUndockEvent();
                break;
            case CANCEL:
                handleCancelEvent();
                break;
            default:
              logger.log(Level.SEVERE, "Invalid dock event type - {0}", dockEvent.getType());
        }
    }
    
    private void handleDockEvent(UIData data) {
        
    }
    
    private void handleUndockEvent() {
        
    }
    
    private void handleCancelEvent() {
        
    }
}
