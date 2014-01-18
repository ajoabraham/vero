/**
 * 
 */
package com.vero.ui.constants;

/**
 * @author Tai Hu
 *
 */
public enum DBKeyType {
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
