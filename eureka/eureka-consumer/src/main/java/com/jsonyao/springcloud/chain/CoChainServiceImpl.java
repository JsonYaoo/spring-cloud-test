package com.jsonyao.springcloud.chain;

/**
 * 合作单位
 */
public class CoChainServiceImpl extends AbstractChainServiceImpl {

    @Override
    protected boolean isRun(ChainContext context) {
        return false;
    }

    @Override
    public boolean executeTask(ChainContext context) {
        if(!isRun(context)) {
            selectCo(context.getVendorCode());
        }
        if(next == null) {
            return true;
        }
        return next.executeTask(context) && isOk;
    }

    private void selectCo(String vendorCode) {
        isOk = true;
    }
}
