package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the SCHEMA_COMMENT_BLOCK database table.
 * 
 */
@Entity
@Table(name="SCHEMA_COMMENT_BLOCK")
@NamedQuery(name="SchemaCommentBlock.findAll", query="SELECT s FROM SchemaCommentBlock s")
public class SchemaCommentBlock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=36)
	private String id;

	@Column(length=1)
	private String comment;

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

	public SchemaCommentBlock() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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