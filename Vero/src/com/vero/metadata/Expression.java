package com.vero.metadata;

import java.util.ArrayList;
import java.util.UUID;

public class Expression implements Filterable {
    UUID uuid;
    String expression;
    ArrayList<Column> columns;

    public Expression(String inExp) {
        uuid = UUID.randomUUID();
        expression = inExp;
        columns = new ArrayList();
    }

    public void addColumn(Column inCol) {
        if (inCol != null) {
            columns.add(inCol);
        }
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
}
