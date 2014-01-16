/**
 * 
 */
package com.vero.ui.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vero.db.AbstractDB;
import com.vero.ui.model.DatasourceObjectData;

/**
 * @author Tai Hu
 * 
 */
public class DatasourceImportServiceImpl implements DatasourceImportService {
    private static final Logger logger = Logger.getLogger(DatasourceImportServiceImpl.class.getName());
    
    public DatasourceImportServiceImpl() {
    }

    @Override
    public List<String> getDatabaseNames(DatasourceObjectData data) throws ServiceException {
        AbstractDB dbConnection = null;
        try {
            dbConnection = data.getDatabaseType().getDBConnection();
            dbConnection.setUsername(data.getUserName()).setPassword(data.getPassword()).setHostName(data.getHostname()).setDatabaseName("northwind");
            return dbConnection.getDatabases();
        }
        catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
        finally {
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                }
                catch (SQLException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public DatasourceObjectData getDatasource(DatasourceObjectData data) throws ServiceException {
        return null;
    }

    @Override
    public boolean testConnection(DatasourceObjectData data) throws ServiceException {
        AbstractDB dbConnection = null;
        
        try {
            dbConnection = data.getDatabaseType().getDBConnection();        
            dbConnection.setUsername(data.getUserName()).setPassword(data.getPassword()).setHostName(data.getHostname()).setDatabaseName("northwind");
            
            return dbConnection.testConnection();
        }
        catch (Exception e) {
            throw new ServiceException("Failed to connect", e);
        }
        finally {
            if (dbConnection != null) {
        	try {
	            dbConnection.close();
                }
                catch (Exception e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
    }
}
