package com.vero.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * An expression consists of one or more columns and one or more functions. 
 * It can point to one or more Tables. In the alpha version an expression 
 * simply has a user inputted string value and it points to one or more tables.  
 * An expression either belongs to a metric or has many attributes. It cannot 
 * have a metric and an attribute.
 * 
 * @author Yulin Wen
 */
public class Expression implements Comparable<Expression> {
    private UUID uuid = null;
    private String formula = null;
    private ArrayList<Column> columns = null;
    private Column smallestColumn = null;

    public Expression() {
    }
    
    public Expression(UUID uuid, String formula) {
        this.uuid = uuid;
        this.formula = formula;
        columns = new ArrayList();
        smallestColumn = null;
    }

    public Expression(UUID uuid, String formula, ArrayList<Column> columns) {
        this.uuid = uuid;
        this.formula = formula;
        this.columns = new ArrayList(columns);
        smallestColumn = null;
    }
    
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
    
    public UUID getUUID() {
        return uuid;
    }    
    
    public void setFormula(String formula) {
        this.formula = formula;
    }
    
    public String getFormula() {
        return formula;
    }
    
    public void addColumn(Column column) {
        if (column != null) {
            this.columns.add(column);
        }
    }

    public ArrayList<Column> getColumns() {
        return this.columns;
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
        
        return smallestColumn;
    }    
    
    public void removeTable(String inTable) {        
        for (Iterator<Column> colIter = columns.listIterator(); colIter.hasNext(); ) {
            Column curCol = colIter.next();
            if (inTable.equals(curCol.getTable().getPhysicalName())) {
                colIter.remove();
            }
        }
    }
    
    public ArrayList gatherTables() {
        ArrayList<Table> aTabList = new ArrayList();
        
        for (int i = 0; i < columns.size(); i++) {
            Column curCol = columns.get(i);
            aTabList.add(curCol.getTable());
        }
        
        return aTabList;
    }    
    
    @Override
    public int compareTo(Expression inExp) {
        //System.out.println(this.getSmallestColumn() + " : " + inExp.getSmallestColumn());
        //System.out.println("inExp formula: " + inExp.getFormula());
        
        if (this.getSmallestColumn().getTable().getRowCount() < inExp.getSmallestColumn().getTable().getRowCount()) {
            return -1;
        } else if (this.getSmallestColumn().getTable().getRowCount() > inExp.getSmallestColumn().getTable().getRowCount()) {
            return 1;
        } else {
            return 0;
        }
    }   
}
