package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the SCHEMA_TABLE_JOIN database table.
 * 
 */
@Entity
@Table(name="SCHEMA_TABLE_JOIN")
@NamedQuery(name="SchemaTableJoin.findAll", query="SELECT s FROM SchemaTableJoin s")
public class SchemaTableJoin extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(name="JOIN_EXPRESSION", length=500)
	private String joinExpression;

	@Column(name="JOIN_TYPE")
	private int joinType;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	//bi-directional many-to-one association to SchemaQueryBlock
	@ManyToOne
	@JoinColumn(name="SCHEMA_QUERY_BLOCK_ID")
	private SchemaQueryBlock schemaQueryBlock;

	//bi-directional many-to-one association to SchemaReportBlock
	@ManyToOne
	@JoinColumn(name="SCHEMA_REPORT_BLOCK_ID")
	private SchemaReportBlock schemaReportBlock;

	//uni-directional one-to-one association to SchemaTable
	@OneToOne
	@JoinColumn(name="TABLE_LEFT")
	private SchemaTable schemaTableLeft;

	//uni-directional one-to-one association to SchemaTable
	@OneToOne
	@JoinColumn(name="TABLE_RIGHT")
	private SchemaTable schemaTableRight;

	public SchemaTableJoin() {
	}

	public Timestamp getCreationTs() {
		return this.creationTs;
	}

	public void setCreationTs(Timestamp creationTs) {
		this.creationTs = creationTs;
	}

	public String getJoinExpression() {
		return this.joinExpression;
	}

	public void setJoinExpression(String joinExpression) {
		this.joinExpression = joinExpression;
	}

	public int getJoinType() {
		return this.joinType;
	}

	public void setJoinType(int joinType) {
		this.joinType = joinType;
	}

	public Timestamp getLastModTs() {
		return this.lastModTs;
	}

	public void setLastModTs(Timestamp lastModTs) {
		this.lastModTs = lastModTs;
	}

	public SchemaQueryBlock getSchemaQueryBlock() {
		return this.schemaQueryBlock;
	}

	public void setSchemaQueryBlock(SchemaQueryBlock schemaQueryBlock) {
		this.schemaQueryBlock = schemaQueryBlock;
	}

	public SchemaReportBlock getSchemaReportBlock() {
		return this.schemaReportBlock;
	}

	public void setSchemaReportBlock(SchemaReportBlock schemaReportBlock) {
		this.schemaReportBlock = schemaReportBlock;
	}

	public SchemaTable getSchemaTableLeft() {
		return this.schemaTableLeft;
	}

	public void setSchemaTableLeft(SchemaTable schemaTableLeft) {
		this.schemaTableLeft = schemaTableLeft;
	}

	public SchemaTable getSchemaTableRight() {
		return this.schemaTableRight;
	}

	public void setSchemaTableRight(SchemaTable schemaTableRight) {
		this.schemaTableRight = schemaTableRight;
	}

}