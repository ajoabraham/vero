/**
 * 
 */
package com.vero.model.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Tai Hu
 *
 */
public final class PersistentUtils {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("VeroModel");
    
    private PersistentUtils() {
    }

    public static EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    
    // FIXME This method should be called before application close
    public static void closeEntityManagerFactory() {
	entityManagerFactory.close();
    }
}
