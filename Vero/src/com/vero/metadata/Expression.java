package com.vero.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    private Map<String, String> parameters = null;
    private List<Column> columns = null;
    private Column smallestColumn = null;
    private Column usedColumn = null;

    public Expression() {
    }
    
    public Expression(UUID uuid, String formula) {
        this.uuid = uuid;
        this.formula = formula;
        parameters = new HashMap();
        columns = new ArrayList();
        smallestColumn = null;
        usedColumn = null;
    }

    public Expression(UUID uuid, String formula, Map<String, String> parameters, List<Column> columns) {
        this.uuid = uuid;
        this.formula = formula;
        this.parameters = new HashMap(parameters);
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

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }
    
    public Map<String, String> getParameters() {
        return parameters;
    }
    
    public void addColumn(Column column) {
        if (column != null) {
            this.columns.add(column);
        }
    }

    public List<Column> getColumns() {
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
    
    public List gatherTables() {
        ArrayList<Table> aTabList = new ArrayList();
        
        for (int i = 0; i < columns.size(); i++) {
            Column curCol = columns.get(i);
            aTabList.add(curCol.getTable());
        }
        
        return aTabList;
    }    

    public List gatherColumns() {
        ArrayList<Column> aColList = new ArrayList();
        
        for (int i = 0; i < columns.size(); i++) {
            aColList.add(columns.get(i));
        }
        
        return aColList;        
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
