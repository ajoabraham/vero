/**
 * 
 */
package com.vero.model.sb;

import com.vero.model.entities.SchemaData;

/**
 * @author Tai Hu
 *
 */
public interface ObjectDataSB {
    public <T extends SchemaData> void persist(T objectData) throws PersistentException;
    public <T extends SchemaData> T find(Class<T> dataType, String id) throws PersistentException;
    public <T extends SchemaData> T update(T objectData) throws PersistentException;
    public <T extends SchemaData> void remove(T objectData) throws PersistentException;
}
