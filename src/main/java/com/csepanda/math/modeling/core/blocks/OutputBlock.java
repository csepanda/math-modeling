package com.csepanda.math.modeling.core.blocks;

import com.csepanda.math.modeling.core.Request;

/** Represents a terminal block, which consumes requests
 *  at the end of the node and count them.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public interface OutputBlock extends Block {
    /** Returns count of requests that passed through this block.
     *  @return count of requests that passed through this block */
    long getPassedRequets();

    /** Consume incoming request.
     *  @return true if can consume request otherwise false
     * */
    boolean consume(Request request);

    /* does not make sense for this type of blocks */
    @Override
    default double getEventTime() {
        return Double.MAX_VALUE;
    }
}
