package com.csepanda.math.modeling.core.blocks.operations;

import com.csepanda.math.modeling.core.Request;
import com.csepanda.math.modeling.core.RequestClass;
import com.csepanda.math.modeling.core.handlers.RequestHandler;
import com.csepanda.math.modeling.core.handlers.ServicingRequest;
import com.csepanda.math.modeling.core.system.LocalTimeline;
import com.csepanda.math.modeling.core.system.ModelTime;

/** Represents a server that can process only one request at its work time.
 *  When it seize a request to process, it locks during the servicing hold time.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class SingleChannelServer extends AbstractServerBlock {
    private ServicingRequest channel;

    public SingleChannelServer(LocalTimeline timer) {
        super(1, timer);
    }

    @Override
    public boolean isLocked() {
        return channel != null;
    }

    @Override
    public boolean seize(Request request) {
        if (isLocked()) {
            return false;
        }

        final RequestClass requestClass = request.getRequestClass();
        final RequestHandler handler = handlers.get(requestClass);
        if (handler == null) {
            throw new IllegalStateException("Unsupportable request class");
        }

        if (request.getClass() == ServicingRequest.class) {
            final ServicingRequest handle = (ServicingRequest) request;
            handle.restart(timer.getTime());
            channel = handle;
        } else {
            channel = handler.handle(request, timer.getTime());
        }

        timer.addEvent(channel.predictedEndTime(), this);
        return true;
    }

    @Override
    public boolean process() {
        if (channel == null) {
            return false;
        }

        final double endTime = channel.predictedEndTime();
        final double curTime = timer.getTime();
        if (endTime == curTime) {
            final ServerUsageStat stat = statistics.get(channel.getRequestClass());
            if (stat == null) {
                throw new IllegalStateException("SingleChannelServer usage statistics isn't " +
                        "initialized to request class "+channel.getRequestClass());
            }

            transferNext(channel.getRequest());

            passedRequestsCount++;
            stat.countOfRequests++;
            stat.holdTime += channel.getHoldTime();

            channel = null;
            return true;
        }

        return false;
    }

    @Override
    public double getEventTime() {
        if (channel == null) {
            return ModelTime.NO_EVENT;
        }

        return channel.predictedEndTime();
    }
}
