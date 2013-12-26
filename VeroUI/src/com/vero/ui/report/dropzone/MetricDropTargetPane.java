/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.ObjectType.METRIC;
import javafx.scene.input.DragEvent;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.editor.EditorPaneManager;
import com.vero.ui.model.UIData;

/**
 *
 * @author Tai Hu
 */
public class MetricDropTargetPane extends DropTargetPane {
    public MetricDropTargetPane() {
        
    }
    
    @Override
    public ObjectType getType() {
        return METRIC;
    }

    @Override
    public void handleDragDroppedEvent(DragEvent event, UIData transferData) {
        super.handleDragDroppedEvent(event, transferData);
        
        EditorPaneManager.getInstance().showDockedMetricEditorPane();
    }
}