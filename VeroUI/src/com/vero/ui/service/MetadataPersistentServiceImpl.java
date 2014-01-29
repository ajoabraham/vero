/**
 * 
 */
package com.vero.ui.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vero.model.dao.MetadataDao;
import com.vero.model.dao.MetadataDaoImpl;
import com.vero.model.dao.PersistentException;
import com.vero.model.entities.SchemaDatasource;
import com.vero.model.entities.SchemaProject;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.ProjectObjectData;
import com.vero.ui.util.DataUtils;

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
        SchemaDatasource schemaDatasource = new SchemaDatasource();
        DataUtils.copy(data, schemaDatasource);
        
        try {
            metadataDao.persist(schemaDatasource);
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
            ProjectObjectData projectObjectData = new ProjectObjectData();
            SchemaProject schemaProject = metadataDao.find(SchemaProject.class, projectId);            
            DataUtils.copy(schemaProject, projectObjectData);
            return projectObjectData;
        }
        catch (PersistentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

}
