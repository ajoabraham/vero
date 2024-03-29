/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.service;

/**
 *
 * @author Tai Hu
 */
public final class ServiceManager {
    private static final DatasourceImportService datasourceImportService = new DatasourceImportServiceImpl();
    private static final QueryEngineService queryEngineService = new QueryEngineServiceImpl();
    private static final QueryExecutionService queryExecutionService = new QueryExecutionServiceImpl();
    
    public static DatasourceImportService getDatasourceImportService() {
        return datasourceImportService;
    }
    
    public static MetadataPersistentService getMetadataPersistentService() {
        return new MetadataPersistentServiceImpl();
    }
    
    public static QueryEngineService getQueryEngineService() {
        return queryEngineService;
    }
    
    public static QueryExecutionService getQueryExecutionService() {
	return queryExecutionService;
    }
}
