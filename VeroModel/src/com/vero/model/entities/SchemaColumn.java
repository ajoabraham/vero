package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SCHEMA_COLUMN database table.
 * 
 */
@Entity
@Table(name="SCHEMA_COLUMN")
@NamedQuery(name="SchemaColumn.findAll", query="SELECT s FROM SchemaColumn s")
public class SchemaColumn extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(name="DATA_TYPE", length=100)
	private String dataType;

	@Column(name="DERIVED_DATA_TYPE", length=100)
	private String derivedDataType;

	@Column(name="KEY_TYPE")
	private int keyType;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(length=100)
	private String name;

	//bi-directional many-to-one association to SchemaTable
	@ManyToOne
	@JoinColumn(name="SCHEMA_TABLE_ID", nullable=false)
	private SchemaTable schemaTable;

	//bi-directional many-to-many association to SchemaExpression
	@ManyToMany(mappedBy="schemaColumns")
	private List<SchemaExpression> schemaExpressions;

	public SchemaColumn() {
	}

	public Timestamp getCreationTs() {
		return this.creationTs;
	}

	public void setCreationTs(Timestamp creationTs) {
		this.creationTs = creationTs;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDerivedDataType() {
		return this.derivedDataType;
	}

	public void setDerivedDataType(String derivedDataType) {
		this.derivedDataType = derivedDataType;
	}

	public int getKeyType() {
		return this.keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
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

	public SchemaTable getSchemaTable() {
		return this.schemaTable;
	}

	public void setSchemaTable(SchemaTable schemaTable) {
		this.schemaTable = schemaTable;
	}

	public List<SchemaExpression> getSchemaExpressions() {
		return this.schemaExpressions;
	}

	public void setSchemaExpressions(List<SchemaExpression> schemaExpressions) {
		this.schemaExpressions = schemaExpressions;
	}

}