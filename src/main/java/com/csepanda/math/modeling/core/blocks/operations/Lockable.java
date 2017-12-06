package com.csepanda.math.modeling.core.blocks.operations;

/** Defines binary state of block: locked or unlocked.
 *  If block is locked it's impossible to seize that block.
 *
 *  @author   Andrey Bova
 *  @version  0.0.1
 *  @since    0.0.1 */
public interface Lockable {
    /** Returns true if block is locked.
     *  If block is locked, it's impossible to seize that block.
     *  @return true if block is locked */
    boolean isLocked();
}
