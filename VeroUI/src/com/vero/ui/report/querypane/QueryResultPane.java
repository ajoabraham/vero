/**
 * 
 */
package com.vero.ui.report.querypane;

import static com.vero.ui.constants.CSSConstants.CLASS_SECTION_TITLE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import com.vero.ui.util.UIUtils;

/**
 * @author Tai Hu
 *
 */
public class QueryResultPane extends BorderPane implements EventHandler<ActionEvent> {
    private Button closeButton = null;
    private List<Map<String, Object>> data = null;
    private Stage owner = null;
    
    public QueryResultPane(Stage owner, List<Map<String, Object>> data) {
	this.owner = owner;
	this.data = data;
	buildUI();
    }
    
    private void buildUI() {
	setStyle("-fx-background-color: -fx-main-background-color;");
	Label label = new Label("Query Result");
	label.setPrefHeight(45);
	label.getStyleClass().add(CLASS_SECTION_TITLE);
	BorderPane.setAlignment(label, Pos.CENTER);
	
	setTop(label);
	setCenter(getResultTablePane());
	setBottom(getButtonPane());
    }
    
    private Pane getButtonPane() {
	HBox hbox = new HBox();
	hbox.setAlignment(Pos.CENTER);
	hbox.setPrefHeight(45);
	
	closeButton = UIUtils.createDefaultButton("Close");
	closeButton.setOnAction(this);
	hbox.getChildren().add(closeButton);
	
	return hbox;
    }

    private Parent getResultTablePane() {
	Set<String> columnNames = data.get(0).keySet();
	List<TableColumn<Map<String, Object>, Object>> tableColumns = new ArrayList<TableColumn<Map<String, Object>, Object>>();
	for (Iterator<String> i = columnNames.iterator(); i.hasNext();) {
	    String columnName = i.next();
	    TableColumn<Map<String, Object>, Object> tableColumn = new TableColumn<Map<String, Object>, Object>(columnName);
	    tableColumn.setCellValueFactory(new MapValueFactory(columnName));
	    tableColumns.add(tableColumn);
	}
	
	
	TableView<Map<String, Object>> tableView = new TableView<Map<String, Object>>(FXCollections.observableArrayList(data));
	tableView.getColumns().addAll(tableColumns);
	tableView.setStyle("-fx-background-color: transparent; -fx-text-fill: -fx-default-text-color;");
	return tableView;
    }
    
    
    @Override
    public void handle(ActionEvent event) {
	if (event.getSource() == closeButton) {
	    owner.close();
	}
    }
}
