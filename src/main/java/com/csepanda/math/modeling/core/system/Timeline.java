package com.csepanda.math.modeling.core.system;

import com.csepanda.math.modeling.core.blocks.Block;
import com.csepanda.math.modeling.core.blocks.OperationalBlock;

import java.util.SortedSet;
import java.util.TreeSet;

/** Defines behaviour of timeline.
 *  Timeline is time-sorted set of events.
 *  From timeline it's possible to get nearest event time,
 *  pop or peek blocks or get current model time.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public interface Timeline {
    /** Add event to time line at specified predicted time.
     * @param predictedTime time when event is going to happen
     * @param eventBlock    block where event is going to be happen */
    void addEvent(double predictedTime, Block eventBlock);

    /** Return a model time of nearest event.
     *  @return a model time of nearest event */
    double getNearestEventTime();

    /** Return a block where nearest event is going to happen
     *  @return block where nearest event is going to happen */
    Block peekEventBlock();

    /** Return and remove from a queue block where
     *  nearest event is going to happen.
     *  @return block where nearest event is going to happen */
    Block popEventBlock();

    /** Return current model time
     *  @return current model time */
    double getTime();

}
