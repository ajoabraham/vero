package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the SCHEMA_FINAL_BLOCK database table.
 * 
 */
@Entity
@Table(name="SCHEMA_FINAL_BLOCK")
@NamedQuery(name="SchemaFinalBlock.findAll", query="SELECT s FROM SchemaFinalBlock s")
public class SchemaFinalBlock extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length=1)
	private String commands;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(nullable=false)
	private int position;

	//bi-directional many-to-one association to SchemaReport
	@ManyToOne
	@JoinColumn(name="REPORT_ID", nullable=false)
	private SchemaReport schemaReport;

	public SchemaFinalBlock() {
	}

	public String getCommands() {
		return this.commands;
	}

	public void setCommands(String commands) {
		this.commands = commands;
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