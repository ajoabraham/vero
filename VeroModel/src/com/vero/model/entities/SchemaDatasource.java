package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SCHEMA_DATASOURCE database table.
 * 
 */
@Entity
@Table(name="SCHEMA_DATASOURCE")
@NamedQuery(name="SchemaDatasource.findAll", query="SELECT s FROM SchemaDatasource s")
public class SchemaDatasource extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(length=250)
	private String description;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(nullable=false, length=100)
	private String name;

	//uni-directional one-to-one association to SchemaDatabase
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="SCHEMA_DATABASE_ID", nullable=false)
	private SchemaDatabase schemaDatabase;

	//bi-directional many-to-one association to SchemaProject
	@ManyToOne
	@JoinColumn(name="SCHEMA_PROJECT_ID", nullable=false)
	private SchemaProject schemaProject;

	//bi-directional many-to-one association to SchemaTable
	@OneToMany(mappedBy="schemaDatasource", cascade={CascadeType.PERSIST})
	private List<SchemaTable> schemaTables;

	public SchemaDatasource() {
	}

	public Timestamp getCreationTs() {
		return this.creationTs;
	}

	public void setCreationTs(Timestamp creationTs) {
		this.creationTs = creationTs;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public SchemaDatabase getSchemaDatabase() {
		return this.schemaDatabase;
	}

	public void setSchemaDatabase(SchemaDatabase schemaDatabase) {
		this.schemaDatabase = schemaDatabase;
	}

	public SchemaProject getSchemaProject() {
		return this.schemaProject;
	}

	public void setSchemaProject(SchemaProject schemaProject) {
		this.schemaProject = schemaProject;
	}

	public List<SchemaTable> getSchemaTables() {
		return this.schemaTables;
	}

	public void setSchemaTables(List<SchemaTable> schemaTables) {
		this.schemaTables = schemaTables;
	}

	public SchemaTable addSchemaTable(SchemaTable schemaTable) {
		getSchemaTables().add(schemaTable);
		schemaTable.setSchemaDatasource(this);

		return schemaTable;
	}

	public SchemaTable removeSchemaTable(SchemaTable schemaTable) {
		getSchemaTables().remove(schemaTable);
		schemaTable.setSchemaDatasource(null);

		return schemaTable;
	}

}