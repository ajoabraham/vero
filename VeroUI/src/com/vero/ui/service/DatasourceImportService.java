/**
 * 
 */
package com.vero.ui.service;

import java.util.List;

import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.TableObjectData;

/**
 * @author Tai Hu
 *
 */
public interface DatasourceImportService {
    // Return list of all database schema names for given database connection.
    public List<String> getDatabaseNames(DatasourceObjectData data) throws ServiceException;
    public boolean testConnection(DatasourceObjectData data) throws ServiceException;
    public List<TableObjectData> getDatabaseTables(DatasourceObjectData data) throws ServiceException;
    // Get all tables primary keys, foreign keys, and row count information
    public void updateTableStats(DatasourceObjectData datasourceObjectData, List<TableObjectData> tableObjectDataList) throws ServiceException;
}
