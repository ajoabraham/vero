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
    private TableObjectData tableData = null;
    private ImageView tableTypeImageView = null;
    
    public ListedTableLabelPane(TableObjectData tableData) {
        this.tableData = tableData;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().addAll(CLASS_OBJECT_PANE, CLASS_TABLE_OBJECT_PANE);
        setPrefWidth(DEFAULT_LABEL_PANE_WIDTH);
        
        tableTypeImageView = new ImageView(tableData.getTableType().getImage());
        getChildren().add(tableTypeImageView);

        Label label = new Label(tableData.getName());
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
}
