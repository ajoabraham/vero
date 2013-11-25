package com.vero.datasource;

public class Column {
    String name;
    ColDataType dataType;
    boolean primaryKey;
    Table table;
    
    public Column(String inName, ColDataType inType, boolean inPKey, Table inTable) {
        name = inName;
        dataType = inType;
        primaryKey = inPKey;
        table = inTable;
    }
}
