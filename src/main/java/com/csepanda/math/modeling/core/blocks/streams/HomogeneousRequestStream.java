package com.csepanda.math.modeling.core.blocks.streams;

import com.csepanda.math.modeling.core.blocks.AbstractBlock;
import com.csepanda.math.modeling.core.blocks.InputBlock;
import com.csepanda.math.modeling.core.system.LocalTimeline;
import com.csepanda.math.modeling.core.Request;
import com.csepanda.math.modeling.core.RequestData;
import com.csepanda.math.modeling.core.RequestClass;
import com.csepanda.math.modeling.core.generators.DeterministicGenerator;
import com.csepanda.math.modeling.core.generators.Generator;
import com.csepanda.math.modeling.core.system.ModelTime;

import java.util.ArrayList;
import java.util.List;

/** Generates a homogeneous stream of requests.
 *  Homogeneous stream means that it produces requests with same class
 *  and priority.
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class HomogeneousRequestStream extends AbstractBlock implements InputBlock {
    private final LocalTimeline timer;
    private final Generator  generator;

    private Request      nextRequest;
    private RequestClass clazz;
    private int          priority;
    private long         count;


    /** Constructs homogeneous request stream with specified
     *  inter-requisition interval distribution, timer,
     *  start offset, total count of arrival requests, class and priority.
     *
     *  @param generator distribution of arrival requests
     *  @param timer     model local timer
     *  @param offset    start time of first request's arrival
     *  @param count     total count of arrival requests
     *  @param clazz     class of requests
     *  @param priority  initial priority of requests */
    public HomogeneousRequestStream(Generator generator, LocalTimeline timer,
                                    long offset, long count,
                                    RequestClass clazz, int priority) {
        this.generator   = generator;
        this.priority    = priority;
        this.timer       = timer;
        this.count       = count;
        this.clazz       = clazz;
        this.nextRequest = new RequestData(priority, offset, clazz);

        if (count < 0 && generator instanceof DeterministicGenerator) {
            final DeterministicGenerator det = (DeterministicGenerator) generator;
            if (det.getVal() == 0) {
                throw new IllegalArgumentException("Infinity loop");
            }
        }

        timer.addEvent(offset, this);
    }

    @Override
    public Request[] fetch() {
        final double currentModelTime = timer.getTime();
        if (count == 0 || currentModelTime != nextRequest.getBirthTime()) {
            return new RequestData[0];
        }

        final Request toReturn = nextRequest; if (count > 0) count--;
        double nextArrivalInterval = generator.generate();
        System.out.println(count);
        if (nextArrivalInterval == 0) {
            List<Request> requests = new ArrayList<>();
            requests.add(toReturn);
            do {
                if (count > 0) count--;
                requests.add(new RequestData(priority, currentModelTime, clazz));
            } while ((nextArrivalInterval = generator.generate()) == 0);

            final double fetchTime = nextArrivalInterval + currentModelTime;
            nextRequest = new RequestData(priority, fetchTime, clazz);
            passedRequestsCount += requests.size();
            return requests.toArray(new Request[0]);
        }

        final double fetchTime = nextArrivalInterval + currentModelTime;
        nextRequest = new RequestData(priority, fetchTime, clazz);
        timer.addEvent(fetchTime, this);

        passedRequestsCount++;
        return new Request[]{toReturn};
    }


    @Override
    public double getEventTime() {
        final double currentModelTime = timer.getTime();
        if (count == 0) {
            return ModelTime.NO_EVENT;
        }

        final double next = nextRequest.getBirthTime();

        if (next - currentModelTime < 0) {
            throw new IllegalStateException("Arrival event was missed");
        }

        return next;
    }
}
