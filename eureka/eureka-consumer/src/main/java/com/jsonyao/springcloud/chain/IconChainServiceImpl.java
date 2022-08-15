package com.jsonyao.springcloud.chain;

/**
 * 图标
 */
public class IconChainServiceImpl extends AbstractChainServiceImpl{

    @Override
    protected boolean isRun(ChainContext context) {
        return false;
    }

    @Override
    public boolean executeTask(ChainContext context) {
        if(!isRun(context)) {
            selectIcon(context.getVendorCode());
        }
        if(next == null) {
            return true;
        }
        return next.executeTask(context) && isOk;
    }

    private void selectIcon(String vendorCode) {
        isOk = true;
    }
}
