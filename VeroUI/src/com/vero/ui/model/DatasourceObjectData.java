/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import static com.vero.ui.constants.DatasourceStatus.INACTIVE;
import static com.vero.ui.constants.ObjectType.DATASOURCE;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.vero.ui.constants.DBType;
import com.vero.ui.constants.DatasourceStatus;
import com.vero.ui.constants.ObjectType;

/**
 *
 * @author Tai Hu
 */
public class DatasourceObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private StringProperty name = new SimpleStringProperty();
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private StringProperty hostname = new SimpleStringProperty();
    private StringProperty databaseName = new SimpleStringProperty();
    private IntegerProperty port = new SimpleIntegerProperty();
    private DBType databaseType = null;
    
    private List<TableObjectData> tableObjectDataList = new ArrayList<TableObjectData>();
    private DatasourceStatus status = INACTIVE;
    
    public DatasourceObjectData() {
        
    }
    
    @Override
    public ObjectType getType() {
        return DATASOURCE;
    }

    @NotBlank(message = "Name cannot be blank.")
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty() {
        return name;
    }

    public List<TableObjectData> getTableObjectDataList() {
        return tableObjectDataList;
    }

    public void setTableObjectDataList(List<TableObjectData> tableObjectDataList) {
        this.tableObjectDataList = tableObjectDataList;
    }
    
    public void addTableObjectData(TableObjectData tableObjectData) {
	tableObjectDataList.add(tableObjectData);
    }
    
    public boolean removeTableObjectData(TableObjectData tableObjectData) {
	return tableObjectDataList.remove(tableObjectData);
    }

    public DatasourceStatus getStatus() {
        return status;
    }

    public void setStatus(DatasourceStatus status) {
        this.status = status;
    }

    @NotBlank(message = "User name cannot be blank.")
    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }
    
    public StringProperty userNameProperty() {
        return userName;
    }

    @NotBlank(message = "Password cannot be blank.")
    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }
    
    @NotBlank(message = "Hostname cannot be blank.")
    public String getHostname() {
        return hostname.get();
    }

    public void setHostname(String hostname) {
        this.hostname.set(hostname);
    }

    public StringProperty hostnameProperty() {
        return hostname;
    }
    
    @NotBlank(message = "Database name cannot be blank.")
    public String getDatabaseName() {
        return databaseName.get();
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName.set(databaseName);
    }
    
    public StringProperty databaseNameProperty() {
        return databaseName;
    }

    @NotNull(message = "Please select a database type.")
    public DBType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DBType databaseType) {
        this.databaseType = databaseType;
    }   
    
    public int getPort() {
        return port.get();
    }
    
    public void setPort(int port) {
        this.port.set(port);
    }
    
    public IntegerProperty portProperty() {
        return port;
    }
}
