package com.vero.ui.editor;

import static com.vero.ui.constants.CSSConstants.CLASS_EDITOR_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_EDITOR_PANE_TOOL_BAR;
import static com.vero.ui.constants.CSSConstants.CLASS_SUBSECTION_TITLE;
import static com.vero.ui.constants.UIConstants.DEFAULT_EDITOR_PANE_HEIGHT;
import static com.vero.ui.constants.UIConstants.EDITOR_PANE_ICON_HEIGHT;
import static com.vero.ui.constants.UIConstants.EDITOR_PANE_ICON_WIDTH;
import static com.vero.ui.constants.UIConstants.EDITOR_TABLE_PANE_HEIGHT;
import static com.vero.ui.constants.UIConstants.EDITOR_TABLE_PANE_WIDTH;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.ImageList;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.model.UIData;
import com.vero.ui.util.UIUtils;

/**
 * Top level class for all dock/undock data editors
 * 
 * @author Tai Hu
 *
 */
public abstract class EditorPane<T extends UIData> extends BorderPane implements EventHandler<ActionEvent> {
    T data = null;
    
    Button applyButton = null;
    HBox tableContainer = null;
    
    public EditorPane(T data) {
        this.data = data;
        getStyleClass().add(CLASS_EDITOR_PANE);
        setPrefHeight(DEFAULT_EDITOR_PANE_HEIGHT);
        setMaxHeight(DEFAULT_EDITOR_PANE_HEIGHT);
        
        // Create tool bar at top
        HBox toolbar = new HBox();
        toolbar.getStyleClass().add(CLASS_EDITOR_PANE_TOOL_BAR);
        
        Label iconLabel = new Label();
        iconLabel.setPrefSize(EDITOR_PANE_ICON_WIDTH, EDITOR_PANE_ICON_HEIGHT);
        iconLabel.setMaxSize(EDITOR_PANE_ICON_WIDTH, EDITOR_PANE_ICON_HEIGHT);
        iconLabel.getStyleClass().add(UIUtils.getEditorPaneIconStyleClass(getType()));
        toolbar.getChildren().add(iconLabel);
        
        Label titleLabel = new Label(data.getType().toString());
        titleLabel.getStyleClass().add(CLASS_SUBSECTION_TITLE);
        toolbar.getChildren().add(titleLabel);
        
        tableContainer = new HBox();
        tableContainer.setFillHeight(true);
        tableContainer.setPrefSize(EDITOR_TABLE_PANE_WIDTH, EDITOR_TABLE_PANE_HEIGHT);
        toolbar.getChildren().addAll(UIUtils.createHorizontalSpaceFiller(10), tableContainer);
        
        Pane fillerPane = UIUtils.createHorizontalSpaceFiller(100);
        HBox.setHgrow(fillerPane, Priority.ALWAYS);
        toolbar.getChildren().add(fillerPane);
        
        applyButton = new Button("APPLY", new ImageView(ImageList.IMAGE_APPLY));
        applyButton.setOnAction(this);
        toolbar.getChildren().add(applyButton);
        
        setTop(toolbar);
    }
    
    public ObjectType getType() {
        return data.getType();
    }
    
    public T getData() {
        return data;
    }
    
    @Override
    public void handle(ActionEvent event) { 
        if (event.getSource() == applyButton) {
            handleApplyAction();
        }
    }    
    
    protected abstract void handleApplyAction();
}
