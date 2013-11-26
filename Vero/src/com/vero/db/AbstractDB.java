/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.db;

import com.vero.datasource.Table;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;

/**
 *
 * @author ajoabraham
 */
abstract class AbstractDB implements Serializable {
    // ---- RUNTIME MACROS FOR DB QUERIES ---- //
    public static final String REPORT_NAME = "REPORT_NAME";
    public static final String USERNAME = "USERNAME";
    public static final String APP_NAME = "APP_NAME";
    
    public static String DEFAULT_SCHEMA = "public";
    public static String ABSOLUTE_TABLE_NAME_PATTERN = "DATABASE.SCHEMA.TABLE";
    public static String VENDOR_NAME="Database";
    public static String DRIVER = "driver";
    public static String[] SUPPORTED_VERSIONS;
    public static String[] SUPPORTED_AUTH_MODES;

    private String version="";
    private String hostName="";
    private int port=0;
    private String authMode="";
    private String username="";
    private String password="";
    private String databaseName="";
    private String schemaName="";
    
    protected Connection conn = null;
   
    
    public AbstractDB(){
    }
    
    // ---- ABSTRACT METHODS ---- //
    abstract Connection connect();
    abstract void testConnection();
    abstract HashMap<String,Table> getDBTables();
    abstract void getDatabases();
    abstract void getSchemas();
    
    // ---- SETTER & GETTER METHODS ---- //
    public String getVersion() {
        return version;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public String getAuthMode() {
        return authMode;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AbstractDB setVersion(String version) {
        this.version = version;
        return this;
    }

    public AbstractDB setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    protected AbstractDB setPort(int port) {
        this.port = port;
        return this;
    }

    public AbstractDB setAuthMode(String authMode) {
        this.authMode = authMode;
        return this;
    }

    public AbstractDB setUsername(String username) {
        this.username = username;
        return this;
    }

    public AbstractDB setPassword(String password) {
        this.password = password;
        return this;
    }
    
    public String getDatabaseName() {
        return databaseName;
    }

    public AbstractDB setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public AbstractDB setSchemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }
}
