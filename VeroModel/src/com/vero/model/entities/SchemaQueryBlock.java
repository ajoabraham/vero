package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the SCHEMA_QUERY_BLOCK database table.
 * 
 */
@Entity
@Table(name="SCHEMA_QUERY_BLOCK")
@NamedQuery(name="SchemaQueryBlock.findAll", query="SELECT s FROM SchemaQueryBlock s")
public class SchemaQueryBlock extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(length=100)
	private String name;

	@Column(nullable=false)
	private int position;

	//bi-directional many-to-one association to SchemaReport
	@ManyToOne
	@JoinColumn(name="SCHEMA_REPORT_ID", nullable=false)
	private SchemaReport schemaReport;

	public SchemaQueryBlock() {
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

	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public SchemaReport getSchemaReport() {
		return this.schemaReport;
	}

	public void setSchemaReport(SchemaReport schemaReport) {
		this.schemaReport = schemaReport;
	}

}