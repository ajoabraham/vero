/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import com.vero.ui.constants.ObjectType;
import static com.vero.ui.constants.ObjectType.TABLE_JOIN;
import com.vero.ui.constants.TableJoinType;

/**
 *
 * @author Tai Hu
 */
public class TableJoinObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private TableObjectData leftTable = null;
    private ColumnObjectData leftColumn = null;
    private TableObjectData rightTable = null;
    private ColumnObjectData rightColumn = null;
    private TableJoinType tableJoinType = null;
    
    public TableJoinObjectData() {
        super(null);
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
    }

    public ColumnObjectData getLeftColumn() {
        return leftColumn;
    }

    public void setLeftColumn(ColumnObjectData leftColumn) {
        this.leftColumn = leftColumn;
    }

    public TableObjectData getRightTable() {
        return rightTable;
    }

    public void setRightTable(TableObjectData rightTable) {
        this.rightTable = rightTable;
    }

    public ColumnObjectData getRightColumn() {
        return rightColumn;
    }

    public void setRightColumn(ColumnObjectData rightColumn) {
        this.rightColumn = rightColumn;
    }

    public TableJoinType getTableJoinType() {
        return tableJoinType;
    }

    public void setTableJoinType(TableJoinType tableJoinType) {
        this.tableJoinType = tableJoinType;
    }    
}
