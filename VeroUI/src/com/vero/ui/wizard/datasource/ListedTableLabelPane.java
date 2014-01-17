/**
 * 
 */
package com.vero.ui.wizard.datasource;

import static com.vero.ui.constants.CSSConstants.*;
import static com.vero.ui.constants.UIConstants.*;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.common.LabelPane;
import com.vero.ui.constants.ImageList;
import com.vero.ui.model.TableObjectData;

/**
 * FIXME Consider to create SelectableObject interface and use
 * TableObjectPane to replace this class to display a table. 
 * 
 * @author Tai Hu
 *
 */
public class ListedTableLabelPane extends LabelPane {    
    private TableObjectData tableData = null;
    
    public ListedTableLabelPane(TableObjectData tableData) {
        this.tableData = tableData;
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().addAll(CLASS_OBJECT_PANE, CLASS_TABLE_OBJECT_PANE);
        setPrefWidth(DEFAULT_LABEL_PANE_WIDTH);
        
        ImageView tableImageView = new ImageView(ImageList.IMAGE_UNKNOWN_TABLE);
        getChildren().add(tableImageView);

        Label label = new Label(tableData.getName());
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);
    }
}
