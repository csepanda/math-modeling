package com.csepanda.math.modeling.core.system;

import com.csepanda.math.modeling.core.blocks.Block;

/** Represents scheduled event in a model.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1
 */
public class ModelEvent implements Comparable<ModelEvent> {
    /** time when event is going to happen */
    final double predictedTime;
    /** block where event is going to happen */
    final Block  eventBlock;

    /** Constructs an instance of ModelEvent with specified
     *  time and block.
     *  @param predictedTime model time when event is going to happen
     *  @param eventBlock    block where event is going to happen */
    ModelEvent(double predictedTime, Block eventBlock) {
        this.predictedTime = predictedTime;
        this.eventBlock = eventBlock;
    }

    @Override
    public int compareTo(ModelEvent o) {
        return Double.compare(predictedTime, o.predictedTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelEvent that = (ModelEvent) o;

        if (Double.compare(that.predictedTime, predictedTime) != 0) return false;
        return eventBlock != null ? eventBlock.equals(that.eventBlock) : that.eventBlock == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(predictedTime);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (eventBlock != null ? eventBlock.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ModelEvent{" +
                "predictedTime=" + predictedTime +
                ", eventBlock=" + eventBlock +
                '}';
    }
}
