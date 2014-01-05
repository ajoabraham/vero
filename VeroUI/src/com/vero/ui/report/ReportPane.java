/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import javafx.scene.layout.BorderPane;

import com.vero.ui.editor.DockContainer;
import com.vero.ui.editor.DockedEditorPane;
import com.vero.ui.report.dropzone.DropZonePane;
import com.vero.ui.report.querypane.QueryPane;

/**
 *
 * @author Tai Hu
 */
public class ReportPane extends BorderPane implements DockContainer {
    
    public ReportPane() {
        buildUI();
    }
    
    private void buildUI() {
        setLeft(new DropZonePane(this));
        setCenter(new QueryPane());
    }

    @Override
    public void showDockedPane(DockedEditorPane dockedEditorPane) {
        setBottom(dockedEditorPane);
    }
}
