/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.DATABASE;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.vero.model.entities.SchemaDatabase;
import com.vero.ui.constants.DBType;
import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class DatabaseObjectData extends UIData {
    private SchemaDatabase schemaDatabase = null;
    
    private StringProperty userName = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private StringProperty hostname = new SimpleStringProperty();
    private StringProperty databaseName = new SimpleStringProperty();
    private IntegerProperty port = new SimpleIntegerProperty();
    private DBType databaseType = null;
//    private DatasourceObjectData datasourceObjectData = null;
    
    public DatabaseObjectData() {
        this(new SchemaDatabase());
    }    
    
    public DatabaseObjectData(SchemaDatabase schemaDatabase) {
        super(schemaDatabase);
        this.schemaDatabase = schemaDatabase;
        
        // init data
        userName.set(schemaDatabase.getUserName());
        userName.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                DatabaseObjectData.this.schemaDatabase.setUserName(newValue);
            }            
        });
        
        password.set(schemaDatabase.getPassword());
        password.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                DatabaseObjectData.this.schemaDatabase.setPassword(newValue);
            }            
        });
        
        hostname.set(schemaDatabase.getHostAddress());
        hostname.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                DatabaseObjectData.this.schemaDatabase.setHostAddress(newValue);
            }            
        });
        
        databaseName.set(schemaDatabase.getDatabaseName());
        databaseName.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                DatabaseObjectData.this.schemaDatabase.setDatabaseName(newValue);
            }            
        });
        
        port.set(schemaDatabase.getPort());
        port.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                DatabaseObjectData.this.schemaDatabase.setPort(newValue.intValue());
            }            
        });
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
    
    public int getPort() {
        return port.get();
    }
    
    public void setPort(int port) {
        this.port.set(port);
    }
    
    public IntegerProperty portProperty() {
        return port;
    }
    
    @Override
    public ObjectType getType() {
        return DATABASE;
    }
    
    @NotNull(message = "Please select a database type.")
    public DBType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DBType databaseType) {
        this.databaseType = databaseType;
    }

//    public DatasourceObjectData getDatasourceObjectData() {
//        return datasourceObjectData;
//    }
//
//    public void setDatasourceObjectData(DatasourceObjectData datasourceObjectData) {
//        this.datasourceObjectData = datasourceObjectData;
//    }   
}
