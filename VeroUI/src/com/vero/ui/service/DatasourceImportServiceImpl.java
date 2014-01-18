/**
 * 
 */
package com.vero.ui.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vero.db.AbstractDB;
import com.vero.metadata.Table;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.TableObjectData;
import com.vero.ui.util.DataUtils;

/**
 * @author Tai Hu
 * 
 */
public class DatasourceImportServiceImpl implements DatasourceImportService {
    private static final Logger logger = Logger.getLogger(DatasourceImportServiceImpl.class.getName());
    
    protected DatasourceImportServiceImpl() {
    }

    @Override
    public List<String> getDatabaseNames(DatasourceObjectData data) throws ServiceException {
        AbstractDB dbConnection = null;
        try {
            dbConnection = createDBConnection(data);
            
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
    public List<TableObjectData> getDatabaseTables(DatasourceObjectData data) throws ServiceException {
	AbstractDB dbConnection = null;
	List<TableObjectData> tableObjectDataList = new ArrayList<TableObjectData>();
	
        try {
            dbConnection = createDBConnection(data);
            Map<String, Table> allTables = dbConnection.getDBTables();

            for (Table table : allTables.values()) {
                // Load primary and foreign key information
                dbConnection.identifyKeys(table);
                TableObjectData tableObjectData = new TableObjectData();
                DataUtils.copy(table, tableObjectData);
                
                tableObjectDataList.add(tableObjectData);
            }
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
        return tableObjectDataList;
    }

    @Override
    public boolean testConnection(DatasourceObjectData data) throws ServiceException {
        AbstractDB dbConnection = null;
        
        try {
            dbConnection = createDBConnection(data);
            
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
    
    private AbstractDB createDBConnection(DatasourceObjectData data) {
	AbstractDB dbConnection = data.getDatabaseType().getDBConnection();
	dbConnection.setUsername(data.getUserName()).setPassword(data.getPassword()).setHostName(data.getHostname()).setDatabaseName(data.getDatabaseName());
	return dbConnection;
    }
}
