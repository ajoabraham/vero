/**
 * 
 */
package com.vero.ui.util;

import com.vero.metadata.Column;
import com.vero.metadata.Table;
import com.vero.ui.constants.DBKeyType;
import com.vero.ui.constants.TableType;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.TableObjectData;

/**
 * @author Tai Hu
 *
 */
public final class DataUtils {
    private DataUtils() {	
    }
    
    public static void copy(Table source, TableObjectData target) {
	target.setName(source.getObjectName());
	target.setPhysicalName(target.getPhysicalName());
	target.setRowCount(source.getRowCount());
	target.setTableType(TableType.valueOf(source.getTableLogicalType().toString()));
	
	
	for (Column column : source.getColumns().values()) {
	    ColumnObjectData columnObjectData = new ColumnObjectData();
	    copy(column, columnObjectData);
	    target.getColumnObjectDataList().add(columnObjectData);
	}
    }
    
    public static void copy(Column source, ColumnObjectData target) {
	target.setName(source.getObjectName());
	target.setKeyType(DBKeyType.valueOf(source.getKeyType().toString()));
	target.setDataType(source.getDataType());
	target.setDataTypeSize(source.getDataTypeSize());
    }
}
