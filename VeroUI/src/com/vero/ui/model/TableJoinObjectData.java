/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.TABLE_JOIN;
import com.vero.ui.common.TableJoinType;

/**
 *
 * @author Tai Hu
 */
public class TableJoinObjectData extends UIData {
    private String leftTableName = null;
    private String rightTableName = null;
    private TableJoinType tableJoinType = null;
    
    public TableJoinObjectData(String leftTableName, TableJoinType tableJoinType, String rightTableName) {
        this.leftTableName = leftTableName;
        this.tableJoinType = tableJoinType;
        this.rightTableName = rightTableName;
    }
    
    @Override
    public ObjectType getType() {
        return TABLE_JOIN;
    }

    public String getLeftTableName() {
        return leftTableName;
    }

    public void setLeftTableName(String leftTableName) {
        this.leftTableName = leftTableName;
    }

    public String getRightTableName() {
        return rightTableName;
    }

    public void setRightTableName(String rightTableName) {
        this.rightTableName = rightTableName;
    }

    public TableJoinType getTableJoinType() {
        return tableJoinType;
    }

    public void setTableJoinType(TableJoinType tableJoinType) {
        this.tableJoinType = tableJoinType;
    }    
}
