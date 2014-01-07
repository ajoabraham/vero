package com.vero.metadata;

import java.util.ArrayList;
import java.util.UUID;

public class Expression implements Filterable {
    UUID uuid;
    String expression;
    ArrayList<Table> tables;
    ArrayList<Column> columns;

    public Expression(String inExp) {
        uuid = UUID.randomUUID();
        expression = inExp;
        tables = new ArrayList();
        columns = new ArrayList();
    }

    public void addTable(Table inTab) {
        if (inTab != null) {        
            tables.add(inTab);
        }
    }

    public void addColumn(Column inCol) {
        if (inCol != null) {
            columns.add(inCol);
        }
    }
    
    public void addTables(ArrayList<Table> inTables) {        
        tables.addAll(inTables);
    }

    public String getExpression() {
        return expression;
    }

    public ArrayList getTables() {
        return tables;
    }
    
    public UUID getUUID() {
        return uuid;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("ExpressionMeta starts...");

        Expression exp = new Expression("abcde");
        exp.addTable(new Table("abc", null));

        System.out.println("ExpressionMeta: " + exp.toString());
        System.out.println("uuid: " + exp.getUUID().toString());
        System.out.println("expression: " + exp.getExpression());
    }
}
