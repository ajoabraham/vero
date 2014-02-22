/**
 * 
 */
package com.vero.ui.service;

import com.vero.ui.model.DatabaseObjectData;

/**
 * @author Tai Hu
 *
 */
public interface QueryExecutionService {
    public void executeQuery(DatabaseObjectData databaseObjectData, String query) throws ServiceException;
}
