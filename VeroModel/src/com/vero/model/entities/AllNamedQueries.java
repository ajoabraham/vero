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
            query = "SELECT COUNT(d.name) FROM SchemaDatasource d WHERE d.name = :name")
})
public class AllNamedQueries extends SchemaData {
    public AllNamedQueries() {        
    }
}
