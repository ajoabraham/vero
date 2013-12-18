package com.vero.ui.report.querypane;

public final class BlockPaneFactory {
    private static BlockPaneFactory INSTANCE = null;
    
    private BlockPaneFactory() {
	
    }
    
    public static BlockPaneFactory getInstance() {
	if (INSTANCE == null) {
	    INSTANCE = new BlockPaneFactory();
	}
	
	return INSTANCE;
    }
    
    public BlockPane createCommentBlockPane() {
	return new CommentBlockPane();
    }
    
    public BlockPane createQueryBlockPane() {
	return new QueryBlockPane();
    }
    
    public BlockPane createReportBlockPane() {
	return new ReportBlockPane();
    }
}
