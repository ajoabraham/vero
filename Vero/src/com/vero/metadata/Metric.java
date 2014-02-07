package com.vero.metadata;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Iterator;
import java.util.List;

/** 
 * Metrics generally map to aggregate expressions, where aggregate expressions 
 * operate on multiple rows. 
 * Metrics primarily drive the Select and Group By Clauses.
 * 
 * @author Yulin Wen
 */
public class Metric {
    private UUID uuid = null;
    private String name = null;
    private List<Expression> expressions = null;
    
    public Metric() {
    }
    
    public Metric(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.expressions = new ArrayList();
    }

    public Metric(UUID uuid, String name, List<Expression> expressions) {
        this.uuid = uuid;
        this.name = name;
        this.expressions = new ArrayList(expressions);
    }

    public void addExpression(Expression expression) {
        this.expressions.add(expression);
    }

    public void addExpressions(ArrayList<Expression> expressions) {
        this.expressions.addAll(expressions);
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
    
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Loop through all expressions and all tables within an expression to find
     * if current table by object is in it. 
     * 
     * @param table The table to find.
     * 
     * @return an expression or null if not found.
     */
    public Expression getExpressionByTable(Table table) {
        List<Expression> expList = this.getExpressions();
        Boolean found = false;
        
        if (expList.size() > 0) {
            Iterator<Expression> iterExp = expList.iterator();
            
            while (iterExp.hasNext()) {
                Expression curExp = iterExp.next();
                List<Table> tabList = curExp.gatherTables();
                
                if (tabList.size() > 0) {
                    Iterator<Table> iterTab = tabList.iterator();
                    
                    while (iterTab.hasNext()) {
                        Table curTab = iterTab.next();
                        
                        if (table == curTab) {
                            return curExp;
                        }                        
                    }
                }                
            }
        }
        
        return null;
    }

    /**
     * Loop through all expressions and all tables within an expression to find
     * if current table by name is in it. 
     * 
     * @return an expression or null if not found.
     */
    public Expression getExpressionByTableName(String inTab) {
        List<Expression> expList = this.getExpressions();
        Boolean found = false;
        
        if (expList.size() > 0) {
            Iterator<Expression> iterExp = expList.iterator();
            
            while (iterExp.hasNext()) {
                Expression curExp = iterExp.next();
                List<Table> tabList = curExp.gatherTables();
                
                if (tabList.size() > 0) {
                    Iterator<Table> iterTab = tabList.iterator();
                    
                    while (iterTab.hasNext()) {
                        Table curTab = iterTab.next();
                        
                        if (inTab.equals(curTab.getPhysicalName())) {
                            return curExp;
                        }                        
                    }
                }                
            }
        }
        
        return null;
    }

    /**
     * Loop through all expressions and all tables within an expression to 
     * remove a given table by name. If, after the table is removed, the 
     * expression becomes empty, the expression is removed too. 
     * 
     * @param table The table to remove.
     */
    public void removeTable(String table) {
        List<Expression> aList = this.getExpressions();
        
        if (aList.size() > 0) {
            Iterator<Expression> iter = aList.iterator();

            while (iter.hasNext()) {
                Expression curExp = iter.next();                
                curExp.removeTable(table);
                if (curExp.getColumns().isEmpty()) {
                    iter.remove();
                }
            }
        }
    } 
    
     /**
     * Loop through all expressions and return all tables within an expression.
     * 
     * @return all tables in a list or null if there is no expression.
     */
    public List<Table> retrieveTables() {        
        List<Expression> aList = this.getExpressions();
        
        if (aList.size() > 0) {
            List<Table> retList = new ArrayList();
            Iterator<Expression> iter = aList.iterator();

            while (iter.hasNext()) {
                retList.addAll(iter.next().gatherTables());
            }

            return retList;
        } else {
            return null;
        }
    }    
}
