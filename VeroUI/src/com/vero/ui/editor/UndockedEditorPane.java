package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_UNDOCKED_EDITOR_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_EDITOR_PANE_TOOL_BAR;
import static com.vero.ui.constants.CSSConstants.CLASS_SUBSECTION_TITLE;
import static com.vero.ui.constants.UIConstants.UNDOCKED_EDITOR_PANE_HEIGHT;
import static com.vero.ui.constants.UIConstants.UNDOCKED_EDITOR_PANE_WIDTH;
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
import javafx.stage.Stage;

import com.vero.ui.constants.ImageList;
import com.vero.ui.util.UIUtils;

/**
 * 
 * @author Tai Hu
 *
 */
public abstract class UndockedEditorPane extends EditorPane implements EventHandler<ActionEvent> {
    Stage stage = null;
    
    Button dockButton = null;
    Button previewButton = null;
    Button okButton = null;
    Button cancelButton = null;
    
    public UndockedEditorPane(Stage stage) {
        this.stage = stage;
        getStyleClass().add(CLASS_UNDOCKED_EDITOR_PANE);
        setPrefSize(UNDOCKED_EDITOR_PANE_WIDTH, UNDOCKED_EDITOR_PANE_HEIGHT);
        setMaxSize(UNDOCKED_EDITOR_PANE_WIDTH, UNDOCKED_EDITOR_PANE_HEIGHT);
        
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
        
        dockButton = new Button("DOCK", new ImageView(ImageList.IMAGE_DOCK));
        dockButton.setOnAction(this);
        previewButton = new Button("PREVIEW", new ImageView(ImageList.IMAGE_PREVIEW));
        previewButton.setOnAction(this);
        okButton = new Button("OK", new ImageView(ImageList.IMAGE_OK));
        okButton.setOnAction(this);
        cancelButton = new Button("CANCEL", new ImageView(ImageList.IMAGE_CANCEL));
        cancelButton.setOnAction(this);
        toolbar.getChildren().addAll(dockButton, previewButton, okButton, cancelButton);
        
        setTop(toolbar);
    }

    @Override
    public void handle(ActionEvent event) { 
        if (event.getSource() == dockButton) {
            
        }
        else if (event.getSource() == okButton) {
            
        }
        else if (event.getSource() == cancelButton) {
            handleCancelEvent();
        }
    }    
    
    protected void handleCancelEvent() {
        if (stage != null) {
            stage.close();
        }
    }
    
    public Stage getStage() {
        return stage;
    }
}
