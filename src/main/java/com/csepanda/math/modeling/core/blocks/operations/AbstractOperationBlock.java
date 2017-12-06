package com.csepanda.math.modeling.core.blocks.operations;

import com.csepanda.math.modeling.core.Request;
import com.csepanda.math.modeling.core.blocks.AbstractBlock;
import com.csepanda.math.modeling.core.blocks.Block;
import com.csepanda.math.modeling.core.blocks.OperationalBlock;
import com.csepanda.math.modeling.core.blocks.OutputBlock;
import com.csepanda.math.modeling.core.system.LocalTimeline;

/** Represents general behaviour of operational block:
 *  transferring through the block to the next.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public abstract class AbstractOperationBlock extends AbstractBlock implements OperationalBlock {
    protected final LocalTimeline timer;

    protected Block next;

    protected AbstractOperationBlock(LocalTimeline timeline) {
        this.timer = timeline;
    }

    /** Returns block that is next in the chain of blocks.
     *  @return block that is next in the chain of blocks */
    public Block getNext() {
        return next;
    }

    /** Sets the next block in the chain of blocks.
     *  @param next Block to be next in the chain of blocks */
    public void setNext(Block next) {
        this.next = next;
    }

    @Override
    public boolean transferNext(Request request) {
        passedRequestsCount++;
        if (next instanceof OperationalBlock) {
            return ((OperationalBlock) next).seize(request);
        } else if (next instanceof OutputBlock) {
            return ((OutputBlock) next).consume(request);
        } else {
            return false;
        }
    }
}
