package com.csepanda.math.modeling.core.blocks.operations;

import com.csepanda.math.modeling.core.RequestClass;
import com.csepanda.math.modeling.core.handlers.RequestHandler;
import com.csepanda.math.modeling.core.system.LocalTimeline;

import java.util.HashMap;
import java.util.Map;

/** Represents mutual attributes and behaviour for server blocks like:
 *  <ul>
 *      <li>handler registration;            </li>
 *      <li>statistic collection;            </li>
 *      <li>local timer access;              </li>
 *      <li>server's capacity/channel count. </li>
 *  </ul>
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public abstract class AbstractServerBlock extends AbstractMeasurableBlock implements ServerBlock {
    protected final Map<RequestClass, RequestHandler>  handlers   = new HashMap<>();

    protected final int capacity;

    /** Sets extremely important server block's attributes:
     *  it's capacity/count of channels and local timeline.
     *
     * @param capacity count of channels
     * @param timer    local timeline */
    protected AbstractServerBlock(int capacity, LocalTimeline timer) {
        super(timer);
        if (capacity <= 0) {
            throw new IllegalArgumentException("Illegal count of channels: " + capacity);
        }

        this.capacity = capacity;
    }

    @Override
    public void registerHandler(RequestClass clazz, RequestHandler handler) {
        handlers.put(clazz, handler);
    }

    @Override
    public boolean isClassSupported(RequestClass clazz) {
        return handlers.containsKey(clazz);
    }

    @Override
    public double getBusyRate() {
        return super.getBusyRate() / capacity;
    }

    @Override
    public double getBusyRate(RequestClass requestClass) {
        return super.getBusyRate() / capacity;
    }
}
