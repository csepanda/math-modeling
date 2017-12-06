package com.csepanda.math.modeling.core.system;

import com.csepanda.math.modeling.core.blocks.Block;

import java.util.SortedSet;
import java.util.TreeSet;

/** Represents modeling network's node timeline or any local timeline
 *  inside a model. Contains a time-sorted queue of events in local area.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class LocalTimeline implements Timeline {
    private final SortedSet<ModelEvent> futureEvents = new TreeSet<>();

    private final ModelTime timer;

    /** Constructs an instance of LocalTimeline with specified model timer.
     *  @param timer model timer to be local timeline timer */
    public LocalTimeline(ModelTime timer) {
        this.timer = timer;
    }

    @Override
    public void addEvent(double predictedTime, Block eventBlock) {
        if (predictedTime < timer.getModelTime()) {
            throw new IllegalStateException("Event timer have already passed");
        }

        futureEvents.add(new ModelEvent(predictedTime, eventBlock));
    }

    @Override
    public double getNearestEventTime() {
        final ModelEvent first = futureEvents.first();
        if (first == null) {
            return ModelTime.NO_EVENT;
        } else {
            return first.predictedTime;
        }
    }

    @Override
    public Block peekEventBlock() {
        final ModelEvent first = futureEvents.first();
        if (first == null) {
            return null;
        } else {
            return first.eventBlock;
        }
    }

    @Override
    public Block popEventBlock() {
        final ModelEvent first = futureEvents.first();
        futureEvents.remove(first);
        return first.eventBlock;
    }

    @Override
    public double getTime() {
        return timer.getModelTime();
    }
}
