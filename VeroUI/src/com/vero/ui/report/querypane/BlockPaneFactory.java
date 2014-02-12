package com.vero.ui.report.querypane;

import com.vero.ui.model.CommentBlockObjectData;
import com.vero.ui.model.QueryBlockObjectData;
import com.vero.ui.model.ReportBlockObjectData;
import com.vero.ui.report.ReportPane;
import com.vero.ui.report.dropzone.DropZonePane;

public final class BlockPaneFactory {    
    private BlockPaneFactory() {
	
    }
        
    public static BlockPane createCommentBlockPane(ReportPane reportPane, CommentBlockObjectData commentBlockObjectData) {
	return new CommentBlockPane(reportPane, commentBlockObjectData);
    }
    
    public static BlockPane createQueryBlockPane(ReportPane reportPane, DropZonePane dropZonePane, QueryBlockObjectData queryBlockObjectData) { 
	return new QueryBlockPane(reportPane, dropZonePane, queryBlockObjectData);
    }
    
    public static BlockPane createReportBlockPane(ReportPane reportPane, DropZonePane dropZonePane, ReportBlockObjectData reportBlockObjectData) {
	return new ReportBlockPane(reportPane, dropZonePane, reportBlockObjectData);
    }
}
