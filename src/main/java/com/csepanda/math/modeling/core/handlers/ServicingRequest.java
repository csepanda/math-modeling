package com.csepanda.math.modeling.core.handlers;

import com.csepanda.math.modeling.core.Request;
import com.csepanda.math.modeling.core.RequestClass;

/** Represent request that is hold on server.
 *  Contains by it self some request and behave itself like holding request.
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class ServicingRequest implements Request {
    private final Request request;

    private double startTime;
    private double holdTime;

    public ServicingRequest(Request request, double startTime, double holdTime) {
        if (startTime < 0 || holdTime < 0) {
            throw new IllegalArgumentException("time cannot be less than zero");
        }

        this.request   = request;
        this.startTime = startTime;
        this.holdTime  = holdTime;
    }

    /** Reset service start time to specified argument.
     *  Restart is usually called during resumption after request's interruption.
     *  @param at restart time */
    public void restart(double at) {
        this.startTime = at;
    }

    /** Reduce hold time according difference between
     *  start time and interruption time.
     *  This method haven't to be called if request processing
     *  resumption must be started from beginning.
     *  @param at interruption time */
    public void interrupt(double at) {
        final double diff = at - startTime;
        if (diff < 0) {
            throw new IllegalArgumentException
                    ("Servicing cannot be interrupt before start time");
        }

        if (diff >= holdTime) {
            throw new IllegalStateException
                    ("Servicing had had to be ended before interruption");
        }

        holdTime -= diff;
    }

    /** Returns original processing request.
     *  @return servicing request */
    public Request getRequest() {
        return request;
    }

    /** Returns a model start time of servicing.
     *  @return a model start time of servicing */
    public double getStartTime() {
        return startTime;
    }


    /** Returns supposed model end time of servicing.
     *  @return sum of start time and hold time */
    public double predictedEndTime() {
        return holdTime + startTime;
    }

    /** Returns hold time of servicing.
     *  @return hold time */
    public double getHoldTime() {
        return holdTime;
    }

    @Override
    public RequestClass getRequestClass() {
        return request.getRequestClass();
    }

    @Override
    public int getPriority() {
        return request.getPriority();
    }

    @Override
    public void setPriority(int priority) {
        request.setPriority(priority);
    }

    @Override
    public double getBirthTime() {
        return request.getBirthTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServicingRequest that = (ServicingRequest) o;

        return request.equals(that.request);
    }

    @Override
    public int hashCode() {
        return request.hashCode();
    }
}
