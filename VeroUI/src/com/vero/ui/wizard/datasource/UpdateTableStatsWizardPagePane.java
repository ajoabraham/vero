/**
 * 
 */
package com.vero.ui.wizard.datasource;

import static com.vero.ui.constants.CSSConstants.CLASS_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_INSTRUCTION_TEXT;
import static com.vero.ui.constants.CSSConstants.CLASS_TABLE_LIST_SCROLL_PANE;
import static com.vero.ui.constants.WizardPageIds.ID_SELECT_TABLES;
import static com.vero.ui.constants.WizardPageIds.ID_UPDATE_TABLE_STATS;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.constants.TableType;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardPagePane;

/**
 * @author Tai Hu
 *
 */
public class UpdateTableStatsWizardPagePane extends WizardPagePane<DatasourceWizardData> implements EventHandler<MouseEvent> {    
    private GridPane contentPane = null;
            
    public UpdateTableStatsWizardPagePane(DatasourceWizardData wizardData) {
        super(wizardData);
        buildUI();
    }
    
    @Override
    public void init() throws WizardException {
        contentPane.getChildren().clear();
        
        int rowIndex = 0;
        int columnIndex = 0;
        
        for (TableObjectData tableData : wizardData.getData().getTableObjectDataList()) {
            ListedTableLabelPane tableLabelPane = LabelPaneFactory.createListedTableLabelPane(tableData, true);
            tableLabelPane.setOnMouseClicked(this);
            tableLabelPane.setOnMouseEntered(this);
            tableLabelPane.setOnMouseExited(this);
            contentPane.add(tableLabelPane, columnIndex, columnIndex < 1 ? rowIndex : rowIndex++);
            columnIndex = ++columnIndex == 2 ? 0: columnIndex;
        }
    }
    
    private void buildUI() {
        // Instruction
        Label instructionLabel = new Label("Update Table Type");
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
        return ID_UPDATE_TABLE_STATS;
    }

    @Override
    public String next() throws WizardException {
        return null;
    }

    @Override
    public String back() throws WizardException {
	for (TableObjectData tableData : wizardData.getData().getTableObjectDataList()) {
	    tableData.setTableType(TableType.UNKNOWN);
	}
	
        return ID_SELECT_TABLES;
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

    @Override
    public void handle(MouseEvent e) {
        if (e.getEventType() == MouseEvent.MOUSE_CLICKED
        	&& e.getButton() == MouseButton.SECONDARY
                && e.getClickCount() == 1) {
            handleContextMenuEvent((ListedTableLabelPane) e.getSource());
        }
        else if (e.getEventType() == MouseEvent.MOUSE_ENTERED) {
            ((ListedTableLabelPane) e.getSource()).enableHighlight(true);
        }
        else if (e.getEventType() == MouseEvent.MOUSE_EXITED) {
            ((ListedTableLabelPane) e.getSource()).enableHighlight(false);
        }
    }
    
    private void handleContextMenuEvent(ListedTableLabelPane source) {
        ContextMenu contextMenu = new ContextMenu();
        
        ToggleGroup toggleGroup = new ToggleGroup();
        for (TableType tableType : TableType.values()) {
            RadioMenuItem menuItem = new RadioMenuItem(tableType.getName(), 
                    new ImageView(tableType.getImage()));
            menuItem.setUserData(tableType);
            menuItem.setSelected(source.getTableType() == tableType);
            menuItem.setToggleGroup(toggleGroup);
            menuItem.setOnAction(source);
            contextMenu.getItems().add(menuItem);
        }
        
        contextMenu.show(source, Side.BOTTOM, 0, 0);
    }
}
