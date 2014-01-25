/**
 * 
 */
package com.vero.model.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vero.model.dao.MetadataDao;
import com.vero.model.dao.MetadataDaoImpl;
import com.vero.model.dao.PersistentException;
import com.vero.model.entities.SchemaDatasource;
import com.vero.model.entities.SchemaTable;

/**
 * @author Tai Hu
 *
 */
public class ObjectDataDaoTest {
    private MetadataDao objectDataDao = null;
    private SchemaDatasource datasource = null;
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        objectDataDao = new MetadataDaoImpl();
        
        datasource = new SchemaDatasource();
//        datasource.setId(UUID.randomUUID().toString());
        datasource.setName("Test" + UUID.randomUUID().toString());
        datasource.setHostAddress("db.vero.com");
        datasource.setDatabaseName("verometadata");
        datasource.setUserName("dbuser");
        datasource.setPassword("password");
        datasource.setPort(3306);
        
        datasource.setSchemaTables(new ArrayList<SchemaTable>());
        
        for (int i = 0; i < 10; i++) {
            SchemaTable table = new SchemaTable();
//            table.setId(UUID.randomUUID().toString());
            table.setName("Table" + UUID.randomUUID().toString());
            table.setPhysicalName("PhysicalTable" + i);
            table.setRowCount(100);
            table.setTableType(2);
            
            datasource.addSchemaTable(table);
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        objectDataDao = null;
    }

    /**
     * Test method for {@link com.vero.model.dao.MetadataDao#persist(java.lang.Object)}.
     * @throws PersistentException 
     */
    @Test
    public void testPersist() throws PersistentException {        
        objectDataDao.persist(datasource);
        
        SchemaDatasource data = objectDataDao.find(SchemaDatasource.class, datasource.getId());
        
        assertEquals(data.getId(), datasource.getId());
        assertEquals(data.getSchemaTables().size(), 10);
    }

    /**
     * Test method for {@link com.vero.model.dao.MetadataDao#update(java.lang.Object)}.
     * @throws PersistentException 
     */
    @Test
    public void testUpdate() throws PersistentException {        
	objectDataDao.persist(datasource);
	
	datasource.setPort(100);
	SchemaDatasource data = objectDataDao.update(datasource);
	
	assertEquals(data.getId(), datasource.getId());
	assertEquals(data.getPort(), 100);
    }
    
    @Test
    public void testIsUniqueName() throws PersistentException {
        boolean isUnique = objectDataDao.isUniqueDatasourceName(UUID.randomUUID().toString());
        
        assertTrue(isUnique);
    }
}
