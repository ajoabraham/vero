/**
 * 
 */
package com.vero.model.entities;

import javax.persistence.*;

/**
 * @author Tai Hu
 * 
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "SchemaDatasource.isUniqueName",
            query = "SELECT COUNT(d.name) FROM SchemaProject p JOIN p.schemaDatasources d WHERE p.id = :projectId AND d.name = :name"),
    @NamedQuery(name = "SchemaTable.findAttributes", 
            query = "SELECT a FROM SchemaAttribute a, SchemaTable t JOIN t.schemaColumns c JOIN c.schemaExpressions e WHERE t.id = :tableId AND e.schemaAttribute.id = a.id"),
    @NamedQuery(name = "SchemaTable.findMetrics", 
            query = "SELECT m FROM SchemaMetric m, SchemaTable t JOIN t.schemaColumns c JOIN c.schemaExpressions e WHERE t.id = :tableId AND e.schemaMetric.id = m.id"),
    @NamedQuery(name = "SchemaTable.findUnusedColumns", 
            query = "SELECT c " + 
                    "FROM SchemaTable t JOIN t.schemaColumns c " + 
                    "WHERE t.id = :tableId AND (SELECT COUNT(e) FROM SchemaColumn c1 JOIN c1.schemaExpressions e WHERE c1 = c) = 0")                    
})
public class AllNamedQueries extends SchemaData {
    public AllNamedQueries() {        
    }
}
