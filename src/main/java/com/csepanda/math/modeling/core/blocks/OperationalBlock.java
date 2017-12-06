package com.csepanda.math.modeling.core.blocks;

import com.csepanda.math.modeling.core.Request;

/** Defines a behaviour of basic operational node's unit.
 *  Operational blocks build chain of request processing
 *  between input and output blocks.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public interface OperationalBlock extends Block {
    /** Seize request to processing inside block.
     *  @return true if request successfully seized to processing */
    boolean seize(Request request);

    /** Bypass operational block and transfer request into the next block
     *  @return true if operation can be completed*/
    boolean transferNext(Request request);

    /** Process request inside block.
     *  @return true if there is request on servicing inside block and
     *          it can be processed at the current model time */
    boolean process();

    /** Sets the next block after this
     *  @param block next block */
    void setNext(Block block);
}
