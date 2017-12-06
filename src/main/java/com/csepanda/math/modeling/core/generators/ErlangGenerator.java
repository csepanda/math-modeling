package com.csepanda.math.modeling.core.generators;

import java.util.Random;

/** Random value generator with Erlang random variable distribution.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class ErlangGenerator implements Generator {
    private final int shape;
    private final double rate;
    private final Random random = new Random(0);

    /** Constructs an instance of ErlangGenerator with specified
     *  rate and shape parameters.
     *
     *  @param shape positive integer
     *  @param rate  positive real number */
    public ErlangGenerator(int shape, double rate) {
        if (shape <= 0 || rate <= 0 ) {
            throw new IllegalArgumentException("Parameters must be positive");
        }

        this.shape = shape;
        this.rate  = rate;
    }

    /** Constructs an instance of ErlangGenerator with specified
     *  shape and mean variable value.
     *
     *  @param mean  positive real number; mean = shape / rate
     *  @param shape positive integer
     */
    public ErlangGenerator(double mean, int shape) {
        if (shape <= 0 || mean <= 0 ) {
            throw new IllegalArgumentException("Parameters must be positive");
        }

        this.shape = shape;
        this.rate  = shape / mean;
    }

    @Override
    public double generate() {
        double v = 1; for (int i = 0; i < shape; i++, v *= random.nextDouble());
        return -Math.log(v) / rate;
    }

    @Override
    public void setSeed(long seed) {
        random.setSeed(seed);
    }
}
