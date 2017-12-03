package com.csepanda.math.modeling.core.generators;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Random;

/** Random decimal numbers generator with exponential distribution
 *
 *  @author Andrey Bova
 *  @since  0.0.1 */
@RequiredArgsConstructor
public class ExponentialGenerator implements Generator {
    /** Minimal value that this generator can generate */
    @NonNull @Getter private final double min;
    /** Expected value of random variable */
    @NonNull @Getter private final double mean;

    private long seed;
    private final Random random = new Random(0);

    public ExponentialGenerator(double mean) {
        this.mean = mean;
        this.min  = 0;
    }

    @Override
    public double generate() {
        final double v = random.nextDouble();
        return -Math.log(1 - v) * mean + min;
    }

    @Override
    public void setSeed(long seed) {
        random.setSeed(seed);
        this.seed = seed;
    }

    @Override
    public String toString() {
        return "ExponentialGenerator{" +
                "min="    + min  +
                ", mean=" + mean +
                ", seed=" + seed +
                '}';
    }
}
