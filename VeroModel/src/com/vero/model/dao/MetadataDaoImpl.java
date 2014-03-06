/**
 * 
 */
package com.vero.model.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.vero.model.entities.SchemaAttribute;
import com.vero.model.entities.SchemaData;
import com.vero.model.entities.SchemaMetric;
import com.vero.model.entities.SchemaTable;
import com.vero.model.util.PersistentUtils;

/**
 * @author Tai Hu
 *
 */
public class MetadataDaoImpl implements MetadataDao {    
    public MetadataDaoImpl() {    
    }

    @Override
    public <T extends SchemaData> void persist(T schemaData) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            em.getTransaction().begin();
            em.merge(schemaData);
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
            // FIXME TH 01/31/2014 When a new datasource persisted into database, reload project
            // won't bring in the new datasource just created (This should be a caching bug in EclipseLink).
            // For now manually refresh it again to bring in all data.
            T schemaData = em.find(dataType, id);
            em.refresh(schemaData);
            
            return schemaData;
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
    public <T extends SchemaData> T update(T schemaData) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            em.getTransaction().begin();
            schemaData = em.merge(schemaData);
            em.getTransaction().commit();
            return schemaData;
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
    public <T extends SchemaData> void remove(T schemaData) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            em.getTransaction().begin();
            schemaData = (T) em.find(schemaData.getClass(), schemaData.getId());
            em.remove(schemaData);
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
    public List<SchemaTable> findSchemaTablesByColumnNames(String datasourceId, Collection<String> columnNames) throws PersistentException {
        EntityManager em = null;
        
        try {
            em = PersistentUtils.createEntityManager();
            TypedQuery<SchemaTable> query = em.createNamedQuery("SchemaTable.findTablesByColumnNames", SchemaTable.class); 
            return query.setParameter("datasourceId", datasourceId)
                        .setParameter("columnNames", columnNames)
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

//    @Override
//    public List<SchemaColumn> findUnusedSchemaColumns(String tableId) throws PersistentException {
//        EntityManager em = null;
//        
//        try {
//            em = PersistentUtils.createEntityManager();
//            
//            return em.createNamedQuery("SchemaTable.findUnusedColumns", SchemaColumn.class)
//                     .setParameter("tableId", tableId)
//                     .getResultList();
//        }
//        catch (Exception e) {
//            throw new PersistentException(e);
//        }
//        finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
}
