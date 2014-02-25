/**
 * 
 */
package com.vero.ui.service;

import com.vero.report.Report;
import com.vero.ui.model.QueryBlockObjectData;

/**
 * @author Tai Hu
 *
 */
public interface QueryEngineService {
    Report generateReportMetadata(QueryBlockObjectData queryBlockObjectData);
}
