/**
 * 
 */
package com.vero.ui.service;

import java.util.List;

import com.vero.ui.model.DatasourceObjectData;

/**
 * @author Tai Hu
 *
 */
public interface DatasourceImportService {
    // Return list of all database schema names for given database connection.
    public List<String> getDatabaseNames(DatasourceObjectData data);
    public DatasourceObjectData getDatasource(DatasourceObjectData data);
    public boolean testConnection(DatasourceObjectData data);
}
