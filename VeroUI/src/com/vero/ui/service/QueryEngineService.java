/**
 * 
 */
package com.vero.ui.service;

import com.vero.ui.model.QueryBlockObjectData;

/**
 * @author Tai Hu
 *
 */
public interface QueryEngineService {
    String generateSQL(QueryBlockObjectData queryBlockObjectData);
}
