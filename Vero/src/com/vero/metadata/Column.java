package com.vero.metadata;

/**
 *
 * @author ajoabraham
 */
public class Column {
    private String objectName;
    private String dataType;
    private int dataTypeSize;
    private Table table;
    private KeyTypes keyType;
    private int decimalDigits;
    private int sqlType;
    
    /**
     * Identifies a column as Primary or Foreign.
     * we'll use this information in our algorithms to 
     * auto detect table type.
     */
    public static enum KeyTypes{
        PRIMARY_KEY,
        FOREIGN_KEY,
        NO_KEY_TYPE
    }
    
    /**
     *
     */
    public Column(){
        this.objectName = "";
        this.dataType = "";        
        this.keyType = KeyTypes.NO_KEY_TYPE;
        this.decimalDigits =0;
    }
    
    /**
     *
     * @param name
     * @param dataType
     * @param dataTypeSize
     * @param table
     */
    public Column(String name, String dataType, int dataTypeSize, Table table) {
        this.objectName = name;
        this.dataType = dataType;
        this.dataTypeSize = dataTypeSize;
        this.decimalDigits= 0;
        this.keyType = KeyTypes.NO_KEY_TYPE;
        this.table = table;
    }

    /**
     *
     * @return
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     *
     * @return
     */
    public String getDataType() {
        return dataType;
    }
    
    /**
     *
     * @return
     */
    public int getDataTypeSize() {
        return dataTypeSize;
    }
    
    /**
     *
     * @return
     */
    public int getDecimalDigits() {
        return decimalDigits;
    }

    /**
     *
     * @return
     */
    public Table getTable() {
        return table;
    }

    /**
     *
     * @return
     */
    public KeyTypes getKeyType() {
        return keyType;
    }

    /**
     *
     * @param name
     */
    public void setObjectName(String name) {
        this.objectName = name;
    }

    /**
     *
     * @param dataType
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     *
     * @param table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    public void setKeyType(KeyTypes keyType) {
        this.keyType = keyType;
    }
    
    /**
     *
     * @param dataTypeSize
     */
    public void setDataTypeSize(int dataTypeSize) {
        this.dataTypeSize = dataTypeSize;
    }
    
    /**
     *
     * @param decimalDigits
     */
    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }
    
    /**
     *
     * @return
     */
    public boolean isForeignKey(){
        return getKeyType() == KeyTypes.FOREIGN_KEY;
    }
    
    /**
     *
     * @return
     */
    public boolean isPrimaryKey(){
        return getKeyType() == KeyTypes.PRIMARY_KEY;
    }
    
    /**
     *
     * @return
     */
    public int getSqlType() {
        return sqlType;
    }

    /**
     * This is the java sql type.  Built from the
     * "DATA_TYPE" field in the metadata.
     * 
     * @param sqlType
     * @see java.sql.Types
     */
    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }
}
