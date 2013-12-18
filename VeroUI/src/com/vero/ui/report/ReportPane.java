/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report;

import javafx.scene.layout.BorderPane;

import com.vero.ui.report.dropzone.DropZonePane;
import com.vero.ui.report.querypane.QueryPane;

/**
 *
 * @author Tai Hu
 */
public class ReportPane extends BorderPane {
    public ReportPane() {
        buildUI();
    }
    
    private void buildUI() {
        setLeft(new DropZonePane());
        setCenter(new QueryPane());
    }
}
