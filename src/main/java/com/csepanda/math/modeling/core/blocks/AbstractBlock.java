package com.csepanda.math.modeling.core.blocks;

public abstract class AbstractBlock implements Block {
    protected long passedRequestsCount;

    @Override
    public long getPassedRequestsCount() {
        return passedRequestsCount;
    }
}
