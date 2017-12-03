package com.csepanda.math.modeling.core.generators;

/** General interface of random value generators.
 *
 *  @author Andrey Bova
 *  @since 0.0.1 */
@FunctionalInterface
public interface Generator {
    /** Returns random or not-random value.
     *  Randomness and distribution depends on implementation.
     *  @return generated value
     *  @since 0.0.1 */
    double generate();

    /** Sets seed of generator. By default just a stub.
     *  @since 0.0.1 */
    default void setSeed(long seed) { }
}
