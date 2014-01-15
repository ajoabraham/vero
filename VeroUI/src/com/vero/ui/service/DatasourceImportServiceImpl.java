/**
 * 
 */
package com.vero.ui.service;

import java.util.List;

import com.vero.db.AbstractDB;
import com.vero.ui.model.DatasourceObjectData;

/**
 * @author Tai Hu
 *
 */
public class DatasourceImportServiceImpl implements DatasourceImportService {

    public DatasourceImportServiceImpl() {
    }

    @Override
    public List<String> getDatabaseNames(DatasourceObjectData data) {
        AbstractDB dbConnection = data.getDatabaseType().getDBConnection();
        dbConnection.setUsername(data.getUserName())
                    .setPassword(data.getPassword())
                    .setHostName(data.getHostname());
        return dbConnection.getDatabases();
    }

    @Override
    public DatasourceObjectData getDatasource(DatasourceObjectData data) {
        return null;
    }

    @Override
    public boolean testConnection(DatasourceObjectData data) {
        return false;
    }
}
