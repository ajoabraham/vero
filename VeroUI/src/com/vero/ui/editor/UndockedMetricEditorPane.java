package com.vero.ui.editor;

import static com.vero.ui.constants.ObjectType.METRIC;
import javafx.stage.Stage;

import com.vero.ui.constants.ObjectType;

/**
 * 
 * @author Tai Hu
 *
 */
public class UndockedMetricEditorPane extends UndockedEditorPane {
    public UndockedMetricEditorPane(Stage stage) {
        super(stage);
    }

    @Override
    public ObjectType getType() {
        return METRIC;
    } 
}
