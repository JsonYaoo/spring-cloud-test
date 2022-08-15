package com.jsonyao.springcloud.chainAsync;

import java.util.concurrent.ExecutorService;

/**
 * 合作单位
 */
public class CoChainServiceImpl extends AbstractChainServiceImpl {

    public CoChainServiceImpl(ExecutorService executor) {
        super(executor);
    }

    @Override
    protected boolean isRun(ChainContext context) {
        return false;
    }

    @Override
    public Boolean call() throws Exception {
        return doCo(context.getVendorCode());
    }

    private Boolean doCo(String vendorCode) {
        return true;
    }
}
