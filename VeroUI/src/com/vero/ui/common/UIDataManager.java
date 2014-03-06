/**
 * 
 */
package com.vero.ui.common;

import java.util.List;
import java.util.Observable;

import com.vero.ui.event.DatasourceEvent;
import com.vero.ui.event.EventFactory;
import com.vero.ui.event.ReportEvent;
import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.ProjectObjectData;
import com.vero.ui.model.ReportObjectData;
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
    private ProjectObjectData projectObjectData = null;
   
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
        if (projectObjectData == null) {
            projectObjectData = service.getProjectDataObject(PROJECT_ID);
        }
        
        return projectObjectData; 
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
        projectObjectData.addDatasourceObjectData(datasourceObjectData);
	service.persistDatasource(datasourceObjectData);
	
	// Notify all listeners
	fireDatasourceEvent(EventFactory.createDatasourceEvent(DatasourceEvent.DATASOURCE_ADDED, datasourceObjectData));
    }
    
    public void persistReportObjectData(ReportObjectData reportObjectData) throws ServiceException {
        projectObjectData.addReportObjectData(reportObjectData);
        service.persistReport(reportObjectData);
        
        fireReportEvent(EventFactory.createReportEvent(ReportEvent.REPORT_SAVED, reportObjectData));
    }
    
    public void fireDatasourceEvent(DatasourceEvent datasourceEvent) {
        setChanged();
        notifyObservers(datasourceEvent);
    }
    
    public void fireReportEvent(ReportEvent reportEvent) {
        setChanged();
        notifyObservers(reportEvent);
    }
}
