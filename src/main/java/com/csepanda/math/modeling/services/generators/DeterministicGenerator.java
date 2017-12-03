package com.csepanda.math.modeling.services.generators;

import com.csepanda.math.modeling.services.Generator;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/** Determined value generator
 *
 *  @author Andrey Bova
 *  @since  0.0.1 */
@RequiredArgsConstructor
public class DeterministicGenerator implements Generator {
    /** Determined value to return */
    @NonNull @Getter private final double val;

    /** Always return determined value
     *  @return determined value */
    @Override
    public double generate() {
        return val;
    }

    @Override
    public String toString() {
        return "DeterministicGenerator{" +
                "val=" + val +
                '}';
    }
}
