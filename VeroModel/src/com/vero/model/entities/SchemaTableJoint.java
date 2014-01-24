package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the SCHEMA_TABLE_JOINT database table.
 * 
 */
@Entity
@Table(name="SCHEMA_TABLE_JOINT")
@NamedQuery(name="SchemaTableJoint.findAll", query="SELECT s FROM SchemaTableJoint s")
public class SchemaTableJoint extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(name="JOIN_EXPRESSION", length=500)
	private String joinExpression;

	@Column(name="JOIN_TYPE", length=100)
	private String joinType;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(name="TABLE_LEFT", length=100)
	private String tableLeft;

	@Column(name="TABLE_RIGHT", length=100)
	private String tableRight;

	public SchemaTableJoint() {
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

	public String getJoinType() {
		return this.joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public Timestamp getLastModTs() {
		return this.lastModTs;
	}

	public void setLastModTs(Timestamp lastModTs) {
		this.lastModTs = lastModTs;
	}

	public String getTableLeft() {
		return this.tableLeft;
	}

	public void setTableLeft(String tableLeft) {
		this.tableLeft = tableLeft;
	}

	public String getTableRight() {
		return this.tableRight;
	}

	public void setTableRight(String tableRight) {
		this.tableRight = tableRight;
	}

}