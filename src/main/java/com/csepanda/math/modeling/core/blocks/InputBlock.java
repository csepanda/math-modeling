package com.csepanda.math.modeling.core.blocks;

import com.csepanda.math.modeling.core.Request;

/** Represents input stream of requests.
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public interface InputBlock extends Block {
    /** Fetches requests from stream according to the current model time.
     *  If there are no arrival event, it returns nothing.
     *  @return array of request or empty array */
    Request[] fetch();
}
