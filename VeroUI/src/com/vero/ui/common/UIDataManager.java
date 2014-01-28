/**
 * 
 */
package com.vero.ui.common;

import com.vero.ui.model.ProjectObjectData;
import com.vero.ui.service.MetadataPersistentService;
import com.vero.ui.service.ServiceException;
import com.vero.ui.service.ServiceManager;

/**
 * @author Tai Hu
 *
 */
public final class UIDataManager {
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
}
