package com.jsonyao.springcloud.chainAsync;

import java.util.concurrent.ExecutorService;

/**
 * 历程
 */
public class CourseChainServiceImpl extends AbstractChainServiceImpl {

    public CourseChainServiceImpl(ExecutorService executor) {
        super(executor);
    }

    @Override
    protected boolean isRun(ChainContext context) {
        return false;
    }

    @Override
    public Boolean call() throws Exception {
        return doCourse(context.getVendorCode());
    }

    private Boolean doCourse(String vendorCode) {
        return true;
    }
}
