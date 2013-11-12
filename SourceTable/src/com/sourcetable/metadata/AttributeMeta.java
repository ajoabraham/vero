package com.sourcetable.metadata;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Iterator;
import com.sourcetable.datasource.*;

public class AttributeMeta {
    UUID uuid;
    String name;
    ArrayList<ExpressionMeta> expressions;
    
    public AttributeMeta(String inName) {
        uuid = UUID.randomUUID();
        name = inName;
        expressions = new ArrayList();
    }

    public AttributeMeta(String inName, ArrayList<ExpressionMeta> inExpressions) {
        uuid = UUID.randomUUID();
        name = inName;
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
        exp.addTable(new Table());

        AttributeMeta attr = new AttributeMeta("Att1");
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
