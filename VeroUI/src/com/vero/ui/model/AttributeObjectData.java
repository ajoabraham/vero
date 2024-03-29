/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.model;

import static com.vero.ui.constants.ObjectType.ATTRIBUTE;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.vero.model.entities.SchemaAttribute;
import com.vero.model.entities.SchemaExpression;
import com.vero.ui.constants.ObjectType;

/**
 * 
 * @author Tai Hu
 */
public class AttributeObjectData extends PositionableObjectData {
    private static final long serialVersionUID = 1L;

    private SchemaAttribute schemaAttribute = null;

    private StringProperty name = new SimpleStringProperty();
    private List<ExpressionObjectData> expressionObjectDataList = null;
    private ExpressionObjectData selectedExpressionObjectData = null;
    private List<TableObjectData> blackHardHints = new ArrayList<TableObjectData>();
    private List<TableObjectData> whiteHardHints = new ArrayList<TableObjectData>();

    // private List<TableObjectData> tableObjectDataList = new
    // ArrayList<TableObjectData>();
    // private List<TableJoinObjectData> tableJoinObjectDataList = new
    // ArrayList<TableJoinObjectData>();

    public AttributeObjectData() {
        this(new SchemaAttribute());
    }

    public AttributeObjectData(SchemaAttribute schemaAttribute) {
        super(schemaAttribute);
        this.schemaAttribute = schemaAttribute;

        // init data
        name.set(schemaAttribute.getName());
        name.addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                AttributeObjectData.this.schemaAttribute.setName(newValue);
            }

        });
    }

    @Override
    public ObjectType getType() {
        return ATTRIBUTE;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty() {
        return name;
    }

    public List<ExpressionObjectData> getExpressionObjectDataList() {
        if (expressionObjectDataList == null) initExpressionObjectDataList();
        return expressionObjectDataList;
    }

    // public void setExpressionObjectDataList(List<ExpressionObjectData>
    // expressionObjectDataList) {
    // this.expressionObjectDataList = expressionObjectDataList;
    // }

    public void addExpressionObjectData(ExpressionObjectData expressionObjectData) {
        if (expressionObjectDataList == null) initExpressionObjectDataList();
        expressionObjectDataList.add(expressionObjectData);
        expressionObjectData.setAttributeObjectData(this);
        schemaAttribute.addSchemaExpression(expressionObjectData.getSchemaExpression());
    }

    public boolean removeExpressionObjectData(ExpressionObjectData expressionObjectData) {
        if (expressionObjectDataList == null) initExpressionObjectDataList();
        expressionObjectData.setAttributeObjectData(null);
        schemaAttribute.removeSchemaExpression(expressionObjectData.getSchemaExpression());
        return expressionObjectDataList.remove(expressionObjectData);
    }

    public ExpressionObjectData getSelectedExpressionObjectData() {
        if (selectedExpressionObjectData == null && getExpressionObjectDataList().size() > 0) {
            selectedExpressionObjectData = getExpressionObjectDataList().get(0);
        }
        return selectedExpressionObjectData;
    }

    public void setSelectedExpressionObjectData(ExpressionObjectData selectedExpressionObjectData) {
        this.selectedExpressionObjectData = selectedExpressionObjectData;
    }

    public ExpressionObjectData getExpressionByFormula(String formula) {
        ExpressionObjectData expression = null;

        for (ExpressionObjectData expressionObjectData : expressionObjectDataList) {
            if (formula.equals(expressionObjectData.getFormula())) {
                expression = expressionObjectData;
                break;
            }
        }

        return expression;
    }

    public boolean usedTableObjectData(TableObjectData tableObjectData) {
        for (ExpressionObjectData expressionObjectData : expressionObjectDataList) {
            if (expressionObjectData.containsTableObjectData(tableObjectData)) {
                return true;
            }
        }

        return false;
    }

    private void initExpressionObjectDataList() {
        expressionObjectDataList = new ArrayList<ExpressionObjectData>();

        if (schemaAttribute.getSchemaExpressions() == null) {
            schemaAttribute.setSchemaExpressions(new ArrayList<SchemaExpression>());
        }
        
        for (SchemaExpression schemaExpression : schemaAttribute.getSchemaExpressions()) {
            ExpressionObjectData expressionObjectData = new ExpressionObjectData(schemaExpression);
            expressionObjectData.setAttributeObjectData(this);
            expressionObjectDataList.add(expressionObjectData);
        }
    }

    public SchemaAttribute getSchemaAttribute() {
        return schemaAttribute;
    }

    public DatasourceObjectData getDatasourceObjectData() {
	if (expressionObjectDataList == null) initExpressionObjectDataList();
	DatasourceObjectData datasourceObjectData = null;
	
	if (expressionObjectDataList.size() > 0) {
	    datasourceObjectData = expressionObjectDataList.get(0).getDatasourceObjectData();
	}
	
	return datasourceObjectData;
    }
    
    public List<TableObjectData> getBlackHardHints() {
        return blackHardHints;
    }
    
    public void addBlackHardHint(TableObjectData tableObjectData) {
        blackHardHints.add(tableObjectData);
    }
    
    public boolean removeBlackHardHint(TableObjectData tableObjectData) {
        return blackHardHints.remove(tableObjectData);
    }
    
    public List<TableObjectData> getWhiteHardHints() {
        return whiteHardHints;
    }
    
    public void addWhiteHardHint(TableObjectData tableObjectData) {
        whiteHardHints.add(tableObjectData);
    }
    
    public boolean removeWhiteHardHint(TableObjectData tableObjectData) {
        return whiteHardHints.remove(tableObjectData);
    }
    
    public ExpressionObjectData getExpressionObjectDataById(String id) {
	for (ExpressionObjectData expressionObjectData : expressionObjectDataList) {
	    if (id.equalsIgnoreCase(expressionObjectData.getId())) {
		return expressionObjectData;
	    }
	}
	
	return null;
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        
        sb.append(super.toString());
        sb.append("Name = " + getName() + "\n");
        
        return sb.toString();
    }
    
    // public List<TableObjectData> getTableObjectDataList() {
    // return tableObjectDataList;
    // }
    //
    // public void setTableObjectDataList(List<TableObjectData>
    // tableObjectDataList) {
    // this.tableObjectDataList = tableObjectDataList;
    // }
    //
    // public boolean contains(TableObjectData tableObjectData) {
    // return tableObjectDataList.contains(tableObjectData);
    // }
    //
    // public void addTableObjectData(TableObjectData tableObjectData) {
    // tableObjectDataList.add(tableObjectData);
    // }
    //
    // public boolean removeTableObjectData(TableObjectData tableObjectData) {
    // return tableObjectDataList.remove(tableObjectData);
    // }
    //
    // public List<TableJoinObjectData> getTableJoinObjectDataList() {
    // return tableJoinObjectDataList;
    // }
    //
    // public void setTableJoinObjectDataList(List<TableJoinObjectData>
    // tableJoinObjectDataList) {
    // this.tableJoinObjectDataList = tableJoinObjectDataList;
    // }
    //
    // public void addTableJoinObjectData(TableJoinObjectData
    // tableJoinObjectData) {
    // tableJoinObjectDataList.add(tableJoinObjectData);
    // }
    //
    // public boolean removeTableJoinObjectData(TableJoinObjectData
    // tableJoinObjectData) {
    // return tableJoinObjectDataList.remove(tableJoinObjectData);
    // }
}
