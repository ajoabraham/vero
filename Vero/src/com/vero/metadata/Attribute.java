package com.vero.metadata;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Iterator;

public class Attribute implements Filterable {
    UUID uuid;
    String name;
    String description;
    ArrayList<Expression> expressions;
    
    public Attribute(String inName, String inDescription) {
        uuid = UUID.randomUUID();
        name = inName;
        description = inDescription;
        expressions = new ArrayList();
    }

    public Attribute(String inName, String inDescription, ArrayList<Expression> inExpressions) {
        uuid = UUID.randomUUID();
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
                ArrayList<Table> tabList = curExp.getTables();
                
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
    
    public ArrayList<Table> retrieveTables() {        
        ArrayList<Expression> aList = this.getExpressions();
        
        if (aList.size() > 0) {
            ArrayList<Table> retList = new ArrayList();
            Iterator<Expression> iter = aList.iterator();

            while (iter.hasNext()) {
                retList.addAll(iter.next().getTables());
            }

            return retList;
        } else {
            return null;
        }
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("AttributeMeta starts...");

        Expression exp = new Expression("abcde");
        exp.addTable(new Table("abc", null));

        Attribute attr = new Attribute("Att1", "haha_description");
        attr.addExpression(exp);
        
        System.out.println("AttributeMeta: " + attr.toString());
        System.out.println("uuid: " + attr.getUUID().toString());
        System.out.println("name: " + attr.getName());
        
        System.out.println("Loop through expressions...");
        ArrayList<Expression> exprs = attr.getExpressions();
        Iterator<Expression> it = exprs.iterator();        
        while (it.hasNext()) {
            Expression curexp = it.next();
            System.out.println("ExpressionMeta: " + curexp.getExpression());
        }
    }
}
