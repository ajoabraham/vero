package com.vero.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the SCHEMA_DATABASE database table.
 * 
 */
@Entity
@Table(name="SCHEMA_DATABASE")
@NamedQuery(name="SchemaDatabase.findAll", query="SELECT s FROM SchemaDatabase s")
public class SchemaDatabase extends SchemaData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="AUTH_MODE", length=100)
	private String authMode;

	@Column(name="CREATION_TS")
	private Timestamp creationTs;

	@Column(name="DATABASE_NAME", length=100)
	private String databaseName;

	@Column(name="DATABASE_TYPE")
	private int databaseType;

	@Column(name="DATABASE_VENDOR", length=100)
	private String databaseVendor;

	@Column(name="DATABASE_VERSION", length=50)
	private String databaseVersion;

	@Column(name="HOST_ADDRESS", length=100)
	private String hostAddress;

	@Column(name="LAST_MOD_TS")
	private Timestamp lastModTs;

	@Column(length=100)
	private String password;

	private int port;

	@Column(name="POST_EXEC_COMMANDS", length=500)
	private String postExecCommands;

	@Column(name="PRE_EXEC_COMMANDS", length=500)
	private String preExecCommands;

	@Column(name="USER_NAME", length=100)
	private String userName;

	public SchemaDatabase() {
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

	public int getDatabaseType() {
		return this.databaseType;
	}

	public void setDatabaseType(int databaseType) {
		this.databaseType = databaseType;
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

}