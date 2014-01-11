package com.vero.metadata;

import com.vero.session.Session;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Iterator;

public class Attribute implements Filterable {
    private final int objID = Session.sessionObjID++;
    private final UUID uuid = UUID.randomUUID();
    private String name = null;
    private String description = null;
    private ArrayList<Expression> expressions = null;
    
    public Attribute(String inName, String inDescription) {
        name = inName;
        description = inDescription;
        expressions = new ArrayList();
    }

    public Attribute(String inName, String inDescription, ArrayList<Expression> inExpressions) {
        name = inName;
        description = inDescription;
        expressions = new ArrayList(inExpressions);
    }

    public void addExpression(Expression inExpression) {
        expressions.add(inExpression);
    }

    public void addExpressions(ArrayList<Expression> inExpressions) {
        expressions.addAll(inExpressions);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getObjID() {
        return objID;
    }
    
    public UUID getUUID() {
        return uuid;
    }
    
    public ArrayList<Expression> getExpressions() {
        return expressions;
    }

    public Expression getExpressionByTable(Table inTab) {
        ArrayList<Expression> expList = this.getExpressions();
        Boolean found = false;
        
        if (expList.size() > 0) {
            Iterator<Expression> iterExp = expList.iterator();
            
            while (iterExp.hasNext()) {
                Expression curExp = iterExp.next();
                ArrayList<Table> tabList = curExp.gatherTables();
                
                if (tabList.size() > 0) {
                    Iterator<Table> iterTab = tabList.iterator();
                    
                    while (iterTab.hasNext()) {
                        Table curTab = iterTab.next();
                        
                        if (inTab == curTab) {
                            return curExp;
                        }                        
                    }
                }                
            }
        }
        
        return null;
    }

    public Expression getExpressionByTableName(String inTab) {
        ArrayList<Expression> expList = this.getExpressions();
        Boolean found = false;
        
        if (expList.size() > 0) {
            Iterator<Expression> iterExp = expList.iterator();
            
            while (iterExp.hasNext()) {
                Expression curExp = iterExp.next();
                ArrayList<Table> tabList = curExp.gatherTables();
                
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
    
    public ArrayList<Table> retrieveTables() {        
        ArrayList<Expression> aList = this.getExpressions();
        
        if (aList.size() > 0) {
            ArrayList<Table> retList = new ArrayList();
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
