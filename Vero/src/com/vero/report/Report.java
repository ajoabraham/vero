package com.vero.report;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private final List<Block> blocks = new ArrayList();
    
    public Report() {        
    }
    
    public List<Block> getBlocks() {
        return this.blocks;
    }
    
    public void addBlock(Block block) {
        this.blocks.add(block);
    }
}
