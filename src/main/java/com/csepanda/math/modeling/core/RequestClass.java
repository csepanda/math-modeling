package com.csepanda.math.modeling.core;

/** Represents specific request's class.
 *  Classes of requests differ in arrival and hold processes and their routes.
 *  <code>class RequestClass</code> doesn't specify arrival, hold process or routes.
 *  It just tag requests with id and name. Name is just human-readable string, which
 *  doesn't play a role to a system.
 *
 *  @author  Andrey Bova
 *  @version 0.0.1
 *  @since   0.0.1 */
public class RequestClass {
    private final long   id;
    private final String name;

    /** Constructs class with specified id and name.
     *  It strongly recommended to create <em>only</em>
     *  each class with unique id to prevent mistakes.
     *  @param id   classes's id; it's strongly recommended to create <em>only</em>
     *              each class with unique id to prevent mistakes.
     *  @param name human-readable string. Doesn't play a role to a system. */
    public RequestClass(long id, String name) {
        this.id   = id;
        this.name = name;
    }

    /** Returns id of the request class
     *  @return request class id */
    public long getId() {
        return id;
    }

    /** Return name of the request class. Name does not play a role to a system
     *  @return request class name */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestClass that = (RequestClass) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
