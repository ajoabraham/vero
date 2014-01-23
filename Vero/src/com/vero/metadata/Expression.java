package com.vero.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class Expression implements Comparable<Expression> {
    private final UUID uuid;
    private final String formula;
    private final ArrayList<Column> columns;
    private Column smallestColumn = null;

    public Expression(String formula) {
        uuid = UUID.randomUUID();
        this.formula = formula;
        columns = new ArrayList();
        smallestColumn = null;
    }

    public void addColumn(Column inCol) {
        if (inCol != null) {
            columns.add(inCol);
        }       
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }
    
    public Column getSmallestColumn() {
        for (Column curCol : this.columns) {
            if (this.smallestColumn == null) {
                this.smallestColumn = curCol;
            }
            
            if (curCol != this.smallestColumn) {
                if (curCol.getTable().getRowCount() < this.smallestColumn.getTable().getRowCount()) {
                    this.smallestColumn = curCol;
                }
            }
        }
                
        return this.smallestColumn;
    }
    
    public String getFormula() {
        return formula;
    }
    
    public void removeTable(String inTable) {        
        for (Iterator<Column> colIter = columns.listIterator(); colIter.hasNext(); ) {
            Column curCol = colIter.next();
            if (inTable.equals(curCol.getTable().getPhysicalName())) {
                System.out.println("Remove table:" + inTable);
                colIter.remove();
            }
        }        
    }
    
    public ArrayList gatherTables() {
        ArrayList<Table> aTabList = new ArrayList();
        
        for (int i = 0; i < columns.size(); i++) {
            Column curCol = columns.get(i);
            System.out.println("GatherTable: " + curCol.getTable().getPhysicalName());
            aTabList.add(curCol.getTable());
        }
        
        return aTabList;
    }
    
    public UUID getUUID() {
        return uuid;
    }
    
    @Override
    public int compareTo(Expression inExp) {
        System.out.println(this.getSmallestColumn() + " : " + inExp.getSmallestColumn());
        System.out.println("inExp formula: " + inExp.getFormula());
        
        if (this.getSmallestColumn().getTable().getRowCount() < inExp.getSmallestColumn().getTable().getRowCount()) {
            return -1;
        } else if (this.getSmallestColumn().getTable().getRowCount() > inExp.getSmallestColumn().getTable().getRowCount()) {
            return 1;
        } else {
            return 0;
        }
    }   
}
