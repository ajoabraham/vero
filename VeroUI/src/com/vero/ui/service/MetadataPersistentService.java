/**
 * 
 */
package com.vero.ui.service;

import java.util.List;

import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.ProjectObjectData;

/**
 * @author Tai Hu
 *
 */
public interface MetadataPersistentService {
    public void persistDatasource(DatasourceObjectData data) throws ServiceException;
    public boolean isUniqueDatasourceName(String projectId, String name) throws ServiceException;
    public ProjectObjectData getProjectDataObject(String projectId) throws ServiceException;
    public List<AttributeObjectData> findAttributeObjectDataList(String tableId) throws ServiceException;
    public List<MetricObjectData> findMetricObjectDataList(String tableId) throws ServiceException;
//    public List<ColumnObjectData> findUnusedColumnObjectDataList(String tableId) throws ServiceException;
}
