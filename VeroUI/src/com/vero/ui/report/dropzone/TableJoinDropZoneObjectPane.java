/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.report.dropzone;

import static com.vero.ui.constants.CSSConstants.CLASS_OBJECT_PANE;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import com.vero.ui.constants.TableJoinType;
import com.vero.ui.model.TableJoinObjectData;
import com.vero.ui.report.ReportPane;

/**
 *
 * @author Tai Hu
 */
public class TableJoinDropZoneObjectPane extends DropZoneObjectPane<TableJoinObjectData> {
    private ImageView tableJoinImageView = null;
    
    public TableJoinDropZoneObjectPane(ReportPane reportPane, TableJoinObjectData data) {
        super(reportPane, data);
        buildUI();
    }
    
    private void buildUI() {
        getStyleClass().add(getType().getStyleClass());
        // Table join pane should not have a border.
        getStyleClass().remove(CLASS_OBJECT_PANE);
        
        Label leftTableLabel = new Label();
        leftTableLabel.setGraphic(new ImageView(data.getLeftTable().getTableType().getImage()));
        leftTableLabel.textProperty().bind(data.getLeftTable().aliasProperty());
        leftTableLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(leftTableLabel, Priority.ALWAYS);
        getChildren().add(leftTableLabel);

        tableJoinImageView = new ImageView(data.getTableJoinType().getImage());
        tableJoinImageView.setOnMouseClicked(this);
        getChildren().add(tableJoinImageView);

        Label rightTableLabel = new Label();
        rightTableLabel.setGraphic(new ImageView(data.getRightTable().getTableType().getImage()));
        rightTableLabel.textProperty().bind(data.getRightTable().aliasProperty());
        rightTableLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(rightTableLabel, Priority.ALWAYS);
        getChildren().add(rightTableLabel);
    }
    
    @Override
    public void handle(MouseEvent event) {
        if (event.getClickCount() == 1) {
            TableJoinType nextType = data.getTableJoinType().next();
            tableJoinImageView.setImage(nextType.getImage());
            data.setTableJoinType(nextType);
        }
    }
}