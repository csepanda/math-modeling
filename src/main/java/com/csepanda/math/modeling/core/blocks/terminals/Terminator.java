package com.csepanda.math.modeling.core.blocks.terminals;

import com.csepanda.math.modeling.core.Request;
import com.csepanda.math.modeling.core.blocks.AbstractBlock;
import com.csepanda.math.modeling.core.blocks.OutputBlock;

/** Represents a terminal block that just count and consumes
 *  incoming requests.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class Terminator extends AbstractBlock implements OutputBlock {
    @Override
    public boolean consume(Request request) {
        passedRequestsCount++;
        return true;
    }
}
