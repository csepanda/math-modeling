package com.csepanda.math.modeling.core.blocks;

import com.csepanda.math.modeling.core.Request;
import com.csepanda.math.modeling.core.RequestClass;
import com.csepanda.math.modeling.core.blocks.operations.Buffer;
import com.csepanda.math.modeling.core.blocks.operations.ServerBlock;
import com.csepanda.math.modeling.core.system.LocalTimeline;

/** Represents single queueing node with one
 *  single/multi channel server, buffer and input/ouput blocks.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class Node implements Block, Measurable {
    private final InputBlock  input;
    private final Buffer      buffer;
    private final ServerBlock server;
    private final OutputBlock output;
    private final LocalTimeline timeline;

    public Node(InputBlock input, Buffer buffer, ServerBlock server,
                OutputBlock output, LocalTimeline timeline) {
        if (input == null) throw new IllegalArgumentException
                ("Node cannot be constructed without input block");
        else if (buffer == null) throw new IllegalArgumentException
                ("Node cannot be constructed without buffer");
        else if (server == null) throw new IllegalArgumentException
                ("Node cannot be constructed without server");
        else if (output == null) throw new IllegalArgumentException
                ("Node cannot be constructed without output block");
        else if (timeline == null) throw new IllegalArgumentException
                ("Node cannot be constructed without timeline");

        this.input = input;
        this.buffer = buffer;
        this.server = server;
        this.output = output;
        this.timeline = timeline;

        server.setNext(output);
        buffer.setNext(server);
    }

    public Node(InputBlock input, ServerBlock server,
                OutputBlock output, LocalTimeline timeline) {
        this(input, new Buffer(1, timeline), server, output, timeline);
    }

    public void run() {
        if (timeline.getNearestEventTime() != timeline.getTime()) {
            throw new IllegalStateException("No event at this moment");
        }

        final Block event = timeline.popEventBlock();
        if (event instanceof InputBlock) {
            final Request[] fetch = ((InputBlock) event).fetch();
            for (Request request : fetch) {
                buffer.seize(request);
            }
        } else if (event instanceof OperationalBlock) {
            ((OperationalBlock) event).process();
        } else {
            throw new UnsupportedOperationException("Unsupported block type");
        }
    }

    @Override
    public double getEventTime() {
        return timeline.getNearestEventTime();
    }

    @Override
    public long getPassedRequestsCount() {
        return output.getPassedRequestsCount();
    }

    @Override
    public double getBusyRate() {
        return server.getBusyRate();
    }

    @Override
    public double getBusyRate(RequestClass requestClass) {
        return server.getBusyRate(requestClass);
    }

    @Override
    public double getAverageHoldTime() {
        return getAverageWaitingTime() + getAverageServiceTime();
    }

    @Override
    public double getAverageHoldTime(RequestClass requestClass) {
        return getAverageWaitingTime(requestClass) + getAverageServiceTime(requestClass);
    }

    public double getAverageWaitingTime() {
        return buffer.getAverageHoldTime();
    }

    public double getAverageWaitingTime(RequestClass requestClass) {
        return buffer.getAverageHoldTime(requestClass);
    }

    public double getAverageServiceTime() {
        return server.getAverageHoldTime();
    }

    public double getAverageServiceTime(RequestClass requestClass) {
        return server.getAverageHoldTime(requestClass);
    }
}
