/**
 * 
 */
package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.PROJECT;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.vero.ui.constants.ObjectType;

/**
 * @author Tai Hu
 *
 */
public class ProjectObjectData extends UIData {
    private static final long serialVersionUID = 1L;
    
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    
    private List<DatasourceObjectData> datasourceObjectDataList = new ArrayList<DatasourceObjectData>();
    
    public ProjectObjectData() {
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
        return datasourceObjectDataList;
    }

    public void setDatasourceObjectDataList(List<DatasourceObjectData> datasourceObjectDataList) {
        this.datasourceObjectDataList = datasourceObjectDataList;
    }
    
    public void addDatasourceObjectData(DatasourceObjectData datasourceObjectData) {
	datasourceObjectData.setProjectObjectData(this);
        datasourceObjectDataList.add(datasourceObjectData);
    }
    
    public boolean removeDatasourceObjectData(DatasourceObjectData datasourceObjectData) {
	datasourceObjectData.setProjectObjectData(null);
        return datasourceObjectDataList.remove(datasourceObjectData);
    }
    
    @Override
    public ObjectType getType() {
        return PROJECT;
    }
}
