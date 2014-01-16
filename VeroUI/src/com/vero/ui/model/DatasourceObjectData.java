/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import static com.vero.ui.constants.DatasourceStatus.INACTIVE;
import static com.vero.ui.constants.ObjectType.DATASOURCE;

import java.util.List;

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
    private DBType databaseType = null;
    
    private List<TableObjectData> tableObjectDataList = null;
    private DatasourceStatus status = INACTIVE;
    
    public DatasourceObjectData() {
        
    }
    
    @Override
    public ObjectType getType() {
        return DATASOURCE;
    }

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

    public DatasourceStatus getStatus() {
        return status;
    }

    public void setStatus(DatasourceStatus status) {
        this.status = status;
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }
    
    public StringProperty userNameProperty() {
        return userName;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }
    
    public String getHostname() {
        return hostname.get();
    }

    public void setHostname(String hostname) {
        this.hostname.set(hostname);
    }

    public StringProperty hostnameProperty() {
        return hostname;
    }
    
    public String getDatabaseName() {
        return databaseName.get();
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName.set(databaseName);
    }
    
    public StringProperty databaseNameProperty() {
        return databaseName;
    }

    public DBType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DBType databaseType) {
        this.databaseType = databaseType;
    }   
}
