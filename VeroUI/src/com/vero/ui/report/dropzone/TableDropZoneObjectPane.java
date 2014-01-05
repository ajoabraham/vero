package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_LABEL;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.ImageList;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.util.UIUtils;

/**
 * 
 * @author Tai Hu
 *
 */
public class TableDropZoneObjectPane extends DropZoneObjectPane<TableObjectData> {
    private TableObjectData data = null;
    
    public TableDropZoneObjectPane(TableObjectData data) {
        super(data);
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(UIUtils.getObjectSytleClass(getType()));
        
        ImageView tableImageView = new ImageView(ImageList.IMAGE_UNKNOWN_TABLE);
        getChildren().add(tableImageView);

        Label label = new Label(data.getName());
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
        label.getStyleClass().add(CLASS_OBJECT_LABEL);
        getChildren().add(label);
    }
}
