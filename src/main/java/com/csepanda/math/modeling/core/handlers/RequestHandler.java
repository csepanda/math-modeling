package com.csepanda.math.modeling.core.handlers;

import com.csepanda.math.modeling.core.Request;

/** Specifies requests handling in servers.
 *  Implementation of this interface should specify distribution of hold time.
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
@FunctionalInterface
public interface RequestHandler {
    /** Handle request and return servicing request with specified
     *  hold time according to handler hold time distribution.
     *  @param request          request to be handled
     *  @param currentModelTime start time of request handling
     *  @return ServicingRequest with specified hold time and start time */
    ServicingRequest handle(Request request, double currentModelTime);
}
