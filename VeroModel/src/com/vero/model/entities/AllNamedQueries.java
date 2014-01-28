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
            query = "SELECT COUNT(d.name) FROM SchemaProject p JOIN p.schemaDatasources d WHERE p.id = :projectId AND d.name = :name")
})
public class AllNamedQueries extends SchemaData {
    public AllNamedQueries() {        
    }
}
