package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SCHEMA_REPORT_BLOCK database table.
 * 
 */
@Entity
@Table(name="SCHEMA_REPORT_BLOCK")
@NamedQuery(name="SchemaReportBlock.findAll", query="SELECT s FROM SchemaReportBlock s")
public class SchemaReportBlock extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(length=1)
	private String metadata;

	@Column(length=100)
	private String name;

	//bi-directional many-to-one association to SchemaReport
	@ManyToOne
	@JoinColumn(name="SCHEMA_REPORT_ID", nullable=false)
	private SchemaReport schemaReport;

	//bi-directional many-to-one association to SchemaTableJoin
	@OneToMany(mappedBy="schemaReportBlock")
	private List<SchemaTableJoin> schemaTableJoins;

	public SchemaReportBlock() {
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

	public String getMetadata() {
		return this.metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SchemaReport getSchemaReport() {
		return this.schemaReport;
	}

	public void setSchemaReport(SchemaReport schemaReport) {
		this.schemaReport = schemaReport;
	}

	public List<SchemaTableJoin> getSchemaTableJoins() {
		return this.schemaTableJoins;
	}

	public void setSchemaTableJoins(List<SchemaTableJoin> schemaTableJoins) {
		this.schemaTableJoins = schemaTableJoins;
	}

	public SchemaTableJoin addSchemaTableJoin(SchemaTableJoin schemaTableJoin) {
		getSchemaTableJoins().add(schemaTableJoin);
		schemaTableJoin.setSchemaReportBlock(this);

		return schemaTableJoin;
	}

	public SchemaTableJoin removeSchemaTableJoin(SchemaTableJoin schemaTableJoin) {
		getSchemaTableJoins().remove(schemaTableJoin);
		schemaTableJoin.setSchemaReportBlock(null);

		return schemaTableJoin;
	}

}