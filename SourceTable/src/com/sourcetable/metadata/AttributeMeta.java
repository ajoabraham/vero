package com.sourcetable.metadata;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Iterator;
import com.sourcetable.datasource.*;

public class AttributeMeta implements Filterable {
    UUID uuid;
    String name;
    String description;
    ArrayList<ExpressionMeta> expressions;
    
    public AttributeMeta(String inName, String inDescription) {
        uuid = UUID.randomUUID();
        name = inName;
        description = inDescription;
        expressions = new ArrayList();
    }

    public AttributeMeta(String inName, String inDescription, ArrayList<ExpressionMeta> inExpressions) {
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

        AttributeMeta attr = new AttributeMeta("Att1", "haha_description");
        attr.addExpression(exp);
        
        System.out.println("AttributeMeta: " + attr.toString());
        System.out.println("uuid: " + attr.getUUID().toString());
        System.out.println("name: " + attr.getName());
        
        System.out.println("Loop through expressions...");
        ArrayList<ExpressionMeta> exprs = attr.getExpressions();
        Iterator<ExpressionMeta> it = exprs.iterator();        
        while (it.hasNext()) {
            ExpressionMeta curexp = it.next();
            System.out.println("ExpressionMeta: " + curexp.getExpression());
        }
    }
}
