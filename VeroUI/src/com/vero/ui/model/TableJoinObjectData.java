/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.TABLE_JOIN;

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
    private TableJoinType tableJoinType = null;
    
    public TableJoinObjectData() {
	this(new SchemaTableJoin());
    }
    
    public TableJoinObjectData(SchemaTableJoin schemaTableJoin) {
        super(schemaTableJoin);
        this.schemaTableJoin = schemaTableJoin;
        
        // init data
        tableJoinType = TableJoinType.values()[schemaTableJoin.getJoinType()];
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
        return tableJoinType;
    }

    public void setTableJoinType(TableJoinType tableJoinType) {
        this.tableJoinType = tableJoinType;
        schemaTableJoin.setJoinType(tableJoinType.ordinal());
    }    
}
