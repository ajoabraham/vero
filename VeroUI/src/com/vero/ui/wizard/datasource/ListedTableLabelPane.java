/**
 * 
 */
package com.vero.ui.wizard.datasource;

import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_LABEL;
import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_TABLE_OBJECT_PANE;
import static com.vero.ui.constants.UIConstants.DEFAULT_LABEL_PANE_WIDTH;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.MenuItem;

import com.vero.ui.common.LabelPane;
import com.vero.ui.constants.TableType;
import com.vero.ui.model.TableObjectData;

/**
 * FIXME Consider to create SelectableObject interface and use
 * TableObjectPane to replace this class to display a table. 
 * 
 * @author Tai Hu
 *
 */
public class ListedTableLabelPane extends LabelPane implements EventHandler<ActionEvent> {  
    private static final String HIGHLIGHTED_STYLE = "-fx-border-color: -fx-button-hover-border-dark-color;"
            + "-fx-border-radius: 3;";
    private static final String SELECTED_STYLE = "-fx-border-color: -fx-button-hover-border-dark-color;"
            + "-fx-border-radius: 3;" + "-fx-border-size: 3";
    
    private TableObjectData tableData = null;
    private ImageView tableTypeImageView = null;
    private boolean isSelected = true;
    private boolean showStats = false;
    
    public ListedTableLabelPane(TableObjectData tableData, boolean showStats) {
        this.tableData = tableData;
        this.showStats = showStats;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().addAll(CLASS_OBJECT_PANE, CLASS_TABLE_OBJECT_PANE);
        setPrefWidth(DEFAULT_LABEL_PANE_WIDTH);
        
        tableTypeImageView = new ImageView(tableData.getTableType().getImage());
        getChildren().add(tableTypeImageView);

        Label label = new Label(tableData.getName() + (showStats ? " (" + tableData.getRowCount() + ")" : ""));
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);
    }
    
    public TableType getTableType() {
        return tableData.getTableType();
    }

    @Override
    public void handle(ActionEvent e) {
        TableType selectedTableType = (TableType) ((MenuItem)e.getSource()).getUserData();
        
        if (selectedTableType != tableData.getTableType()) {
            tableTypeImageView.setImage(selectedTableType.getImage());
            tableData.setTableType(selectedTableType);
        }
    }
    
    public void enableHighlight(boolean enable) {
	setStyle(enable ? HIGHLIGHTED_STYLE : null);
    }
    
    public void setSelected(boolean isSelected) {
	setStyle(isSelected ? SELECTED_STYLE : null);
	this.isSelected = isSelected;
    }
    
    public boolean isSelected() {
	return isSelected;
    }
    
    public TableObjectData getTableData() {
	return tableData;
    }
}
