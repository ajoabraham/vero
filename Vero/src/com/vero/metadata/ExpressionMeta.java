package com.vero.metadata;

import com.vero.datasource.Table;
import java.util.ArrayList;
import java.util.UUID;

public class ExpressionMeta implements Filterable {
    UUID uuid;
    String expression;
    ArrayList<Table> tables;

    public ExpressionMeta(String inExp) {
        uuid = UUID.randomUUID();
        expression = inExp;
        tables = new ArrayList();
    }

    public ExpressionMeta(String inExp, ArrayList<Table> inTables) {
        uuid = UUID.randomUUID();
        expression = inExp;
        tables = new ArrayList(inTables);
    }

    public void addTable(Table inTab) {
        tables.add(inTab);
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

        ExpressionMeta exp = new ExpressionMeta("abcde");
        exp.addTable(new Table("abc", null));

        System.out.println("ExpressionMeta: " + exp.toString());
        System.out.println("uuid: " + exp.getUUID().toString());
        System.out.println("expression: " + exp.getExpression());
    }
}
