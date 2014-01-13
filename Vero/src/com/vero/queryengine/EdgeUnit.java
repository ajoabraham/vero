/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vero.queryengine;

import com.vero.metadata.JoinDefinition;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 *
 * @author yulinwen
 */
public class EdgeUnit extends DefaultWeightedEdge implements Comparable<EdgeUnit> {
    static private int EU_ID = 0;
    
    public static enum EUType {
        EUTYPE_NONE,
        EUTYPE_PHYSICAL,
        EUTYPE_VIRTUAL
    }

    private final int id = EU_ID++;
    private EUType type = EUType.EUTYPE_NONE;
    private JoinDefinition joinDef = null;
    private ProcessingUnit srcPU = null;
    private ProcessingUnit dstPU = null;
    private String srcTable = null;
    private String dstTable = null;

    public EdgeUnit() {
        super();
    }

    public int getID() {
        return id;
    }
    
    public EUType getType() {
        return type;
    }
    
    public void setType(EUType inType) {
        type = inType;
    }
    
    public JoinDefinition getJoinDef() {
        return joinDef;
    }

    public void setJoinDef(JoinDefinition inJoinDef) {
        joinDef = inJoinDef;
    }

    public ProcessingUnit retrieveOtherEndPoint(ProcessingUnit thisEndPoint) {
        if (this.getSource() == thisEndPoint) {
            return (ProcessingUnit)this.getTarget();
        } else {
            return (ProcessingUnit)this.getSource();
        }
    }
    
    public ProcessingUnit getSrcPU() {
        if (this.type == EUType.EUTYPE_PHYSICAL) {        
            if (srcPU == null) {
                findSrcDstPU();
            }
        }
        
        return srcPU;
    }

    public void setSrcPU(ProcessingUnit inPU) {
        srcPU = inPU;
    }
    
    public ProcessingUnit getDstPU() {
        if (this.type == EUType.EUTYPE_PHYSICAL) {
            if (dstPU == null) {
                findSrcDstPU();
            }
        }

        return dstPU;
    }

    public void setDstPU(ProcessingUnit inPU) {
        dstPU = inPU;
    }
    
    public String getSrcTable() {
        return srcTable;
    }

    public void setSrcTable(String inTab) {
        srcTable = inTab;
    }

    public String getDstTable() {
        return dstTable;
    }

    public void setDstTable(String inTab) {
        dstTable = inTab;
    }

    public ProcessingUnit retrieveMatchingPU(String inTab) {
        if ((srcTable != null) && (dstTable != null)) {
            if (srcTable.equals(inTab)) {
                return srcPU;
            } else {
                return dstPU;
            }
        } else {
            return null;
        }
    }
        
    public String retrieveMatchingAlias(String inTab) {
        if ((srcTable != null) && (dstTable != null)) {
            if (srcTable.equals(inTab)) {
                return srcPU.assignTableAlias();
            } else if (dstTable.equals(inTab)) {
                return dstPU.assignTableAlias();
            } else {
                System.out.println("@@@ EdgeUnit.retrieveAlias(): not equal to both src and dst table!...");
                return null;
            }
        } else {
            return null;
        }
    }
    
    @Override
    public double getWeight() {
        return super.getWeight();
    }

    private void findSrcDstPU() {
        if (srcPU == null) {
            ProcessingUnit aPU = (ProcessingUnit)this.getSource();
            ProcessingUnit bPU = (ProcessingUnit)this.getTarget();

            if (aPU.getID() < bPU.getID()) {
                srcPU = aPU;
                dstPU = bPU;
            } else {
                srcPU = bPU;
                dstPU = aPU;
            }
        }
    }
    
    @Override
    public int compareTo(EdgeUnit inEU) {
        if (this.id < inEU.id) {
            return -1;
        } else if (this.id > inEU.id) {
            return 1;
        } else {
            return 0;
        }
    }
}
