package com.vero.metadata;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Iterator;

public class Metric implements Filterable {
    UUID uuid;
    String name;
    String description;
    ArrayList<Expression> expressions;
    
    public Metric(String inName, String inDescription) {
        uuid = UUID.randomUUID();
        name = inName;
        description = inDescription;
        expressions = new ArrayList();
    }

    public Metric(String inName, String inDescription, ArrayList<Expression> inExpressions) {
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

        Metric metr = new Metric("Met1", "haha_description");
        metr.addExpression(exp);
        
        System.out.println("MetricMeta: " + metr.toString());
        System.out.println("uuid: " + metr.getUUID().toString());
        System.out.println("name: " + metr.getName());
        
        System.out.println("Loop through expressions...");
        ArrayList<Expression> exprs = metr.getExpressions();
        Iterator<Expression> it = exprs.iterator();        
        while (it.hasNext()) {
            Expression curexp = it.next();
            System.out.println("ExpressionMeta: " + curexp.getExpression());
        }
    }        
}
