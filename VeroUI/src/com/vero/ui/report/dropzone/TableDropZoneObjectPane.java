package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_LABEL;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.model.TableObjectData;
import com.vero.ui.report.ReportPane;

/**
 * 
 * @author Tai Hu
 *
 */
public class TableDropZoneObjectPane extends DropZoneObjectPane<TableObjectData> {
    
    public TableDropZoneObjectPane(ReportPane reportPane, TableObjectData data) {
        super(reportPane, data);
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(getType().getStyleClass());
        
        ImageView tableImageView = new ImageView(data.getTableType().getImage());
        getChildren().add(tableImageView);

        Label label = new Label();
        label.textProperty().bind(data.nameProperty().concat(" ").concat(data.aliasProperty()));        
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);
    }
}
