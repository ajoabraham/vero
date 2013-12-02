package com.vero.metadata;

/**
 * Columns are the most fundamental of all objects. A table
 * has many columns and a column belongs to a table.
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
        /**
         * The primary key column of a table.  There could
         * be zero or many primary key columns.
         */
        PRIMARY_KEY,
        /**
         * The foreign key column of a table.  There could be 
         * zero or many foreign key columns.
         */
        FOREIGN_KEY,
        /**
         * This is the default key type which is just a normal
         * column that is not a primary or foreign key.
         */
        NO_KEY_TYPE
    }
    
    /**
     * Default column constructor.
     */
    public Column(){
        this.objectName = "";
        this.dataType = "";        
        this.keyType = KeyTypes.NO_KEY_TYPE;
        this.decimalDigits =0;
    }
    
    /**
     * A convenience constructor for building columns quickly.
     * 
     * @param name  The name of a column (must be actual name of column in the db).
     * @param dataType  The name of the datatype as reported by the database.
     * @param dataTypeSize  The size of the datatype as reported by the database.
     * @param table The table to which this column belongs to.
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
     * Column names are always the same as their name in the database.
     * We store column names exactly as it exists in the database with case sensitivity.
     * 
     * @return  Exact name of column as it exists in the database.
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * String name of datatype reported by database.
     * 
     * @return  column datatype as reported by the database.
     */
    public String getDataType() {
        return dataType;
    }
    
    /**
     * Size of the datatype as reported by the database.
     * 
     * @return  Integer size of db datatype {@link #getDataType()}
     */
    public int getDataTypeSize() {
        return dataTypeSize;
    }
    
    /**
     * Decimal digits of datatype as reported by the database.
     * Zero by default.
     * 
     * @return  For numeric values with precision it returns actual
     *          decimal digits otherwise zero.
     */
    public int getDecimalDigits() {
        return decimalDigits;
    }

    /**
     * 
     * @return  The table this column belongs to.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Get the key type of a column. It could be a primary
     * key, foreign key, or no key type.  We use this information
     * to determine table type and optimize queries.
     * 
     * @return  a LogicalTableType
     */
    public KeyTypes getKeyType() {
        return keyType;
    }

    /**
     * Name of a column as it exists in the database. We store this
     * with case sensitivity.
     * 
     * @param name  this should be the actual name of the column
     *              in the database.  Names other than the db name
     *              may cause issues with queries.
     */
    public void setObjectName(String name) {
        this.objectName = name;
    }

    /**
     * We may have to rethink datatype in the future. We might just have
     * to store the java.sql.Type instead of the db reported one.
     * 
     * @param dataType  name of datatype reported by the database.
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * 
     * @param table The table that this column belong to.
     */
    public void setTable(Table table) {
        this.table = table;
    }

    public void setKeyType(KeyTypes keyType) {
        this.keyType = keyType;
    }
    
    /**
     *
     * @param dataTypeSize  size of datatype as reported by the db.
     */
    public void setDataTypeSize(int dataTypeSize) {
        this.dataTypeSize = dataTypeSize;
    }
    
    /**
     *
     * @param decimalDigits decimal digits as reported by the db.
     */
    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }
    
    /**
     *
     * @return  true if foreign key
     */
    public boolean isForeignKey(){
        return getKeyType() == KeyTypes.FOREIGN_KEY;
    }
    
    /**
     *
     * @return  true if primary key
     */
    public boolean isPrimaryKey(){
        return getKeyType() == KeyTypes.PRIMARY_KEY;
    }
    
    /**
     * @see java.sql.Types
     * @return  the java.sql.Types
     */
    public int getSqlType() {
        return sqlType;
    }

    /**
     * This is the java.sql.Types.  Built from the
     * "DATA_TYPE" field in the @see DatabaseMetadata.
     * 
     * @param sqlType
     * @see java.sql.Types
     */
    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }
}
