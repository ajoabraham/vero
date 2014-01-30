/**
 * 
 */
package com.vero.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.vero.model.entities.SchemaAttribute;
import com.vero.model.entities.SchemaColumn;
import com.vero.model.entities.SchemaData;
import com.vero.model.entities.SchemaMetric;
import com.vero.model.util.PersistentUtils;

/**
 * @author Tai Hu
 *
 */
public class MetadataDaoImpl implements MetadataDao {    
    public MetadataDaoImpl() {    
    }

    @Override
    public <T extends SchemaData> void persist(T objectData) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            em.getTransaction().begin();
            em.persist(objectData);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            if (em.getTransaction().isActive())
        	em.getTransaction().rollback();
            throw new PersistentException(e);
        }
        finally {
            if (em != null) {
        	em.close();
            }
        }
    }

    @Override
    public <T extends SchemaData> T find(Class<T> dataType, String id) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            
            return em.find(dataType, id);
        }
        catch (Exception e) {
            throw new PersistentException(e);
        }
        finally {
            if (em != null) {
        	em.close();
            }
        }
    }

    @Override
    public <T extends SchemaData> T update(T objectData) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            em.getTransaction().begin();
            objectData = em.merge(objectData);
            em.getTransaction().commit();
            return objectData;
        }
        catch (Exception e) {
            if (em.getTransaction().isActive())
        	em.getTransaction().rollback();
            throw new PersistentException(e);
        }
        finally {
            if (em != null) {
        	em.close();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends SchemaData> void remove(T objectData) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            em.getTransaction().begin();
            objectData = (T) em.find(objectData.getClass(), objectData.getId());
            em.remove(objectData);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            if (em.getTransaction().isActive())
        	em.getTransaction().rollback();
            throw new PersistentException(e);
        }
        finally {
            if (em != null) {
        	em.close();
            }
        }
    }

    @Override
    public boolean isUniqueDatasourceName(String projectId, String name) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            
            return em.createNamedQuery("SchemaDatasource.isUniqueName", Long.class)
                     .setParameter("projectId", projectId)
                     .setParameter("name", name)
                     .getSingleResult() == 0;
        }
        catch (Exception e) {
            throw new PersistentException(e);
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<SchemaAttribute> findSchemaAttributes(String tableId) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            
            return em.createNamedQuery("SchemaTable.findAttributes", SchemaAttribute.class)
                     .setParameter("tableId", tableId)
                     .getResultList();
        }
        catch (Exception e) {
            throw new PersistentException(e);
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }


    @Override
    public List<SchemaMetric> findSchemaMetrics(String tableId) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            
            return em.createNamedQuery("SchemaTable.findMetrics", SchemaMetric.class)
                     .setParameter("tableId", tableId)
                     .getResultList();
        }
        catch (Exception e) {
            throw new PersistentException(e);
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<SchemaColumn> findUnusedSchemaColumns(String tableId) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            
            return em.createNamedQuery("SchemaTable.findUnusedColumns", SchemaColumn.class)
                     .setParameter("tableId", tableId)
                     .getResultList();
        }
        catch (Exception e) {
            throw new PersistentException(e);
        }
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
