package com.vero.ui.report.querypane;

import com.vero.ui.report.dropzone.DropZonePane;

public final class BlockPaneFactory {    
    private BlockPaneFactory() {
	
    }
        
    public static BlockPane createCommentBlockPane() {
	return new CommentBlockPane();
    }
    
    public static BlockPane createQueryBlockPane(QueryPane queryPane, DropZonePane dropZonePane) {
	return new QueryBlockPane(queryPane, dropZonePane);
    }
    
    public static BlockPane createReportBlockPane(QueryPane queryPane, DropZonePane dropZonePane) {
	return new ReportBlockPane(queryPane, dropZonePane);
    }
}
