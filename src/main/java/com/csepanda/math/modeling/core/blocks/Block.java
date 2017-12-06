package com.csepanda.math.modeling.core.blocks;

/** Represent model block which can produce an event.
 *  Through blocks requests can be transferred, furthermore
 *  they can be processed, consumed or produced in model blocks.
 *  This logic is specified/implemented by subinterfaces and
 *  implementation classes. Generally block only returns time of nearest
 *  event that will happen in this block and count of passed requests through
 *  this block.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public interface Block {
    /** Returns model time of nearest event that will be passed in this block
     *  @return model time of nearest event in this block */
    double getEventTime();

    /** Returns count of request that successfully passed through that block
     *  @return count of passed requests */
    long getPassedRequestsCount();
}
