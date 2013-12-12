/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.object;

import com.vero.ui.model.UIData;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Tai Hu
 */
public interface DraggableObject {
    /**
     * This data will be put on DragAndDropDataManager cache to share with
     * drop target.
     * 
     * @return Data to share with drop target 
     */
    public UIData getTransferData();
    public Node getDragSource();
    public void handleDragDetectedEvent(MouseEvent event);
    public void handleDragDoneEvent(DragEvent event);
}
