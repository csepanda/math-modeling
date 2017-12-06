package com.csepanda.math.modeling.core.system;

import com.csepanda.math.modeling.core.blocks.Node;

// now just single queueing node model
public class Model {
    private final ModelTime timer = new ModelTime();
    private Node node;

    public ModelTime getTimer() {
        return timer;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void run(long requestCount) {
        while (node.getPassedRequestsCount() < requestCount && node.getEventTime() != ModelTime.NO_EVENT) {
            final double time = node.getEventTime();
            timer.setModelTime(time);
            node.run();
        }
    }
}
