/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import com.vero.ui.common.ObjectType;
import static com.vero.ui.common.ObjectType.ROOT;
import java.util.List;

/**
 *
 * @author Tai Hu
 */
public class RootObjectData extends UIData {
    private List<DatasourceObjectData> datasourceObjectDataList = null;
    
    public RootObjectData() {
        
    }

    public List<DatasourceObjectData> getDatasourceObjectDataList() {
        return datasourceObjectDataList;
    }

    public void setDatasourceObjectDataList(List<DatasourceObjectData> datasourceObjectDataList) {
        this.datasourceObjectDataList = datasourceObjectDataList;
    }
    
    @Override
    public ObjectType getType() {
        return ROOT;
    }
}
