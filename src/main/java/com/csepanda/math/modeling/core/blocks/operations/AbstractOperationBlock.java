package com.csepanda.math.modeling.core.blocks.operations;

import com.csepanda.math.modeling.core.Request;
import com.csepanda.math.modeling.core.blocks.Block;
import com.csepanda.math.modeling.core.blocks.OperationalBlock;
import com.csepanda.math.modeling.core.blocks.OutputBlock;

/** Represents general behaviour of operational block:
 *  transferring through the block to the next.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public abstract class AbstractOperationBlock implements OperationalBlock {
    protected Block next;

    private long passedRequestsCount;

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
    public long getPassedRequestsCount() {
        return passedRequestsCount;
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
