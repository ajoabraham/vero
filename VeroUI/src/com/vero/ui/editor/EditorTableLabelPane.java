/**
 * 
 */
package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.*;
import static com.vero.ui.constants.UIConstants.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.common.LabelPane;
import com.vero.ui.model.TableObjectData;

/**
 * @author Tai Hu
 *
 */
public class EditorTableLabelPane extends LabelPane {
    private TableObjectData data = null;
    private Label tableLabel = null;
    private ImageView tableTypeImageView = null;
    
    public EditorTableLabelPane(TableObjectData data) {
        this.data = data;        
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_EDITOR_TABLE_LABEL_PANE);
        setPrefSize(EDITOR_TABLE_PANE_WIDTH, EDITOR_TABLE_PANE_HEIGHT);
        tableTypeImageView = new ImageView();
        tableLabel = new Label();
        tableLabel.setGraphic(tableTypeImageView);
        HBox.setHgrow(tableLabel, Priority.ALWAYS);
        tableLabel.setMaxWidth(Double.MAX_VALUE);
        tableLabel.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(tableLabel);
        
        populateData(data);
    }
    
    private void populateData(TableObjectData data) {
        tableLabel.setText(data.getName());
        tableTypeImageView.setImage(data.getTableType().getImage());
    }

    public TableObjectData getData() {
        return data;
    }

    public void setData(TableObjectData data) {
        this.data = data;
        populateData(data);
    }
}
