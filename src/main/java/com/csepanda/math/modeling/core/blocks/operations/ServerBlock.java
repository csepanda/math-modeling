package com.csepanda.math.modeling.core.blocks.operations;

import com.csepanda.math.modeling.core.RequestClass;
import com.csepanda.math.modeling.core.blocks.OperationalBlock;
import com.csepanda.math.modeling.core.handlers.RequestHandler;

/** Defines behaviour of servicing unit.
 *  ServerBlock can process different class requests, however each class
 *  must be registered with handler. For each class it's possible to get
 *  stats like busy rate and average hold time.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public interface ServerBlock extends OperationalBlock, Lockable {
    /** Register handler in server block. Must be called before the time when
     *  model will have started.
     *  After registration request class requests of this class can be
     *  processed in server block.
     *
     * @param clazz   class of request
     * @param handler handler which defines how long will be request holden
     *                in block and how it will processed */
    void registerHandler(RequestClass clazz, RequestHandler handler);

    /** Returns true if handler for this request class was registered in block.
     *  @param clazz request class to check
     *  @return true if request class and handler was registered */
    boolean isClassSupported(RequestClass clazz);

    /** Returns total busy rate of server block.
     *  @return return partition of time when server block was busy */
    double getBusyRate();

    /** Returns busy rate for request class of server block.
     *  @return return partition of time when server block was busy with
     *          servicing of specified class's request */
    double getBusyRate(RequestClass requestClass);

    /** Returns total average model time spent to request processing.
     *  @return total average model time spent to request processing */
    double getAverageHoldTime();

    /** Returns average model time spent to specified class's request processing.
     *  @return average model time spent to specified class's request processing */
    double getAverageHoldTime(RequestClass requestClass);
}
