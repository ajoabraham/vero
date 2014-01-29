/**
 * 
 */
package com.vero.model.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.eclipse.persistence.annotations.UuidGenerator;


/**
 * @author Tai Hu
 *
 */
@MappedSuperclass
@UuidGenerator(name="METADATA_ID_GEN")
public abstract class SchemaData {
    @Id
    @Column(unique=true, nullable=false, length=36)
    @GeneratedValue(generator = "METADATA_ID_GEN")
    private String id;
    //TH 01/28/2014, no need to manually generate Id.
//    private String id = IdGenerator.generateId();
    
    public SchemaData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
