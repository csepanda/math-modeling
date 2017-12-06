package com.csepanda.math.modeling.core.system;

/** Represent singleton timer for a single model.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class ModelTime {
    /** Means that there are no nearest event according to current state. */
    public final static double NO_EVENT = Double.POSITIVE_INFINITY;

    private double modelTime;

    /** Returns the current model time.
     *  @return the current model time */
    public double getModelTime() {
        return modelTime;
    }

    /** Sets the model time.
     *  @param modelTime new model time */
    public void setModelTime(double modelTime) {
        this.modelTime = modelTime;
    }
}
