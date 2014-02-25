/**
 * 
 */
package com.vero.ui.service;

import static com.vero.ui.constants.DBKeyType.FOREIGN_KEY;
import static com.vero.ui.constants.DBKeyType.PRIMARY_KEY;

import java.util.List;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.vero.queryengine.QueryEngine;
import com.vero.report.Report;
import com.vero.session.Session;
import com.vero.testparser.TestParser;
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
    private static final Logger logger = Logger.getLogger(QueryEngineServiceImpl.class.getName());

    public QueryEngineServiceImpl() {
    }

    @Override
    public Report generateReportMetadata(QueryBlockObjectData queryBlockObjectData) {
	DatasourceObjectData datasource = queryBlockObjectData.getDatasourceObjectData();
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
	databaseObject.put("vendor", "Teradata");
	jsonDatasource.put("database", databaseObject);
	datasourceArray.put(jsonDatasource);
	
	jsonOutput.object()
	          .key("datasources")
	          .value(datasourceArray);
		
	// Generate tables
	JSONArray tableArray = new JSONArray();
	for (TableObjectData table : tables) {	    
	    JSONObject jsonTable = new JSONObject();
	    jsonTable.put("uuid", table.getId());
	    jsonTable.put("name", table.getPhysicalName());
	    jsonTable.put("rowCount", table.getRowCount());
	    jsonTable.put("datasource", datasource.getName());
	    jsonTable.put("tableType", table.getTableType().getName().toLowerCase());
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
	    tableArray.put(jsonTable);
	}
		
	jsonOutput.key("tables")
	          .value(tableArray);
	
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
		    JSONArray columnTablePair = new JSONArray();
		    columnTablePair.put(column.getName());
		    columnTablePair.put(column.getTableObjectData().getPhysicalName());
		    columnArray.put(columnTablePair);
		}
		jsonExpression.put("columns", columnArray);
		expressionArray.put(jsonExpression);
	    }
	    
	    jsonAttribute.put("expressions", expressionArray);
	    attributeArray.put(jsonAttribute);
	}
	
	jsonOutput.key("attributes")
	          .value(attributeArray);
	
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
		    JSONArray columnTablePair = new JSONArray();
		    columnTablePair.put(column.getName());
		    columnTablePair.put(column.getTableObjectData().getPhysicalName());
		    columnArray.put(columnTablePair);
		}
		jsonExpression.put("columns", columnArray);
		expressionArray.put(jsonExpression);
	    }
	    
	    jsonMetric.put("expressions", expressionArray);
	    metricArray.put(jsonMetric);
	}
	
	jsonOutput.key("metrics")
	          .value(metricArray)
	          .endObject();
	          
	logger.finest("JSON Input = " + jsonOutput.toString());

        TestParser testParser = new TestParser(jsonOutput);
        Session userSession = testParser.parse();
        QueryEngine queryEngine = new QueryEngine();
        queryEngine.preprocess(userSession);
        Report report = queryEngine.getReport();

        logger.finest("Generated SQL = " + report.getBlocks().get(0).getSqlString());        
        
	return report;
    }

}
