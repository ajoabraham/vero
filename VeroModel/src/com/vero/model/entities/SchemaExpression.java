package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SCHEMA_EXPRESSION database table.
 * 
 */
@Entity
@Table(name="SCHEMA_EXPRESSION")
@NamedQuery(name="SchemaExpression.findAll", query="SELECT s FROM SchemaExpression s")
public class SchemaExpression extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(length=500)
	private String expression;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	//bi-directional many-to-one association to SchemaAttribute
	@ManyToOne
	@JoinColumn(name="SCHEMA_ATTRIBUTE_ID")
	private SchemaAttribute schemaAttribute;

	//bi-directional many-to-many association to SchemaColumn
	@ManyToMany
	@JoinTable(
		name="COLUMNS_EXPRESSIONS"
		, joinColumns={
			@JoinColumn(name="SCHEMA_EXPRESSION_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="SCHEMA_COLUMN_ID", nullable=false)
			}
		)
	private List<SchemaColumn> schemaColumns;

	//bi-directional many-to-one association to SchemaMetric
	@ManyToOne
	@JoinColumn(name="SCHEMA_METRIC_ID")
	private SchemaMetric schemaMetric;

	public SchemaExpression() {
	}

	public Timestamp getCreationTs() {
		return this.creationTs;
	}

	public void setCreationTs(Timestamp creationTs) {
		this.creationTs = creationTs;
	}

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Timestamp getLastModTs() {
		return this.lastModTs;
	}

	public void setLastModTs(Timestamp lastModTs) {
		this.lastModTs = lastModTs;
	}

	public SchemaAttribute getSchemaAttribute() {
		return this.schemaAttribute;
	}

	public void setSchemaAttribute(SchemaAttribute schemaAttribute) {
		this.schemaAttribute = schemaAttribute;
	}

	public List<SchemaColumn> getSchemaColumns() {
		return this.schemaColumns;
	}

	public void setSchemaColumns(List<SchemaColumn> schemaColumns) {
		this.schemaColumns = schemaColumns;
	}

	public SchemaMetric getSchemaMetric() {
		return this.schemaMetric;
	}

	public void setSchemaMetric(SchemaMetric schemaMetric) {
		this.schemaMetric = schemaMetric;
	}

}