/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.common;

/**
 *
 * @author Tai Hu
 */
public enum ObjectType {  
    DATASOURCE, 
    TABLE,
    COLUMN,
    EXPRESSION,
    ATTRIBUTE,
    METRIC,
    TABLE_JOIN,
    // This is a special type used to represent tree root
    // which is not displayed in UI.
    ROOT
}
