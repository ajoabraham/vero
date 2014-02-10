/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import com.vero.ui.constants.ObjectType;
import com.vero.ui.report.querypane.QueryPane;

import static com.vero.ui.constants.ObjectType.ATTRIBUTE;

/**
 *
 * @author Tai Hu
 */
public class AttributeDropTargetPane extends DropTargetPane {    
    public AttributeDropTargetPane(QueryPane queryPane) {
        super(queryPane);
    }

    @Override
    public ObjectType getType() {
        return ATTRIBUTE;
    }
}
