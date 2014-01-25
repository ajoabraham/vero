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
public interface MetadataPersistentService {
    public void persistDatasource(DatasourceObjectData data) throws ServiceException;
    public List<DatasourceObjectData> findAllDatasources() throws ServiceException;
    public boolean isUniqueDatasourceName(String name) throws ServiceException;
}
