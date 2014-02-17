/**
 * 
 */
package com.vero.model.dao;

import java.util.Collection;
import java.util.List;

import com.vero.model.entities.SchemaAttribute;
import com.vero.model.entities.SchemaData;
import com.vero.model.entities.SchemaMetric;
import com.vero.model.entities.SchemaTable;

/**
 * @author Tai Hu
 *
 */
public interface MetadataDao {
    public <T extends SchemaData> void persist(T schemaData) throws PersistentException;
    public <T extends SchemaData> T find(Class<T> dataType, String id) throws PersistentException;
    public <T extends SchemaData> T update(T schemaData) throws PersistentException;
    public <T extends SchemaData> void remove(T schemaData) throws PersistentException;
    public boolean isUniqueDatasourceName(String projectId, String name) throws PersistentException;
    public List<SchemaAttribute> findSchemaAttributes(String tableId) throws PersistentException;
    public List<SchemaMetric> findSchemaMetrics(String tableId) throws PersistentException;
    public List<SchemaTable> findSchemaTablesByColumnNames(String datasourceId, Collection<String> columnNames) throws PersistentException;
//    public List<SchemaColumn> findUnusedSchemaColumns(String tableId) throws PersistentException;
}
