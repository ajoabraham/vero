package com.vero.metadata;

import com.vero.datasource.Table;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Iterator;

public class MetricMeta implements Filterable {
    UUID uuid;
    String name;
    String description;
    ArrayList<ExpressionMeta> expressions;
    
    public MetricMeta(String inName, String inDescription) {
        uuid = UUID.randomUUID();
        name = inName;
        description = inDescription;
        expressions = new ArrayList();
    }

    public MetricMeta(String inName, String inDescription, ArrayList<ExpressionMeta> inExpressions) {
        uuid = UUID.randomUUID();
        name = inName;
        description = inDescription;
        expressions = new ArrayList(inExpressions);
    }

    public void addExpression(ExpressionMeta inExpression) {
        expressions.add(inExpression);
    }

    public void addExpressions(ArrayList<ExpressionMeta> inExpressions) {
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
    
    public ArrayList<ExpressionMeta> getExpressions() {
        return expressions;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("AttributeMeta starts...");

        ExpressionMeta exp = new ExpressionMeta("abcde");
        exp.addTable(new Table("abc", null));

        MetricMeta metr = new MetricMeta("Met1", "haha_description");
        metr.addExpression(exp);
        
        System.out.println("MetricMeta: " + metr.toString());
        System.out.println("uuid: " + metr.getUUID().toString());
        System.out.println("name: " + metr.getName());
        
        System.out.println("Loop through expressions...");
        ArrayList<ExpressionMeta> exprs = metr.getExpressions();
        Iterator<ExpressionMeta> it = exprs.iterator();        
        while (it.hasNext()) {
            ExpressionMeta curexp = it.next();
            System.out.println("ExpressionMeta: " + curexp.getExpression());
        }
    }        
}
