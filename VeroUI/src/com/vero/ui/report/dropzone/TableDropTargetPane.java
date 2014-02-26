/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.ObjectType.TABLE;
import javafx.collections.ListChangeListener;

import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.report.ReportPane;

/**
 *
 * @author Tai Hu
 */
public class TableDropTargetPane extends DropTargetPane implements ListChangeListener<TableObjectData> {

    public TableDropTargetPane(ReportPane reportPane, DropZonePane dropZonePane) {
        super(reportPane, dropZonePane);
    }
    
    @Override
    public ObjectType getType() {
        return TABLE;
    }

    @Override
    public void onChanged(javafx.collections.ListChangeListener.Change<? extends TableObjectData> change) {        
        while (change.next()) {
            if (change.wasAdded()) {
                TableObjectData tableObjectData = change.getAddedSubList().get(0);
                addDropZoneObjectPane(LabelPaneFactory.createDropZoneObjectPane(reportPane, tableObjectData));
            }
            else if (change.wasRemoved()) {
                TableObjectData tableObjectData = change.getRemoved().get(0);
                removeDropZoneObjectPane(tableObjectData.getId());
            }
        }
    }    
}
