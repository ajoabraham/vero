/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.metadata;

/**
 *
 * @author yulinwen
 */
public class FilterItem {
    FilterOperandType opType;
    Filterable filterable; // ToDo: constant
    
    public enum FilterOperandType {
        NONE,
        AND,
        OR,
        AND_NOT,
        OR_NOT
    }    
}
