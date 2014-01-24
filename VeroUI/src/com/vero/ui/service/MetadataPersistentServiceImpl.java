/**
 * 
 */
package com.vero.ui.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vero.model.dao.MetadataDao;
import com.vero.model.dao.MetadataDaoImpl;
import com.vero.model.dao.PersistentException;
import com.vero.model.entities.SchemaDatasource;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.util.DataUtils;

/**
 * @author Tai Hu
 *
 */
public class MetadataPersistentServiceImpl implements MetadataPersistentService {
    private static final Logger logger = Logger.getLogger(MetadataPersistentServiceImpl.class.getName());
    
    private MetadataDao metadataDao = null;

    public MetadataPersistentServiceImpl() {
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

    /* (non-Javadoc)
     * @see com.vero.ui.service.MetadataPersistentService#findAllDatasources()
     */
    @Override
    public List<DatasourceObjectData> findAllDatasources() throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

}
