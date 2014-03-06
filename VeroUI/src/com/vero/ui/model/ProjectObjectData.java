/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.PROJECT;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.vero.model.entities.SchemaDatasource;
import com.vero.model.entities.SchemaProject;
import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class ProjectObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private SchemaProject schemaProject = null;
    
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    
    private List<DatasourceObjectData> datasourceObjectDataList = null;
    private List<ReportObjectData> reportObjectDataList = new ArrayList<ReportObjectData>();
    
    public ProjectObjectData() {
        this(new SchemaProject());
    }
    
    public ProjectObjectData(SchemaProject schemaProject) {
        super(schemaProject);
        this.schemaProject = schemaProject;
        
        // init data
        name.set(schemaProject.getName());
        name.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ProjectObjectData.this.schemaProject.setName(newValue);
            }
            
        });
        description.set(schemaProject.getDescription());
        description.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ProjectObjectData.this.schemaProject.setDescription(newValue);
            }
        });
        
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
    
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
	return description;
    }
    
    public List<DatasourceObjectData> getDatasourceObjectDataList() {
	if (datasourceObjectDataList == null) initDatasourceObjectDataList();
        return datasourceObjectDataList;
    }

//    public void setDatasourceObjectDataList(List<DatasourceObjectData> datasourceObjectDataList) {
//        this.datasourceObjectDataList = datasourceObjectDataList;
//    }
    
    public void addDatasourceObjectData(DatasourceObjectData datasourceObjectData) {
        if (datasourceObjectDataList == null) initDatasourceObjectDataList();
	datasourceObjectData.setProjectObjectData(this);
        datasourceObjectDataList.add(datasourceObjectData);
        schemaProject.addSchemaDatasource(datasourceObjectData.getSchemaDatasource());
    }
    
    public boolean removeDatasourceObjectData(DatasourceObjectData datasourceObjectData) {
        if (datasourceObjectDataList == null) initDatasourceObjectDataList();
	datasourceObjectData.setProjectObjectData(null);
	schemaProject.removeSchemaDatasource(datasourceObjectData.getSchemaDatasource());
        return datasourceObjectDataList.remove(datasourceObjectData);
    }
    
    public List<ReportObjectData> getReportObjectDataList() { 
        return reportObjectDataList;
    }

//    public void setReportObjectDataList(List<ReportObjectData> reportObjectDataList) {
//        this.reportObjectDataList = reportObjectDataList;
//    }
    
    public void addReportObjectData(ReportObjectData reportObjectData) {
	reportObjectData.setProjectObjectData(this);
	reportObjectDataList.add(reportObjectData);
    }
    
    public boolean removeReportObjectData(ReportObjectData reportObjectData) {
	reportObjectData.setProjectObjectData(null);
	return reportObjectDataList.remove(reportObjectData);
    }
    
    @Override
    public ObjectType getType() {
        return PROJECT;
    }
    
    public SchemaProject getSchemaProject() {
        return schemaProject;
    }
    
    private void initDatasourceObjectDataList() {
        datasourceObjectDataList = new ArrayList<DatasourceObjectData>();
        
        if (schemaProject.getSchemaDatasources() == null) {
            schemaProject.setSchemaDatasources(new ArrayList<SchemaDatasource>());
        }
        
        for (SchemaDatasource schemaDatasource : schemaProject.getSchemaDatasources()) {
            DatasourceObjectData datasourceObjectData = new DatasourceObjectData(schemaDatasource);
            datasourceObjectData.setProjectObjectData(this);
            datasourceObjectDataList.add(datasourceObjectData);
        }
    }
}
