/**
 * 
 */
package com.vero.ui.common;

import java.util.List;
import java.util.Observable;

import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.ProjectObjectData;
import com.vero.ui.service.MetadataPersistentService;
import com.vero.ui.service.ServiceException;
import com.vero.ui.service.ServiceManager;

/**
 * @author Tai Hu
 *
 */
public final class UIDataManager extends Observable {
    private static final String PROJECT_ID = "5536101a-e477-453a-9b68-3d4bd63ec329";
    private static UIDataManager INSTANCE = null;
    
    private MetadataPersistentService service = null;
   
    private UIDataManager() {
        service = ServiceManager.getMetadataPersistentService();
    }

    public static UIDataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UIDataManager();
        }
        
        return INSTANCE;
    }
    
    public ProjectObjectData getProjectObjectData() throws ServiceException {
        return service.getProjectDataObject(PROJECT_ID);
    }
    
    public boolean isUniqueDatasourceName(String name) throws ServiceException {
        return service.isUniqueDatasourceName(PROJECT_ID, name);
    }
    
    public List<AttributeObjectData> getRelatedAttributeObjectDataList(String tableId) throws ServiceException {
	return service.findAttributeObjectDataList(tableId);
    }
    
    public List<MetricObjectData> getRelatedMetricObjectDataList(String tableId) throws ServiceException {
	return service.findMetricObjectDataList(tableId);
    }
    
    public void persistDatasourceObjectData(DatasourceObjectData datasourceObjectData) throws ServiceException {
	ProjectObjectData projectObjectData = new ProjectObjectData();
	projectObjectData.setId(PROJECT_ID);
	datasourceObjectData.setProjectObjectData(projectObjectData);
	service.persistDatasource(datasourceObjectData);
	
	// Notify all listeners
    }
}
