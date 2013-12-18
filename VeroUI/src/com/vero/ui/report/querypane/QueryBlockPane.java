package com.vero.ui.report.querypane;

import com.vero.ui.constants.BlockType;
import static com.vero.ui.constants.BlockType.QUERY_BLOCK;

public class QueryBlockPane extends BlockPane {
    public QueryBlockPane() {
		
    }

    @Override
    public BlockType getType() {
	return QUERY_BLOCK;
    }    
}