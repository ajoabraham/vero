/**
 * 
 */
package com.vero.ui.wizard.datasource;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardPagePane;

import static com.vero.ui.constants.CSSConstants.*;
import static com.vero.ui.constants.WizardPageIds.*;

/**
 * @author Tai Hu
 *
 */
public class SelectTablesWizardPagePane extends WizardPagePane<DatasourceWizardData> {
    private GridPane contentPane = null;
    
    public SelectTablesWizardPagePane(DatasourceWizardData wizardData) {
        super(wizardData);
        buildUI();
    }
    
    @Override
    public void init() throws WizardException {
        contentPane.getChildren().clear();
        
        int rowIndex = 0;
        int columnIndex = 0;
        
        for (TableObjectData tableData : wizardData.getData().getTableObjectDataList()) {
            ListedTableLabelPane tableLabelPane = LabelPaneFactory.createListedTableLabelPane(tableData);
            contentPane.add(tableLabelPane, columnIndex, columnIndex < 1 ? rowIndex : rowIndex++);
            columnIndex = ++columnIndex == 2 ? 0: columnIndex;
        }
    }
    
    private void buildUI() {
        // Instruction
        Label instructionLabel = new Label("Select import tables");
        BorderPane.setAlignment(instructionLabel, Pos.CENTER);
        instructionLabel.setPrefHeight(100);
        instructionLabel.getStyleClass().add(CLASS_INSTRUCTION_TEXT);
        setTop(instructionLabel);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add(CLASS_TABLE_LIST_SCROLL_PANE);
        contentPane = new GridPane();
        contentPane.getStyleClass().add(CLASS_CONTENT_PANE);
        scrollPane.setContent(contentPane);
        setCenter(scrollPane);
    }

    @Override
    public String getWizardId() {
        return ID_SELECT_TABLES;
    }

    @Override
    public String next() throws WizardException {
        return null;
    }

    @Override
    public String back() throws WizardException {
        return ID_DB_PARAMS;
    }

    @Override
    public void finish() throws WizardException {        
    }

    @Override
    public void cancel() {        
    }

    @Override
    public boolean isFirst() {
        return false;
    }
    
    @Override
    public boolean canFinish() {
        return true;
    }
}