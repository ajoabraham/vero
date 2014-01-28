/**
 * 
 */
package com.vero.ui.service;

import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.ProjectObjectData;

/**
 * @author Tai Hu
 *
 */
public interface MetadataPersistentService {
    public void persistDatasource(DatasourceObjectData data) throws ServiceException;
    public boolean isUniqueDatasourceName(String projectId, String name) throws ServiceException;
    public ProjectObjectData getProjectDataObject(String projectId) throws ServiceException;
}
