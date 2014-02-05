package com.vero.ui.report.querypane;

import com.vero.ui.report.dropzone.DropZonePane;

public final class BlockPaneFactory {    
    private BlockPaneFactory() {
	
    }
        
    public static BlockPane createCommentBlockPane(QueryPane queryPane) {
	return new CommentBlockPane(queryPane);
    }
    
    public static BlockPane createQueryBlockPane(QueryPane queryPane, DropZonePane dropZonePane) {
	return new QueryBlockPane(queryPane, dropZonePane);
    }
    
    public static BlockPane createReportBlockPane(QueryPane queryPane, DropZonePane dropZonePane) {
	return new ReportBlockPane(queryPane, dropZonePane);
    }
}
