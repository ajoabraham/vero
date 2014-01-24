package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SCHEMA_TABLE database table.
 * 
 */
@Entity
@Table(name="SCHEMA_TABLE")
@NamedQuery(name="SchemaTable.findAll", query="SELECT s FROM SchemaTable s")
public class SchemaTable extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=36)
	private String id;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(length=500)
	private String description;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(length=100)
	private String name;

	@Column(name="PHYSICAL_NAME", length=100)
	private String physicalName;

	@Column(name="ROW_COUNT")
	private int rowCount;

	@Column(name="TABLE_PREFIX", length=25)
	private String tablePrefix;

	@Column(name="TABLE_TYPE")
	private int tableType;

	//bi-directional many-to-one association to SchemaColumn
	@OneToMany(mappedBy="schemaTable", cascade={CascadeType.PERSIST})
	private List<SchemaColumn> schemaColumns;

	//bi-directional many-to-one association to SchemaDatasource
	@ManyToOne
	@JoinColumn(name="DATASOURCE_ID", nullable=false)
	private SchemaDatasource schemaDatasource;

	public SchemaTable() {
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

	public String getPhysicalName() {
		return this.physicalName;
	}

	public void setPhysicalName(String physicalName) {
		this.physicalName = physicalName;
	}

	public int getRowCount() {
		return this.rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public String getTablePrefix() {
		return this.tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public int getTableType() {
		return this.tableType;
	}

	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

	public List<SchemaColumn> getSchemaColumns() {
		return this.schemaColumns;
	}

	public void setSchemaColumns(List<SchemaColumn> schemaColumns) {
		this.schemaColumns = schemaColumns;
	}

	public SchemaColumn addSchemaColumn(SchemaColumn schemaColumn) {
		getSchemaColumns().add(schemaColumn);
		schemaColumn.setSchemaTable(this);

		return schemaColumn;
	}

	public SchemaColumn removeSchemaColumn(SchemaColumn schemaColumn) {
		getSchemaColumns().remove(schemaColumn);
		schemaColumn.setSchemaTable(null);

		return schemaColumn;
	}

	public SchemaDatasource getSchemaDatasource() {
		return this.schemaDatasource;
	}

	public void setSchemaDatasource(SchemaDatasource schemaDatasource) {
		this.schemaDatasource = schemaDatasource;
	}

}