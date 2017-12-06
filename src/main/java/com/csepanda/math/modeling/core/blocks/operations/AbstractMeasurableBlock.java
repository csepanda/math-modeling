package com.csepanda.math.modeling.core.blocks.operations;

import com.csepanda.math.modeling.core.RequestClass;
import com.csepanda.math.modeling.core.blocks.Measurable;
import com.csepanda.math.modeling.core.system.LocalTimeline;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Implements measurable blocks properties and methods and represents
 *  measurable operation block
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public abstract class AbstractMeasurableBlock extends AbstractOperationBlock implements Measurable {
    private final Map<RequestClass, ServerUsageStat> statistics = new HashMap<>();

    /** Sets extremely important server block's attributes:
     *  it's capacity/count of channels and local timeline.
     *
     * @param timer local timeline */
    protected AbstractMeasurableBlock(LocalTimeline timer) {
        super(timer);
    }

    protected void requestPassed(RequestClass requestClass, double holdTime) {
        ServerUsageStat stat = statistics.computeIfAbsent(requestClass, k -> new ServerUsageStat());

        stat.holdTime += holdTime;
        stat.countOfRequests++;
    }

    @Override
    public double getBusyRate() {
        return statistics.values().stream().
                map(ServerUsageStat::busyRate).
                reduce(Double::sum).
                orElse(-1.0);
    }

    @Override
    public double getBusyRate(RequestClass requestClass) {
        final ServerUsageStat stat = statistics.get(requestClass);
        if (stat == null) {
            return 0;
        }

        return stat.busyRate();
    }

    @Override
    public double getAverageHoldTime() {
        final Collection<ServerUsageStat> stats = statistics.values();
        final double summaryHoldTime = stats.stream().
                map(ServerUsageStat::getHoldTime).
                reduce(Double::sum).
                orElse(-1.0);
        final long summaryRequestCount = stats.stream().
                map(ServerUsageStat::getCountOfRequests).
                reduce(Long::sum).
                orElse(-1L);

        return summaryHoldTime / summaryRequestCount;
    }

    @Override
    public double getAverageHoldTime(RequestClass requestClass) {
        final ServerUsageStat stat = statistics.get(requestClass);
        if (stat == null) {
            throw new IllegalStateException("MultiChannelServer usage statistics isn't " +
                    "initialized to request class " + requestClass);
        }

        return stat.averageHoldTime();
    }

    protected class ServerUsageStat {
        long   countOfRequests;
        double holdTime;

        double averageHoldTime() {
            if (countOfRequests == 0) {
                return 0;
            }

            return holdTime / countOfRequests;
        }

        double busyRate() {
            if (timer.getTime() == 0) {
                return 0;
            }

            return holdTime / timer.getTime();
        }

        long getCountOfRequests() { return countOfRequests; }
        double getHoldTime() { return holdTime; }
    }
}
