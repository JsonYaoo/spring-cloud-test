package com.jsonyao.springcloud.chainAsync;

import java.util.concurrent.ExecutorService;

/**
 * 图标
 */
public class IconChainServiceImpl extends AbstractChainServiceImpl {

    public IconChainServiceImpl(ExecutorService executor) {
        super(executor);
    }

    @Override
    protected boolean isRun(ChainContext context) {
        return false;
    }

    @Override
    public Boolean call() throws Exception {
        return doIcon(context.getVendorCode());
    }

    private Boolean doIcon(String vendorCode) {
        return true;
    }
}
