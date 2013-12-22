package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_DOCKED_EDITOR_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_EDITOR_PANE_TOOL_BAR;
import static com.vero.ui.constants.CSSConstants.CLASS_SUBSECTION_TITLE;
import static com.vero.ui.constants.UIConstants.DOCKED_EDITOR_PANE_HEIGHT;
import static com.vero.ui.constants.UIConstants.DOCKED_EDITOR_PANE_WIDTH;
import static com.vero.ui.constants.UIConstants.EDITOR_PANE_ICON_HEIGHT;
import static com.vero.ui.constants.UIConstants.EDITOR_PANE_ICON_WIDTH;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.ImageList;
import com.vero.ui.util.UIUtils;
/**
 * 
 * @author Tai Hu
 *
 */
public abstract class DockedEditorPane extends EditorPane implements EventHandler<ActionEvent> {
    Button undockButton = null;
    Button okButton = null;
    Button cancelButton = null;
    
    public DockedEditorPane() {
        getStyleClass().add(CLASS_DOCKED_EDITOR_PANE);
        setPrefSize(DOCKED_EDITOR_PANE_WIDTH, DOCKED_EDITOR_PANE_HEIGHT);
        setMaxSize(DOCKED_EDITOR_PANE_WIDTH, DOCKED_EDITOR_PANE_HEIGHT);
        
        // Create tool bar at top
        HBox toolbar = new HBox();
        toolbar.getStyleClass().add(CLASS_EDITOR_PANE_TOOL_BAR);
        
        Label iconLabel = new Label();
        iconLabel.setPrefSize(EDITOR_PANE_ICON_WIDTH, EDITOR_PANE_ICON_HEIGHT);
        iconLabel.setMaxSize(EDITOR_PANE_ICON_WIDTH, EDITOR_PANE_ICON_HEIGHT);
        iconLabel.getStyleClass().add(UIUtils.getEditorPaneIconStyleClass(getType()));
        toolbar.getChildren().add(iconLabel);
        
        Label titleLabel = new Label("New Metric");
        titleLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        toolbar.getChildren().add(titleLabel);
        
        Pane fillerPane = UIUtils.createHorizontalSpaceFiller(100);
        HBox.setHgrow(fillerPane, Priority.ALWAYS);
        toolbar.getChildren().add(fillerPane);
        
        undockButton = new Button("UNDOCK", new ImageView(ImageList.IMAGE_UNDOCK));
        undockButton.setOnAction(this);
        okButton = new Button("OK", new ImageView(ImageList.IMAGE_OK));
        okButton.setOnAction(this);
        cancelButton = new Button("CANCEL", new ImageView(ImageList.IMAGE_CANCEL));
        cancelButton.setOnAction(this);
        toolbar.getChildren().addAll(undockButton, okButton, cancelButton);
        
        setTop(toolbar);
    }

    @Override
    public void handle(ActionEvent event) { 
        if (event.getSource() == undockButton) {
            
        }
        else if (event.getSource() == okButton) {
            
        }
        else if (event.getSource() == cancelButton) {
            handleCancelEvent();
        }
    }    
    
    protected void handleCancelEvent() {
        EditorPaneManager.getInstance().hideDockedMetricEditorPane();
    }
}
