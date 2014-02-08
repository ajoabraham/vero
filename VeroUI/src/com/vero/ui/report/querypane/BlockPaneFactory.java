package com.vero.ui.report.querypane;

import com.vero.ui.model.CommentBlockObjectData;
import com.vero.ui.model.QueryBlockObjectData;
import com.vero.ui.model.ReportBlockObjectData;
import com.vero.ui.report.dropzone.DropZonePane;

public final class BlockPaneFactory {    
    private BlockPaneFactory() {
	
    }
        
    public static BlockPane createCommentBlockPane(QueryPane queryPane, CommentBlockObjectData commentBlockObjectData) {
	return new CommentBlockPane(queryPane, commentBlockObjectData);
    }
    
    public static BlockPane createQueryBlockPane(QueryPane queryPane, DropZonePane dropZonePane, QueryBlockObjectData queryBlockObjectData) {
	return new QueryBlockPane(queryPane, dropZonePane, queryBlockObjectData);
    }
    
    public static BlockPane createReportBlockPane(QueryPane queryPane, DropZonePane dropZonePane, ReportBlockObjectData reportBlockObjectData) {
	return new ReportBlockPane(queryPane, dropZonePane, reportBlockObjectData);
    }
}
