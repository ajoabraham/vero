/**
 * 
 */
package com.vero.ui.service;

import java.util.List;
import java.util.Map;

import com.vero.ui.model.DatabaseObjectData;

/**
 * @author Tai Hu
 *
 */
public interface QueryExecutionService {
    public List<Map<String, Object>> executeQuery(DatabaseObjectData databaseObjectData, String query) throws ServiceException;
}
