package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SCHEMA_REPORT database table.
 * 
 */
@Entity
@Table(name="SCHEMA_REPORT")
@NamedQuery(name="SchemaReport.findAll", query="SELECT s FROM SchemaReport s")
public class SchemaReport extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(length=100)
	private String name;

	//bi-directional many-to-one association to SchemaCommentBlock
	@OneToMany(mappedBy="schemaReport", cascade={CascadeType.PERSIST})
	private List<SchemaCommentBlock> schemaCommentBlocks;

	//bi-directional many-to-one association to SchemaFinalBlock
	@OneToMany(mappedBy="schemaReport", cascade={CascadeType.PERSIST})
	private List<SchemaFinalBlock> schemaFinalBlocks;

	//bi-directional many-to-one association to SchemaQueryBlock
	@OneToMany(mappedBy="schemaReport", cascade={CascadeType.PERSIST})
	private List<SchemaQueryBlock> schemaQueryBlocks;

	//bi-directional many-to-one association to SchemaProject
	@ManyToOne
	@JoinColumn(name="SCHEMA_PROJECT_ID", nullable=false)
	private SchemaProject schemaProject;

	//bi-directional many-to-one association to SchemaReportBlock
	@OneToMany(mappedBy="schemaReport", cascade={CascadeType.PERSIST})
	private List<SchemaReportBlock> schemaReportBlocks;

	public SchemaReport() {
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

	public List<SchemaCommentBlock> getSchemaCommentBlocks() {
		return this.schemaCommentBlocks;
	}

	public void setSchemaCommentBlocks(List<SchemaCommentBlock> schemaCommentBlocks) {
		this.schemaCommentBlocks = schemaCommentBlocks;
	}

	public SchemaCommentBlock addSchemaCommentBlock(SchemaCommentBlock schemaCommentBlock) {
		getSchemaCommentBlocks().add(schemaCommentBlock);
		schemaCommentBlock.setSchemaReport(this);

		return schemaCommentBlock;
	}

	public SchemaCommentBlock removeSchemaCommentBlock(SchemaCommentBlock schemaCommentBlock) {
		getSchemaCommentBlocks().remove(schemaCommentBlock);
		schemaCommentBlock.setSchemaReport(null);

		return schemaCommentBlock;
	}

	public List<SchemaFinalBlock> getSchemaFinalBlocks() {
		return this.schemaFinalBlocks;
	}

	public void setSchemaFinalBlocks(List<SchemaFinalBlock> schemaFinalBlocks) {
		this.schemaFinalBlocks = schemaFinalBlocks;
	}

	public SchemaFinalBlock addSchemaFinalBlock(SchemaFinalBlock schemaFinalBlock) {
		getSchemaFinalBlocks().add(schemaFinalBlock);
		schemaFinalBlock.setSchemaReport(this);

		return schemaFinalBlock;
	}

	public SchemaFinalBlock removeSchemaFinalBlock(SchemaFinalBlock schemaFinalBlock) {
		getSchemaFinalBlocks().remove(schemaFinalBlock);
		schemaFinalBlock.setSchemaReport(null);

		return schemaFinalBlock;
	}

	public List<SchemaQueryBlock> getSchemaQueryBlocks() {
		return this.schemaQueryBlocks;
	}

	public void setSchemaQueryBlocks(List<SchemaQueryBlock> schemaQueryBlocks) {
		this.schemaQueryBlocks = schemaQueryBlocks;
	}

	public SchemaQueryBlock addSchemaQueryBlock(SchemaQueryBlock schemaQueryBlock) {
		getSchemaQueryBlocks().add(schemaQueryBlock);
		schemaQueryBlock.setSchemaReport(this);

		return schemaQueryBlock;
	}

	public SchemaQueryBlock removeSchemaQueryBlock(SchemaQueryBlock schemaQueryBlock) {
		getSchemaQueryBlocks().remove(schemaQueryBlock);
		schemaQueryBlock.setSchemaReport(null);

		return schemaQueryBlock;
	}

	public SchemaProject getSchemaProject() {
		return this.schemaProject;
	}

	public void setSchemaProject(SchemaProject schemaProject) {
		this.schemaProject = schemaProject;
	}

	public List<SchemaReportBlock> getSchemaReportBlocks() {
		return this.schemaReportBlocks;
	}

	public void setSchemaReportBlocks(List<SchemaReportBlock> schemaReportBlocks) {
		this.schemaReportBlocks = schemaReportBlocks;
	}

	public SchemaReportBlock addSchemaReportBlock(SchemaReportBlock schemaReportBlock) {
		getSchemaReportBlocks().add(schemaReportBlock);
		schemaReportBlock.setSchemaReport(this);

		return schemaReportBlock;
	}

	public SchemaReportBlock removeSchemaReportBlock(SchemaReportBlock schemaReportBlock) {
		getSchemaReportBlocks().remove(schemaReportBlock);
		schemaReportBlock.setSchemaReport(null);

		return schemaReportBlock;
	}

}