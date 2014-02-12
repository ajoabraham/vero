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
    
    public EditorTableLabelPane(TableObjectData data) {
        this.data = data;        
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(CLASS_EDITOR_TABLE_LABEL_PANE);
        setPrefSize(EDITOR_TABLE_PANE_WIDTH, EDITOR_TABLE_PANE_HEIGHT);
        ImageView tableImageView = new ImageView(data.getTableType().getImage());
        getChildren().add(tableImageView);

        Label label = new Label(data.getName());
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);
    }
}
