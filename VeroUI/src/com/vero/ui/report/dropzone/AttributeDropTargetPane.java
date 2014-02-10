/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.ObjectType.ATTRIBUTE;

import com.vero.ui.constants.ObjectType;

/**
 *
 * @author Tai Hu
 */
public class AttributeDropTargetPane extends DropTargetPane {    
    public AttributeDropTargetPane(DropZonePane dropZonePane) {
        super(dropZonePane);
    }

    @Override
    public ObjectType getType() {
        return ATTRIBUTE;
    }
}
