package com.vero.metadata;

public class Column {
    private String name;
    private String dataType;
    private int dataTypeSize;
    private Table table;
    private KeyTypes keyType;
    private int decimalDigits;
    private int sqlType;
    
    public static enum KeyTypes{
        PRIMARY_KEY,
        FOREIGN_KEY,
        NO_KEY_TYPE
    }
    
    public Column(){
        this.name = "";
        this.dataType = "";        
        this.keyType = KeyTypes.NO_KEY_TYPE;
        this.decimalDigits =0;
    }
    
    public Column(String name, String dataType, int dataTypeSize, Table table) {
        this.name = name;
        this.dataType = dataType;
        this.dataTypeSize = dataTypeSize;
        this.decimalDigits= 0;
        this.keyType = KeyTypes.NO_KEY_TYPE;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }
    
    public int getDataTypeSize() {
        return dataTypeSize;
    }
    
    public int getDecimalDigits() {
        return decimalDigits;
    }

    public Table getTable() {
        return table;
    }

    public KeyTypes getKeyType() {
        return keyType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setKeyType(KeyTypes keyType) {
        this.keyType = keyType;
    }
    
    public void setDataTypeSize(int dataTypeSize) {
        this.dataTypeSize = dataTypeSize;
    }
    
    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }
    
    public boolean isForeignKey(){
        return getKeyType() == KeyTypes.FOREIGN_KEY;
    }
    
    public boolean isPrimaryKey(){
        return getKeyType() == KeyTypes.PRIMARY_KEY;
    }
    
    public int getSqlType() {
        return sqlType;
    }

    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }
}
