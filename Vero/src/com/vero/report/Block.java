/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author yulinwen
 */
public class Block {
    public enum BlockType {
        NONE,
        QUERY_BLOCK,
        COMMENT_BLOCK
    }       
    
    private String sqlString = null;
    private Block.BlockType blockType = BlockType.NONE;
    private int blockID;
    private final Map<UUID, UUID> attributeMap = new HashMap();
    private final Map<UUID, UUID> metricMap = new HashMap();
    private final List<UUID> joindefList = new ArrayList();
    
    private Block() {

    }
    
    public BlockType getBlockType() {
        return this.blockType;
    }
    
    public void setBlockType(BlockType inBlockType) {
        this.blockType = inBlockType;
    }
}
