package com.csepanda.math.modeling.core.blocks.operations;

import com.csepanda.math.modeling.core.Request;
import com.csepanda.math.modeling.core.system.LocalTimeline;

import java.util.ArrayList;
import java.util.List;

/** Represents a request's queue in a modeling system.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class Buffer extends AbstractOperationBlock implements Lockable {
    /** Infinite buffer capacity */
    public final static int ENDLESS_CAPACITTY = -1;

    private final List<Request> buf = new ArrayList<>();
    private final int capacity;

    /** Constructs an instance of Buffer with specified capacity.
     *  @param capacity maximum size of requests queue. If it's negative,
     *                  it means that buffer has infinite capacity */
    public Buffer(int capacity, LocalTimeline timeline) {
        super(timeline);
        this.capacity = capacity;
    }

    @Override
    public boolean isLocked() {
        return capacity < buf.size() && capacity > 0;
    }

    /** Tries to seize buffer. If next block is ready to seize request,
     *  it transfer request further.
     * @param request request to be seized
     * @return true if request was successfully transferred next
     *              or added to buffer */
    @Override
    public boolean seize(Request request) {
        if (isLocked()) {
            return false;
        } else if (transferNext(request)) {
            return true;
        } else {
            buf.add(request);
            if (buf.size() == 1) {
                timer.addEvent(next.getEventTime(), this);
            }
            return true;
        }
    }

    /** Get the first request from a queue
     *  and try to transfer it to a next block.
     *
     *  @return true if request was successfully transferred to a next block */
    @Override
    public boolean process() {
        final Request request = buf.get(0);
        if (transferNext(request)) {
            buf.remove(0);
            if (buf.size() > 0) {
                timer.addEvent(next.getEventTime(), this);
            }
            return true;
        } else {
            return false;
        }
    }

    /** Returns an event time of next to buffer block.
     *  Buffer has no self-generating events.
     *  @return an event time of next to buffer block */
    @Override
    public double getEventTime() {
        return next.getEventTime();
    }
}
