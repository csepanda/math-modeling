package com.csepanda.math.modeling.core;

/** Represent's request in the system
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class RequestData implements Request {
    private int          priority;
    private final double birthTime;
    private final RequestClass requestClass;

    public RequestData(int priority, double birthTime, RequestClass requestClass) {
        this.priority  = priority;
        this.birthTime = birthTime;
        this.requestClass = requestClass;
    }

    public RequestData(double birthTime, RequestClass requestClass) {
        this.birthTime = birthTime;
        this.requestClass = requestClass;
        this.priority  = 1;
    }


    /** Returns request's class
     *  @return request's class */
    @Override
    public RequestClass getRequestClass() {
        return requestClass;
    }

    /** Returns request's priority.
     *  @return request's priority */
    @Override
    public int getPriority() {
        return priority;
    }

    /** Sets the priority of requests */
    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /** Returns model time when request's was arrived to the model
     *  @return model time of arriving */
    @Override
    public double getBirthTime() {
        return birthTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestData that = (RequestData) o;

        return priority == that.priority &&
                Double.compare(that.birthTime, birthTime) == 0 &&
                requestClass.equals(that.requestClass);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = priority;
        temp = Double.doubleToLongBits(birthTime);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + requestClass.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "priority=" + priority +
                ", birthTime=" + birthTime +
                '}';
    }
}
