package com.sourcetable.metadata;

import java.util.ArrayList;

import com.sourcetable.datasource.*;

public class ExpressionMeta {	
	String expression;
	ArrayList<Table> tables;
		
	public ExpressionMeta(String inExp) {
		expression = inExp;
		tables = new ArrayList<Table>();
	}	
	
	public void addTable(Table inTab) {
		tables.add(inTab);
	}
	
	public String getExpression() {
		return expression;	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ExpressionMeta starts...");
		
		ExpressionMeta exp = new ExpressionMeta("abcde");		
		exp.addTable(new Table());
		
		System.out.println("Expression: " + exp.toString());
		System.out.println("    expression: " + exp.getExpression());
	}
}
