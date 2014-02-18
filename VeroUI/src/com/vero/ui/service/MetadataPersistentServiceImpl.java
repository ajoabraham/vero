/**
 * 
 */
package com.vero.ui.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vero.model.dao.MetadataDao;
import com.vero.model.dao.MetadataDaoImpl;
import com.vero.model.dao.PersistentException;
import com.vero.model.entities.SchemaAttribute;
import com.vero.model.entities.SchemaMetric;
import com.vero.model.entities.SchemaProject;
import com.vero.model.entities.SchemaTable;
import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.ProjectObjectData;
import com.vero.ui.model.TableObjectData;

/**
 * @author Tai Hu
 *
 */
public class MetadataPersistentServiceImpl implements MetadataPersistentService {
    private static final Logger logger = Logger.getLogger(MetadataPersistentServiceImpl.class.getName());
    
    private MetadataDao metadataDao = null;

    protected MetadataPersistentServiceImpl() {
        metadataDao = new MetadataDaoImpl();
    }

    @Override
    public void persistDatasource(DatasourceObjectData data) throws ServiceException {
        try {
            metadataDao.persist(data.getSchemaDatasource());
        }
        catch (PersistentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isUniqueDatasourceName(String projectId, String name) throws ServiceException { 
        try {
            return metadataDao.isUniqueDatasourceName(projectId, name);
        }
        catch (PersistentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public ProjectObjectData getProjectDataObject(String projectId) throws ServiceException {
        try {
            SchemaProject schemaProject = metadataDao.find(SchemaProject.class, projectId);
            return new ProjectObjectData(schemaProject);
        }
        catch (PersistentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AttributeObjectData> findAttributeObjectDataList(String tableId) throws ServiceException {
        try {
	    List<SchemaAttribute> schemaAttributes = metadataDao.findSchemaAttributes(tableId);
	    List<AttributeObjectData> attributeObjectDataList = new ArrayList<AttributeObjectData>();
	    
	    for (SchemaAttribute schemaAttribute : schemaAttributes) {
		AttributeObjectData attributeObjectData = new AttributeObjectData(schemaAttribute);
		attributeObjectDataList.add(attributeObjectData);
	    }
	    
	    return attributeObjectDataList;
        }
        catch (PersistentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MetricObjectData> findMetricObjectDataList(String tableId) throws ServiceException {
        try {
	    List<SchemaMetric> schemaMetrics = metadataDao.findSchemaMetrics(tableId);
	    List<MetricObjectData> metricObjectDataList = new ArrayList<MetricObjectData>();
	    
	    for (SchemaMetric schemaMetric : schemaMetrics) {
		MetricObjectData metricObjectData = new MetricObjectData(schemaMetric);
		metricObjectDataList.add(metricObjectData);
	    }
	    
	    return metricObjectDataList;
        }
        catch (PersistentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TableObjectData> findTableObjectDataListByColumnNames(String datasourceId, Collection<String> columnNames) throws ServiceException {
        try {
            List<SchemaTable> schemaTables = metadataDao.findSchemaTablesByColumnNames(datasourceId, columnNames);
            List<TableObjectData> tableObjectDataList = new ArrayList<TableObjectData>();
            
            DatasourceObjectData datasourceObjectData = null;
            for (SchemaTable schemaTable : schemaTables) {
                TableObjectData tableObjectData = new TableObjectData(schemaTable);
                if (datasourceObjectData == null) {
                    datasourceObjectData = new DatasourceObjectData();
                    datasourceObjectData.setId(schemaTable.getSchemaDatasource().getId());
                }
                
                datasourceObjectData.addTableObjectData(tableObjectData);
                
                tableObjectDataList.add(tableObjectData);
            }
            
            return tableObjectDataList;
        }
        catch (PersistentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }
}
