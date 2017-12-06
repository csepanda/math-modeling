package com.csepanda.math.modeling.core;

/** Represents transferring request through a model
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public interface Request {
    /** Returns request's class
     *  @return request's class */
    RequestClass getRequestClass();

    /** Returns priority of request. Request's priority is mutable value.
     *  @return request's priority */
    int getPriority();

    /** Set the request's priority
     *  @param priority new priority value */
    void setPriority(int priority);

    /** Returns a model time of request's arrival to a system
     *  @return a model time of request's arrival to a system  */
    double getBirthTime();
}
