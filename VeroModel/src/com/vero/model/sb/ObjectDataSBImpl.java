/**
 * 
 */
package com.vero.model.sb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Tai Hu
 *
 */
public class ObjectDataSBImpl implements ObjectDataSB {    
    public ObjectDataSBImpl() {    
    }

    @Override
    public <T> void persist(T objectData) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        em.persist(objectData);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public <T> T find(Class<T> dataType, String id) {
        EntityManager em = createEntityManager();
        T objectData = em.find(dataType, id);
        em.close();
        return objectData;
    }

    @Override
    public <T> T update(T objectData) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        objectData = em.merge(objectData);
        em.getTransaction().commit();
        em.close();
        return objectData;
    }
    
    private EntityManager createEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VeroModel");
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public <T> void remove(T objectData) {
        EntityManager em = createEntityManager();
        em.getTransaction().begin();
        em.remove(objectData);
        em.getTransaction().commit();
        em.close();
    }
}
