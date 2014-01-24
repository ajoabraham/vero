package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SCHEMA_ATTRIBUTE database table.
 * 
 */
@Entity
@Table(name="SCHEMA_ATTRIBUTE")
@NamedQuery(name="SchemaAttribute.findAll", query="SELECT s FROM SchemaAttribute s")
public class SchemaAttribute extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=36)
	private String id;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(length=100)
	private String name;

	//bi-directional many-to-one association to SchemaExpression
	@OneToMany(mappedBy="schemaAttribute")
	private List<SchemaExpression> schemaExpressions;

	public SchemaAttribute() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreationTs() {
		return this.creationTs;
	}

	public void setCreationTs(Timestamp creationTs) {
		this.creationTs = creationTs;
	}

	public Timestamp getLastModTs() {
		return this.lastModTs;
	}

	public void setLastModTs(Timestamp lastModTs) {
		this.lastModTs = lastModTs;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SchemaExpression> getSchemaExpressions() {
		return this.schemaExpressions;
	}

	public void setSchemaExpressions(List<SchemaExpression> schemaExpressions) {
		this.schemaExpressions = schemaExpressions;
	}

	public SchemaExpression addSchemaExpression(SchemaExpression schemaExpression) {
		getSchemaExpressions().add(schemaExpression);
		schemaExpression.setSchemaAttribute(this);

		return schemaExpression;
	}

	public SchemaExpression removeSchemaExpression(SchemaExpression schemaExpression) {
		getSchemaExpressions().remove(schemaExpression);
		schemaExpression.setSchemaAttribute(null);

		return schemaExpression;
	}

}