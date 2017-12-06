package com.csepanda.math.modeling.core.blocks.streams;

import com.csepanda.math.modeling.core.blocks.AbstractBlock;
import com.csepanda.math.modeling.core.blocks.Block;
import com.csepanda.math.modeling.core.blocks.InputBlock;
import com.csepanda.math.modeling.core.system.LocalTimeline;
import com.csepanda.math.modeling.core.Request;
import com.csepanda.math.modeling.core.system.ModelTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/** Generates a heterogeneous stream of requests.
 *  Heterogeneous stream means that it produce requests with
 *  different classes, priorities and arrival distribution.
 *  Actually it'sa multiplexer of homogeneous request streams.
 *  HeterogeneousRequestStream combine different input streams
 *  into single requests flow.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class HeterogeneousRequestStream extends AbstractBlock implements InputBlock {
    private final LocalTimeline timer;
    private final Collection<? extends InputBlock> inputs;

    /** Construct instance of HeterogeneousRequestStream
     *  with specified timer and input request streams.
     *
     * @param timer  model local timer
     * @param inputs collection of input blocks to be
     *               combined into single requests flow */
    public HeterogeneousRequestStream(LocalTimeline timer, Collection<? extends InputBlock> inputs) {
        this.timer = timer;
        this.inputs = inputs;
    }

    @Override
    public Request[] fetch() {
        List<Request> requests = null;
        for (InputBlock stream : inputs) {
            final Request[] fetch = stream.fetch();
            if (fetch.length > 0) {
                if (requests == null) {
                    requests = new ArrayList<>();
                }

                Collections.addAll(requests, fetch);
            }
        }

        if (requests == null) {
            return new Request[0];
        } else {
            passedRequestsCount += requests.size();
            return requests.toArray(new Request[0]);
        }
    }

    /* prevent iteration through inputs if current time is less than cached time */
    private double minimalTimeCache = 0;
    @Override
    public double getEventTime() {
        final double currentModelTime = timer.getTime();
        if (minimalTimeCache > currentModelTime) {
            return minimalTimeCache;
        }

        return inputs
                .stream()
                .map(Block::getEventTime)
                .min(Double::compareTo)
                .orElse(ModelTime.NO_EVENT);
    }
}
