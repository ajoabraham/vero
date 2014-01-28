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
import com.vero.model.entities.SchemaDatabase;
import com.vero.model.entities.SchemaDatasource;
import com.vero.model.entities.SchemaProject;
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
        
        SchemaProject project = new SchemaProject();
        project.setId("5536101a-e477-453a-9b68-3d4bd63ec329");
        project.setName("DEFAULT PROJECT");
        project.setSchemaDatasources(new ArrayList<SchemaDatasource>());
        
        datasource = new SchemaDatasource();
        project.addSchemaDatasource(datasource);
//        datasource.setId(UUID.randomUUID().toString());
        datasource.setName("Test" + UUID.randomUUID().toString());
        SchemaDatabase database = new SchemaDatabase();
        database.setHostAddress("db.vero.com");
        database.setDatabaseName("verometadata");
        database.setUserName("dbuser");
        database.setPassword("password");
        database.setPort(3306);
        datasource.setSchemaDatabase(database);
        
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
	
	datasource.getSchemaDatabase().setPort(100);
	SchemaDatasource data = objectDataDao.update(datasource);
	
	assertEquals(data.getId(), datasource.getId());
	assertEquals(data.getSchemaDatabase().getPort(), 100);
    }
    
    @Test
    public void testIsUniqueName() throws PersistentException {
        boolean isUnique = objectDataDao.isUniqueDatasourceName(UUID.randomUUID().toString());
        
        assertTrue(isUnique);
    }
}
