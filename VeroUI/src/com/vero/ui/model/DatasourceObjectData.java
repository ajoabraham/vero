/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import com.vero.ui.common.DatasourceStatus;
import static com.vero.ui.common.DatasourceStatus.INACTIVE;
import java.util.List;

/**
 *
 * @author Tai Hu
 */
public class DatasourceObjectData extends UIData {
    private String name = null;
    private List<TableObjectData> tableObjectDataList = null;
    private DatasourceStatus status = INACTIVE;
    
    public DatasourceObjectData() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
