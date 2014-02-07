/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.Column;
import com.vero.metadata.Expression;

/**
 *
 * @author Yulin Wen
 */
public class ExpressionUnit {
    private Expression expression = null;
    private Column column = null;
 
    public ExpressionUnit(Expression expression, Column column) {
        this.expression = expression;
        this.column = column;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public Column getColumn() {
        return column;
    }
}
