package com.csepanda.math.modeling.core.blocks.operations;

import com.csepanda.math.modeling.core.RequestClass;
import com.csepanda.math.modeling.core.handlers.RequestHandler;
import com.csepanda.math.modeling.core.system.LocalTimeline;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Represents mutual attributes and behaviour for server blocks like:
 *  <ul>
 *      <li>handler registration;            </li>
 *      <li>statistic collection;            </li>
 *      <li>local timer access;              </li>
 *      <li>server's capacity/channel count. </li>
 *  </ul>
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public abstract class AbstractServerBlock extends AbstractOperationBlock implements ServerBlock {
    protected final Map<RequestClass, ServerUsageStat> statistics = new HashMap<>();
    protected final Map<RequestClass, RequestHandler>  handlers   = new HashMap<>();

    protected final int capacity;

    /** Sets extremely important server block's attributes:
     *  it's capacity/count of channels and local timeline.
     *
     * @param capacity count of channels
     * @param timer    local timeline */
    protected AbstractServerBlock(int capacity, LocalTimeline timer) {
        super(timer);
        if (capacity <= 0) {
            throw new IllegalArgumentException("Illegal count of channels: " + capacity);
        }

        this.capacity = capacity;
    }

    @Override
    public void registerHandler(RequestClass clazz, RequestHandler handler) {
        handlers.put(clazz, handler);
        statistics.put(clazz, new ServerUsageStat());
    }

    @Override
    public boolean isClassSupported(RequestClass clazz) {
        return handlers.containsKey(clazz);
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
            throw new IllegalStateException("MultiChannelServer usage statistics isn't " +
                    "initialized to request class " + requestClass);
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

            return holdTime / timer.getTime() / capacity;
        }

        long getCountOfRequests() { return countOfRequests; }
        double getHoldTime() { return holdTime; }
    }
}
