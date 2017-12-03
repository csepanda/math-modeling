package com.csepanda.math.modeling.core.generators;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Random;

/** Random decimal numbers generator with uniform distribution.
 *
 *  @author Andrey Bova
 *  @since  0.0.1 */
@RequiredArgsConstructor
public class UniformGenerator implements Generator {
    /** Minimal value that this generator can generate */
    @NonNull @Getter private final double min;
    /** Upper bound of generator distribution */
    @NonNull @Getter private final double max;

    private long seed;
    private final Random random = new Random(0);

    @Override
    public double generate() {
        final double v = random.nextDouble();
        return v*(max - min + 1) + min;
    }

    @Override
    public void setSeed(long seed) {
        random.setSeed(seed);
        this.seed = seed;
    }

    @Override
    public String toString() {
        return "UniformGenerator{" +
                "min="    + min  +
                ", max="  + max  +
                ", seed=" + seed +
                '}';
    }
}
