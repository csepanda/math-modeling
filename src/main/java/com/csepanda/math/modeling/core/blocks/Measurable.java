package com.csepanda.math.modeling.core.blocks;

import com.csepanda.math.modeling.core.RequestClass;

/** Represents block which is collecting statistics
 *  and can provide measurements.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public interface Measurable {
    /** Returns total busy rate of block.
     *  @return return partition of time when server block was busy */
    double getBusyRate();

    /** Returns busy rate for request class of block.
     *  @return return partition of time when server block was busy with
     *          servicing of specified class's request */
    double getBusyRate(RequestClass requestClass);

    /** Returns average model time spent to standing in block
     *  @return average model time spent to standing in block */
    double getAverageHoldTime();

    /** Returns average model time spent to standing in block
     *  for specified request class.
     *  @return average model time spent to standing in block */
    double getAverageHoldTime(RequestClass requestClass);
}
