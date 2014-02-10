package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SCHEMA_PROJECT database table.
 * 
 */
@Entity
@Table(name="SCHEMA_PROJECT")
@NamedQuery(name="SchemaProject.findAll", query="SELECT s FROM SchemaProject s")
public class SchemaProject extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(length=250)
	private String description;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(nullable=false, length=100)
	private String name;

	//bi-directional many-to-one association to SchemaDatasource
	@OneToMany(mappedBy="schemaProject")
	private List<SchemaDatasource> schemaDatasources;

	//bi-directional many-to-one association to SchemaReport
	@OneToMany(mappedBy="schemaProject")
	private List<SchemaReport> schemaReports;

	public SchemaProject() {
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

	public List<SchemaDatasource> getSchemaDatasources() {
		return this.schemaDatasources;
	}

	public void setSchemaDatasources(List<SchemaDatasource> schemaDatasources) {
		this.schemaDatasources = schemaDatasources;
	}

	public SchemaDatasource addSchemaDatasource(SchemaDatasource schemaDatasource) {
		getSchemaDatasources().add(schemaDatasource);
		schemaDatasource.setSchemaProject(this);

		return schemaDatasource;
	}

	public SchemaDatasource removeSchemaDatasource(SchemaDatasource schemaDatasource) {
		getSchemaDatasources().remove(schemaDatasource);
		schemaDatasource.setSchemaProject(null);

		return schemaDatasource;
	}

	public List<SchemaReport> getSchemaReports() {
		return this.schemaReports;
	}

	public void setSchemaReports(List<SchemaReport> schemaReports) {
		this.schemaReports = schemaReports;
	}

	public SchemaReport addSchemaReport(SchemaReport schemaReport) {
		getSchemaReports().add(schemaReport);
		schemaReport.setSchemaProject(this);

		return schemaReport;
	}

	public SchemaReport removeSchemaReport(SchemaReport schemaReport) {
		getSchemaReports().remove(schemaReport);
		schemaReport.setSchemaProject(null);

		return schemaReport;
	}

}