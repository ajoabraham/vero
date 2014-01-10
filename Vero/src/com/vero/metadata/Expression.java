package com.vero.metadata;

import java.util.ArrayList;
import java.util.UUID;

public class Expression implements Comparable<Expression> {
    private final UUID uuid;
    private final String expression;
    private final ArrayList<Column> columns;
    private Column smallestColumn = null;

    public Expression(String inExp) {
        uuid = UUID.randomUUID();
        expression = inExp;
        columns = new ArrayList();
        smallestColumn = null;
    }

    public void addColumn(Column inCol) {
        if (inCol != null) {
            columns.add(inCol);
        }
        
        // update smallestColumn
        if (smallestColumn == null) {
            smallestColumn = inCol;
        } else {
            if (inCol.getTable().getRowCount() < smallestColumn.getTable().getRowCount()) {
                smallestColumn = inCol;
            }
        }
    }

    public Column getSmallestColumn() {
        return smallestColumn;
    }
    
    public String getExpression() {
        return expression;
    }
    
    public ArrayList gatherTables() {
        ArrayList<Table> aTabList = new ArrayList();
        
        for (int i = 0; i < columns.size(); i++) {
            Column curCol = columns.get(i);
            aTabList.add(curCol.getTable());
        }
        
        return aTabList;
    }
    
    public UUID getUUID() {
        return uuid;
    }
    
    @Override
    public int compareTo(Expression inExp) {
        if (this.getSmallestColumn().getTable().getRowCount() < inExp.getSmallestColumn().getTable().getRowCount()) {
            return -1;
        } else if (this.getSmallestColumn().getTable().getRowCount() > inExp.getSmallestColumn().getTable().getRowCount()) {
            return 1;
        } else {
            return 0;
        }
    }   
}
