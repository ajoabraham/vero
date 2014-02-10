/**
 * 
 */
package com.vero.ui.service;

import static com.vero.ui.constants.DBKeyType.FOREIGN_KEY;
import static com.vero.ui.constants.DBKeyType.PRIMARY_KEY;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.ColumnObjectData;
import com.vero.ui.model.DatasourceObjectData;
import com.vero.ui.model.ExpressionObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.QueryBlockObjectData;
import com.vero.ui.model.TableObjectData;

/**
 * @author Tai Hu
 *
 */
public class QueryEngineServiceImpl implements QueryEngineService {

    public QueryEngineServiceImpl() {
    }

    @Override
    public String generateSQL(QueryBlockObjectData queryBlockObjectData) {
	DatasourceObjectData datasource = null;
	List<TableObjectData> tables = queryBlockObjectData.getTableObjectDataList();
	List<AttributeObjectData> attributes = queryBlockObjectData.getAttributeObjectDataList();
	List<MetricObjectData> metrics = queryBlockObjectData.getMetricObjectDataList();
	
	JSONStringer jsonOutput = new JSONStringer();
	
	// Generate datasources
	JSONArray datasourceArray = new JSONArray();
	JSONObject jsonDatasource = new JSONObject();
	jsonDatasource.put("uuid", datasource.getId());
	jsonDatasource.put("name", datasource.getName());
	JSONObject databaseObject = new JSONObject();
	databaseObject.put("vendor", "PostgreSQL");
	jsonDatasource.put("database", databaseObject);
	datasourceArray.put(jsonDatasource);
	
	jsonOutput.object()
	          .key("datasources")
	          .value(datasourceArray)
	          .endObject();
	
	// Generate tables
	JSONArray tableArray = new JSONArray();
	for (TableObjectData table : tables) {
	    JSONObject jsonTable = new JSONObject();
	    jsonTable.put("uuid", table.getId());
	    jsonTable.put("name", table.getPhysicalName());
	    jsonTable.put("rowCount", table.getRowCount());
	    jsonTable.put("datasource", datasource.getName());
	    JSONArray columnArray = new JSONArray();
	    for (ColumnObjectData column : table.getColumnObjectDataList()) {
		JSONObject jsonColumn = new JSONObject();
		jsonColumn.put("uuid", column.getId());
		jsonColumn.put("name", column.getName());
		jsonColumn.put("type", column.getDataType());
		jsonColumn.put("primaryKey", column.getKeyType() == PRIMARY_KEY);
		jsonColumn.put("foreignKey", column.getKeyType() == FOREIGN_KEY);
		columnArray.put(jsonColumn);
	    }
	    jsonTable.put("columns", columnArray);	    
	}
	
	jsonOutput.object()
	          .key("tables")
	          .value(tableArray)
	          .endObject();
	
	// Generate attributes
	JSONArray attributeArray = new JSONArray();
	for (AttributeObjectData attribute : attributes) {
	    JSONObject jsonAttribute = new JSONObject();
	    jsonAttribute.put("uuid", attribute.getId());
	    jsonAttribute.put("name", attribute.getName());
	    
	    JSONArray expressionArray = new JSONArray();
	    
	    for (ExpressionObjectData expression : attribute.getExpressionObjectDataList()) {
		JSONObject jsonExpression = new JSONObject();
		jsonExpression.put("uuid", expression.getId());
		jsonExpression.put("definition", expression.getFormula());
		JSONArray columnArray = new JSONArray();
		for (ColumnObjectData column : expression.getColumnObjectDataList()) {
		    columnArray.put(column.getName());
		}
		jsonExpression.put("columns", columnArray);
		expressionArray.put(jsonExpression);
	    }
	    
	    jsonAttribute.put("expressions", expressionArray);
	}
	
	jsonOutput.object()
	          .key("attributes")
	          .value(attributeArray)
	          .endObject();
	
	// Generate metrics
	JSONArray metricArray = new JSONArray();
	for (MetricObjectData metric : metrics) {
	    JSONObject jsonMetric = new JSONObject();
	    jsonMetric.put("uuid", metric.getId());
	    jsonMetric.put("name", metric.getName());
	    
	    JSONArray expressionArray = new JSONArray();
	    
	    for (ExpressionObjectData expression : metric.getExpressionObjectDataList()) {
		JSONObject jsonExpression = new JSONObject();
		jsonExpression.put("uuid", expression.getId());
		jsonExpression.put("definition", expression.getFormula());
		JSONArray columnArray = new JSONArray();
		for (ColumnObjectData column : expression.getColumnObjectDataList()) {
		    columnArray.put(column.getName());
		}
		jsonExpression.put("columns", columnArray);
		expressionArray.put(jsonExpression);
	    }
	    
	    jsonMetric.put("expressions", expressionArray);
	}
	
	jsonOutput.object()
	          .key("metrics")
	          .value(metricArray)
	          .endObject();
	          
System.out.println("Output = " + jsonOutput.toString());	
	return null;
    }

}
