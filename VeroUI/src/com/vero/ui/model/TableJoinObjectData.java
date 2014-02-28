/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.TABLE_JOIN;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.vero.model.entities.SchemaTableJoin;
import com.vero.ui.constants.ObjectType;
import com.vero.ui.constants.TableJoinType;

/**
 *
 * @author Tai Hu
 */
public class TableJoinObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private SchemaTableJoin schemaTableJoin = null;
    
    private TableObjectData leftTable = null;
    private TableObjectData rightTable = null;
    private ObjectProperty<TableJoinType> tableJoinType = new SimpleObjectProperty<TableJoinType>();
    private StringProperty formula = new SimpleStringProperty();
    
    public TableJoinObjectData() {
	this(new SchemaTableJoin());
    }
    
    public TableJoinObjectData(SchemaTableJoin schemaTableJoin) {
        super(schemaTableJoin);
        this.schemaTableJoin = schemaTableJoin;
        
        // init data
        formula.set(schemaTableJoin.getJoinExpression());
        formula.addListener(new ChangeListener<String>() {

	    @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {	        
		TableJoinObjectData.this.schemaTableJoin.setJoinExpression(newValue);
	    }
            
        });
        
        tableJoinType.set(TableJoinType.values()[schemaTableJoin.getJoinType()]);
        tableJoinType.addListener(new ChangeListener<TableJoinType>() {

	    @Override
            public void changed(ObservableValue<? extends TableJoinType> observable, TableJoinType oldValue, TableJoinType newValue) {	        
		TableJoinObjectData.this.schemaTableJoin.setJoinType(newValue.ordinal());
	    }
            
        });
        
        if (schemaTableJoin.getSchemaTableLeft() != null) {
            leftTable = new TableObjectData(schemaTableJoin.getSchemaTableLeft());
        }
        
        if (schemaTableJoin.getSchemaTableRight() != null) {
            rightTable = new TableObjectData(schemaTableJoin.getSchemaTableRight());
        }
    }
    
    @Override
    public ObjectType getType() {
        return TABLE_JOIN;
    }

    public String getFormula() {
	return formula.get();
    }
    
    public void setFormula(String formula) {
	this.formula.set(formula);
    }
    
    public StringProperty formulaProperty() {
	return formula;
    }
    
    public TableObjectData getLeftTable() {
        return leftTable;
    }

    public void setLeftTable(TableObjectData leftTable) {
        this.leftTable = leftTable;
        schemaTableJoin.setSchemaTableLeft(leftTable.getSchemaTable());
    }

    public TableObjectData getRightTable() {
        return rightTable;
    }

    public void setRightTable(TableObjectData rightTable) {
        this.rightTable = rightTable;
        schemaTableJoin.setSchemaTableRight(rightTable.getSchemaTable());
    }

    public TableJoinType getTableJoinType() {
        return tableJoinType.get();
    }

    public void setTableJoinType(TableJoinType tableJoinType) {
        this.tableJoinType.set(tableJoinType);
    }    
    
    public ObjectProperty<TableJoinType> tableJoinTypeProperty() {
	return tableJoinType;
    }
}
