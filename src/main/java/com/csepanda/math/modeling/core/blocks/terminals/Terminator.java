package com.csepanda.math.modeling.core.blocks.terminals;

import com.csepanda.math.modeling.core.Request;
import com.csepanda.math.modeling.core.blocks.OutputBlock;

/** Represents a terminal block that just count and consumes
 *  incoming requests.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class Terminator implements OutputBlock {
    private long requestCount = 0;

    @Override
    public long getPassedRequets() {
        return requestCount;
    }

    @Override
    public boolean consume(Request request) {
        requestCount++;
        return true;
    }
}
