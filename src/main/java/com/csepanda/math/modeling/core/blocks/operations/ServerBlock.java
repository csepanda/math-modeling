package com.csepanda.math.modeling.core.blocks.operations;

import com.csepanda.math.modeling.core.RequestClass;
import com.csepanda.math.modeling.core.blocks.Measurable;
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
public interface ServerBlock extends OperationalBlock, Measurable, Lockable {
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
}
