package com.vero.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the SCHEMA_DATASOURCE database table.
 * 
 */
@Entity
@Table(name="SCHEMA_DATASOURCE")
@NamedQuery(name="SchemaDatasource.findAll", query="SELECT s FROM SchemaDatasource s")
public class SchemaDatasource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=36)
	private String id;

	@Column(name="AUTH_MODE", length=100)
	private String authMode;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(name="DATABASE_NAME", length=100)
	private String databaseName;

	@Column(name="DATABASE_VENDOR", length=100)
	private String databaseVendor;

	@Column(name="DATABASE_VERSION", length=50)
	private String databaseVersion;

	@Column(name="HOST_ADDRESS", length=100)
	private String hostAddress;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(nullable=false, length=100)
	private String name;

	@Column(length=100)
	private String password;

	private int port;

	@Column(name="POST_EXEC_COMMANDS", length=500)
	private String postExecCommands;

	@Column(name="PRE_EXEC_COMMANDS", length=500)
	private String preExecCommands;

	@Column(name="USER_NAME", length=100)
	private String userName;

	//bi-directional many-to-one association to SchemaReport
	@OneToMany(mappedBy="schemaDatasource")
	private List<SchemaReport> schemaReports;

	//bi-directional many-to-one association to SchemaTable
	@OneToMany(mappedBy="schemaDatasource")
	private List<SchemaTable> schemaTables = new ArrayList<SchemaTable>();

	public SchemaDatasource() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthMode() {
		return this.authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}

	public Timestamp getCreationTs() {
		return this.creationTs;
	}

	public void setCreationTs(Timestamp creationTs) {
		this.creationTs = creationTs;
	}

	public String getDatabaseName() {
		return this.databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDatabaseVendor() {
		return this.databaseVendor;
	}

	public void setDatabaseVendor(String databaseVendor) {
		this.databaseVendor = databaseVendor;
	}

	public String getDatabaseVersion() {
		return this.databaseVersion;
	}

	public void setDatabaseVersion(String databaseVersion) {
		this.databaseVersion = databaseVersion;
	}

	public String getHostAddress() {
		return this.hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPostExecCommands() {
		return this.postExecCommands;
	}

	public void setPostExecCommands(String postExecCommands) {
		this.postExecCommands = postExecCommands;
	}

	public String getPreExecCommands() {
		return this.preExecCommands;
	}

	public void setPreExecCommands(String preExecCommands) {
		this.preExecCommands = preExecCommands;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<SchemaReport> getSchemaReports() {
		return this.schemaReports;
	}

	public void setSchemaReports(List<SchemaReport> schemaReports) {
		this.schemaReports = schemaReports;
	}

	public SchemaReport addSchemaReport(SchemaReport schemaReport) {
		getSchemaReports().add(schemaReport);
		schemaReport.setSchemaDatasource(this);

		return schemaReport;
	}

	public SchemaReport removeSchemaReport(SchemaReport schemaReport) {
		getSchemaReports().remove(schemaReport);
		schemaReport.setSchemaDatasource(null);

		return schemaReport;
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