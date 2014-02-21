package com.vero.ui.editor;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vero.ui.model.AttributeObjectData;
import com.vero.ui.model.MetricObjectData;
import com.vero.ui.model.UIData;
import com.vero.ui.report.querypane.QueryBlockPane;

/**
 * 
 * @author Tai Hu
 * 
 */
public final class EditorPaneFactory {
    private static final Logger logger = Logger.getLogger(EditorPaneFactory.class.getName());
    
    private EditorPaneFactory() {        
    }
    
    public static EditorPane<? extends UIData> createEditorPane(QueryBlockPane queryBlockPane, UIData data) {
        EditorPane<? extends UIData> editorPane = null;
        switch (data.getType()) {
            case ATTRIBUTE:
        	editorPane = new AttributeEditorPane(queryBlockPane, (AttributeObjectData) data);
                break;
            case METRIC:
        	editorPane = new MetricEditorPane(queryBlockPane, (MetricObjectData) data);
                break;
            case TABLE_JOIN:
                break;
            default:
                logger.log(Level.SEVERE, "Invalid object type - {0}", data.getType());
        }

        return editorPane;
    }
}