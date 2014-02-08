/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.ui.constants;

import static com.vero.ui.constants.CSSConstants.CLASS_ATTRIBUTE_OBJECT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_COLUMN_OBJECT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_DATASOURCE_OBJECT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_METRIC_OBJECT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_TABLE_JOIN_OBJECT_PANE;
import static com.vero.ui.constants.CSSConstants.CLASS_TABLE_OBJECT_PANE;

/**
 *
 * @author Tai Hu
 */
public enum ObjectType {  
    PROJECT {
        @Override
        public String getStyleClass() {
            return null;
        }
    },
    DATASOURCE {
        @Override
        public String getStyleClass() {
            return CLASS_DATASOURCE_OBJECT_PANE;
        }
    }, 
    DATABASE {

        @Override
        public String getStyleClass() {
            return null;
        }
        
    },
    TABLE {
        @Override
        public String getStyleClass() {
            return CLASS_TABLE_OBJECT_PANE;
        }
    },
    COLUMN {
        @Override
        public String getStyleClass() {
            return CLASS_COLUMN_OBJECT_PANE;
        }
    },
    EXPRESSION {
        @Override
        public String getStyleClass() {
            return null;
        }
    },
    ATTRIBUTE {
        @Override
        public String getStyleClass() {
            return CLASS_ATTRIBUTE_OBJECT_PANE;
        }
    },
    METRIC {
        @Override
        public String getStyleClass() {
            return CLASS_METRIC_OBJECT_PANE;
        }
    },
    TABLE_JOIN {
        @Override
        public String getStyleClass() {
            return CLASS_TABLE_JOIN_OBJECT_PANE;
        }
    },
    REPORT {
        @Override
        public String getStyleClass() {
            return null;
        }
    },
    GLOBAL_FILTER {

	@Override
        public String getStyleClass() {
	    return null;
        }
	
    },
    COMMENT_BLOCK {
        @Override
        public String getStyleClass() {
            return null;
        }
    },
    QUERY_BLOCK {
        @Override
        public String getStyleClass() {
            return null;
        }
    },
    REPORT_BLOCK {
        @Override
        public String getStyleClass() {
            return null;
        }
    },
    FINAL_BLOCK {
        @Override
        public String getStyleClass() {
            return null;
        }
    };
    
    public abstract String getStyleClass();
}
