package com.vero.ui.report.querypane;

import com.vero.ui.report.dropzone.DropZonePane;

public final class BlockPaneFactory {    
    private BlockPaneFactory() {
	
    }
        
    public static BlockPane createCommentBlockPane() {
	return new CommentBlockPane();
    }
    
    public static BlockPane createQueryBlockPane(DropZonePane dropZonePane) {
	return new QueryBlockPane(dropZonePane);
    }
    
    public static BlockPane createReportBlockPane(DropZonePane dropZonePane) {
	return new ReportBlockPane(dropZonePane);
    }
}
