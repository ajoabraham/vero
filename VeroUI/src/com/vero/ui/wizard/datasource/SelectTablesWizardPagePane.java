/**
 * 
 */
package com.vero.ui.wizard.datasource;

import static com.vero.ui.constants.CSSConstants.CLASS_CONTENT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_INSTRUCTION_TEXT;
import static com.vero.ui.constants.CSSConstants.CLASS_TABLE_LIST_SCROLL_PANE;
import static com.vero.ui.constants.WizardPageIds.ID_DB_PARAMS;
import static com.vero.ui.constants.WizardPageIds.ID_SELECT_TABLES;
import static com.vero.ui.constants.WizardPageIds.ID_UPDATE_TABLE_STATS;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import com.vero.ui.common.LabelPaneFactory;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.service.DatasourceImportService;
import com.vero.ui.service.ServiceException;
import com.vero.ui.service.ServiceManager;
import com.vero.ui.util.UIUtils;
import com.vero.ui.wizard.WizardException;
import com.vero.ui.wizard.WizardPagePane;

/**
 * @author Tai Hu
 * 
 */
public class SelectTablesWizardPagePane extends WizardPagePane<DatasourceWizardData> implements EventHandler<MouseEvent> {
    private static final Logger logger = Logger.getLogger(SelectTablesWizardPagePane.class.getName());
    
    private GridPane contentPane = null;
    private final List<ListedTableLabelPane> allTablePanes = new ArrayList<ListedTableLabelPane>();
    private final List<ListedTableLabelPane> selectedTablePanes = new ArrayList<ListedTableLabelPane>();

    private Button selectAllButton = null;
    private Button deselectAllButton = null;

    public SelectTablesWizardPagePane(DatasourceWizardData wizardData) {
	super(wizardData);
	buildUI();
    }

    @Override
    public void init() throws WizardException {
	contentPane.getChildren().clear();
	allTablePanes.clear();
	selectedTablePanes.clear();

	int rowIndex = 0;
	int columnIndex = 0;

	for (TableObjectData tableData : wizardData.getAllTableObjectData()) {
	    ListedTableLabelPane tableLabelPane = LabelPaneFactory.createListedTableLabelPane(tableData, false);
	    
	    tableLabelPane.setOnMouseClicked(this);
	    tableLabelPane.setOnMouseEntered(this);
	    tableLabelPane.setOnMouseExited(this);
	    contentPane.add(tableLabelPane, columnIndex, columnIndex < 1 ? rowIndex : rowIndex++);
	    columnIndex = ++columnIndex == 2 ? 0 : columnIndex;

	    allTablePanes.add(tableLabelPane);
	    
	    if (wizardData.getData().getTableObjectDataList().contains(tableData)) {
		tableLabelPane.setSelected(true);
		selectedTablePanes.add(tableLabelPane);
	    }
	}
    }

    private void buildUI() {
	// Instruction
	Label instructionLabel = new Label("Select import tables");
	BorderPane.setAlignment(instructionLabel, Pos.CENTER);
	instructionLabel.setPrefHeight(65);
	instructionLabel.getStyleClass().add(CLASS_INSTRUCTION_TEXT);
	setTop(instructionLabel);
	ScrollPane scrollPane = new ScrollPane();
	scrollPane.getStyleClass().add(CLASS_TABLE_LIST_SCROLL_PANE);
	contentPane = new GridPane();
	contentPane.getStyleClass().add(CLASS_CONTENT_PANE);
	scrollPane.setContent(contentPane);
	setCenter(scrollPane);

	HBox buttonPane = UIUtils.createDefaultButtonPane();
	selectAllButton = UIUtils.createDefaultButton("Select All", null, 100);
	selectAllButton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
            public void handle(ActionEvent event) {
	        for (ListedTableLabelPane tablePane : allTablePanes) {
	            if (!tablePane.isSelected()) {
	        	tablePane.setSelected(true);
	        	selectedTablePanes.add(tablePane);
	            }
	        }
            }	    
	});
	deselectAllButton = UIUtils.createDefaultButton("Deselect All", null, 100);
	deselectAllButton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
            public void handle(ActionEvent event) {
		for (ListedTableLabelPane tablePane : allTablePanes) {
		    if (tablePane.isSelected()) {
			tablePane.setSelected(false);
			selectedTablePanes.remove(tablePane);
		    }
		}
            }	    
	});
	
	buttonPane.getChildren().addAll(selectAllButton, deselectAllButton);
	setBottom(buttonPane);
    }

    @Override
    public String getWizardId() {
	return ID_SELECT_TABLES;
    }

    @Override
    public String next() throws WizardException {
	if (selectedTablePanes.size() == 0) {
	    throw new WizardException("Please select at least one table.");
	}
	
	wizardData.getData().getTableObjectDataList().clear();
	for (ListedTableLabelPane tablePane : selectedTablePanes) {
	    wizardData.getData().addTableObjectData(tablePane.getTableData());
	}
	
	try {
	    DatasourceImportService service = ServiceManager.getDatasourceImportService();
	    service.updateTableStats(wizardData.getData(), wizardData.getData().getTableObjectDataList());
        }
        catch (ServiceException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new WizardException(e.getMessage());
        }
	
	return ID_UPDATE_TABLE_STATS;
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
	return false;
    }

    @Override
    public void handle(MouseEvent e) {
	if (e.getEventType() == MouseEvent.MOUSE_CLICKED && e.getButton() == MouseButton.PRIMARY
	        && e.getClickCount() == 1) {
	    handleMouseClickedEvent((ListedTableLabelPane) e.getSource());
	}
	else if (!selectedTablePanes.contains(e.getSource())) {
	    if (e.getEventType() == MouseEvent.MOUSE_ENTERED) {
		((ListedTableLabelPane) e.getSource()).enableHighlight(true);
	    }
	    else if (e.getEventType() == MouseEvent.MOUSE_EXITED) {
		((ListedTableLabelPane) e.getSource()).enableHighlight(false);
	    }
	}
    }

    /**
     * @param source
     */
    private void handleMouseClickedEvent(ListedTableLabelPane source) {
	if (selectedTablePanes.contains(source)) {
	    source.setSelected(false);
	    selectedTablePanes.remove(source);
	}
	else {
	    source.setSelected(true);
	    selectedTablePanes.add(source);
	}
    }
}