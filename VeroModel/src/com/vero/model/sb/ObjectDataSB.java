/**
 * 
 */
package com.vero.model.sb;

/**
 * @author Tai Hu
 *
 */
public interface ObjectDataSB {
    public <T> void persist(T objectData);
    public <T> T find(Class<T> dataType, String id);
    public <T> T update(T objectData);
    public <T> void remove(T objectData);
}
