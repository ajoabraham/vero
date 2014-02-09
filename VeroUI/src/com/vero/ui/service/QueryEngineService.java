/**
 * 
 */
package com.vero.ui.service;

import java.util.List;

import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.ReportObjectData;
import com.vero.ui.model.TableObjectData;

/**
 * @author Tai Hu
 *
 */
public interface QueryEngineService {
    ReportObjectData generateSQL(DatasourceObjectData datasource, List<AttributeObjectData> attributes, List<MetricObjectData> metrics, List<TableObjectData> tables);
}
