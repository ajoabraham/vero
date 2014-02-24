/**
 * 
 */
package com.vero.ui.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vero.ui.constants.DBType;
import com.vero.ui.model.DatabaseObjectData;

/**
 * @author Tai Hu
 *
 */
public class QueryExecutionServiceImpl implements QueryExecutionService {
    private static final Logger logger = Logger.getLogger(QueryExecutionServiceImpl.class.getName());
    
    public QueryExecutionServiceImpl() {
    }

    @Override
    public List<Map<String, Object>> executeQuery(DatabaseObjectData databaseObjectData, String query)
            throws ServiceException {	
	DBType dbType = databaseObjectData.getDatabaseType();
	
	String dbUrl = String.format(dbType.getDBUrl(), 
		databaseObjectData.getHostname(),
		databaseObjectData.getDatabaseType().getDefaultPort(),
		databaseObjectData.getDatabaseName(),
		databaseObjectData.getUserName(),
		databaseObjectData.getPassword());
	
	logger.finest("DB URL = " + dbUrl);
	
	Connection connection = null;
	Statement statement = null;
	
	try {
	    Class.forName(dbType.getDriver());
	    connection = DriverManager.getConnection(dbUrl);
	    statement = connection.createStatement();
	    ResultSet resultSet = statement.executeQuery(query);
	    
	    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
	    int columnCount = resultSetMetaData.getColumnCount();
	    
	    List<String> columnNames = new ArrayList<String>();
	    
	    for (int i = 1; i <= columnCount; i++) {
		columnNames.add(resultSetMetaData.getColumnName(i));
	    }
	    
	    List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
	    
	    while (resultSet.next()) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (String columnName : columnNames) {
		    result.put(columnName, resultSet.getObject(columnName));
		}
		
		results.add(result);
	    }
	    
	    return results;
	}
	catch (SQLException e) {
	    logger.log(Level.SEVERE, e.getMessage(), e);
	    throw new ServiceException(e.getMessage(), e);
	}
	catch (Exception e) {
	    logger.log(Level.SEVERE, e.getMessage(), e);
	    throw new ServiceException(e.getMessage(), e);
	}
	finally {
	    if (statement != null) {
		try {
	            statement.close();
                }
                catch (SQLException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
	    }
	    
	    if (connection != null) {
		try {
	            connection.close();
                }
                catch (SQLException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
	    }
	}
    }
}
