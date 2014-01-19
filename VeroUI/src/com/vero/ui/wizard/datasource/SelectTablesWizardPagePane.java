/**
 * 
 */
package com.vero.ui.wizard.datasource;

import static com.vero.ui.constants.CSSConstants.CLASS_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_INSTRUCTION_TEXT;
import static com.vero.ui.constants.CSSConstants.CLASS_TABLE_LIST_SCROLL_PANE;
import static com.vero.ui.constants.WizardPageIds.ID_DB_PARAMS;
import static com.vero.ui.constants.WizardPageIds.ID_SELECT_TABLES;

import java.util.ArrayList;
import java.util.List;

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
public class SelectTablesWizardPagePane extends WizardPagePane<DatasourceWizardData> implements EventHandler<MouseEvent> {
    private static final String HIGHLIGHTED_STYLE = "-fx-border-color: -fx-button-hover-border-dark-color;"
            + "-fx-border-radius: 3;";
    private static final String SELECTED_STYLE = "-fx-border-color: -fx-button-hover-border-dark-color;"
            + "-fx-border-radius: 3;" + "-fx-border-size: 3";
    
    private GridPane contentPane = null;
    private List<ListedTableLabelPane> selectedTablePanes = new ArrayList<ListedTableLabelPane>();
    
    public SelectTablesWizardPagePane(DatasourceWizardData wizardData) {
        super(wizardData);
        buildUI();
    }
    
    @Override
    public void init() throws WizardException {
        contentPane.getChildren().clear();
        selectedTablePanes.clear();
        
        int rowIndex = 0;
        int columnIndex = 0;
        
        for (TableObjectData tableData : wizardData.getData().getTableObjectDataList()) {
            ListedTableLabelPane tableLabelPane = LabelPaneFactory.createListedTableLabelPane(tableData);
            tableLabelPane.setOnMouseClicked(this);
            tableLabelPane.setOnMouseEntered(this);
            tableLabelPane.setOnMouseExited(this);
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

    @Override
    public void handle(MouseEvent e) {
        if (e.getEventType() == MouseEvent.MOUSE_CLICKED
                && e.getButton() == MouseButton.PRIMARY
                && e.getClickCount() == 1) {
            handleMouseClickedEvent((ListedTableLabelPane) e.getSource());
        }  
        else if (e.getEventType() == MouseEvent.MOUSE_CLICKED
                && e.getButton() == MouseButton.SECONDARY
                && e.getClickCount() == 1) {
            handleContextMenuEvent((ListedTableLabelPane) e.getSource());
        }
        else if (!selectedTablePanes.contains(e.getSource())) {
            if (e.getEventType() == MouseEvent.MOUSE_ENTERED) {
                handleMouseEnteredEvent((ListedTableLabelPane) e.getSource());
            }
            else if (e.getEventType() == MouseEvent.MOUSE_EXITED) {
                handleMouseExitedEvent((ListedTableLabelPane) e.getSource());
            }
        }
    }

    /**
     * @param source
     */
    private void handleMouseExitedEvent(ListedTableLabelPane source) {
        source.setStyle(null);
    }

    /**
     * @param source
     */
    private void handleMouseEnteredEvent(ListedTableLabelPane source) {
        source.setStyle(HIGHLIGHTED_STYLE);
    }

    /**
     * @param source
     */
    private void handleMouseClickedEvent(ListedTableLabelPane source) {
        if (selectedTablePanes.contains(source)) {
            source.setStyle(null);
            selectedTablePanes.remove(source);
        }
        else {
            source.setStyle(SELECTED_STYLE);
            selectedTablePanes.add(source);
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